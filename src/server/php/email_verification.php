<?php
header("Content-Type: application/json");
session_start();
define('ACCESS_ALLOWED', true);
require_once "../php/conn.php";
$mail_config = require_once "../php/email_config.php";

require 'PHPMailer-master/src/PHPMailer.php';
require 'PHPMailer-master/src/SMTP.php';
require 'PHPMailer-master/src/Exception.php';

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

$token = ";;mslaundryshop2025;;";
$action = $_POST['action'] ?? '';
$ownerEmail = $mail_config['owner_email'];

// ============ SEND VERIFICATION CODE ============
if ($_SERVER["REQUEST_METHOD"] === "POST" && $action === 'send') {
    // 1) Basic token & field check
    if (
        !isset($_POST['client_fetch_token_request']) ||
        $_POST['client_fetch_token_request'] !== $token ||
        !isset($_POST['email'], $_POST['fname'], $_POST['lname'], $_POST['gender'], $_POST['branch'])
    ) {
        echo json_encode(["response" => false]);
        exit;
    }


    function respondEmailTaken()
    {
        echo json_encode([
            "response" => false,
            "error"    => "An account with this email has either already been created or is pending approval.",
            "exist"    => true
        ]);
        exit;
    }

    $email = trim($_POST['email']);
    if (strcasecmp($email, $ownerEmail) === 0) {
        respondEmailTaken();
    }

    $branch = $_POST['branch'];
    $stmt = $conn->prepare("
        SELECT email FROM approvals       WHERE email = ? AND branch = ?
        UNION
        SELECT email FROM user_credentials WHERE email = ? AND branch = ?
    ");
    $stmt->bind_param("ssss", $email, $branch, $email, $branch);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        respondEmailTaken();
    }

    $fname  = $_POST['fname'];
    $lname  = $_POST['lname'];
    $gender = $_POST['gender'];

    try {
        $code = rand(100000, 999999);
        $_SESSION['email_code']      = $code;
        $_SESSION['email_to_verify'] = $email;
        $_SESSION['register_fname']  = $fname;
        $_SESSION['register_lname']  = $lname;
        $_SESSION['register_gender'] = $gender;
        $_SESSION['register_branch'] = $branch;

        $mail = new PHPMailer(true);
        $mail->isSMTP();
        $mail->Host       = 'smtp.gmail.com';
        $mail->SMTPAuth   = true;
        $mail->Username   = $mail_config['username'];
        $mail->Password   = $mail_config['password'];
        $mail->SMTPSecure = 'tls';
        $mail->Port       = 587;

        $mail->setFrom($mail_config['username'], 'Mrs. Clean Laundry Ease');
        $mail->addAddress($email, "$fname $lname");
        $mail->isHTML(true);
        $mail->Subject = 'Please Verify Your Email';
        $mail->Body = '
        <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9; border-radius: 8px;">
            <div style="text-align: center;">
                <img src="https://i.imgur.com/xmdt55d.png" alt="Mrs. Clean Laundry Ease Logo" style="max-width: 120px; margin-bottom: 20px;">
                <h2 style="color: #007bff;">Email Verification Required</h2>
            </div>
            <p>Hello <b>' . (($gender === "Male") ? "Mr. " : "Ms. ") . htmlspecialchars("$fname $lname") . '</b>,</p>
            <p>To continue your registration, please verify your email address by entering the code below:</p>
            <p style="font-size: 24px; font-weight: bold; color: #28a745;">' . $code . '</p>
            <p>This code will remain valid unless you cancel the registration process or close the verification form.</p>
            <hr>
            <p style="font-size: 13px; color: #555;">If you did not request this verification or are not signing up, you may safely ignore this message. No further action is required.</p>
            <p style="margin-top: 30px; font-size: 12px; color: #999;">&copy; ' . date("Y") . ' Mrs. Clean Laundry Ease. All rights reserved.</p>
        </div>
    ';
        $mail->send();
        echo json_encode(["response" => true]);
    } catch (Exception $e) {
        echo json_encode([
            "response" => false,
            "error"    => $mail->ErrorInfo
        ]);
    }
    exit;
}


// ============ VERIFY CODE ============
else if ($_SERVER["REQUEST_METHOD"] === "POST" && $action === 'verify') {
    if (
        !isset($_POST['client_fetch_token_request']) || $_POST['client_fetch_token_request'] !== $token ||
        !isset($_POST['code']) || !isset($_SESSION['email_code'])
    ) {
        echo json_encode(["response" => false]);
        exit;
    }

    $code = $_POST['code'];
    if ($code == $_SESSION['email_code'] && isset($_POST['fname']) && isset($_POST['lname']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['role']) && isset($_POST['branch'])) {

        $time    = date('Y-m-d H:i:s');
        $fname = $_POST['fname'] ?? null;
        $lname = $_POST['lname'] ?? null;
        $email = $_POST['email'] ?? null;
        $pass = $_POST['password'] ?? null;
        $role = $_POST['role'] ?? null;
        $branch = $_POST['branch'] ?? null;
        $gender = $_POST['gender'] ?? null;
        $status = "requested";
        $hashedPassword = password_hash($pass, PASSWORD_DEFAULT);
        $stmt = $conn->prepare("INSERT INTO approvals (time, firstname, lastname, email, password, role, branch, gender, status) VALUES (?,?,?,?,?,?,?,?,?)");
        $stmt->bind_param("sssssssss", $time, $fname, $lname, $email, $hashedPassword, $role, $branch, $gender, $status);
        if ($stmt->execute()) {
            unset($_SESSION['email_code']);
            $message = "You have a new employee request";

            $stmt = $conn->prepare("
                INSERT INTO notifications (`time`, `message`)
                VALUES (?, ?)
            ");
            $stmt->bind_param("ss", $time, $message);
            $stmt->execute();
            $stmt->close();

            echo json_encode(["response" => true]);
        } else {
            echo json_encode(["response" => false]);
        }
    } else {
        echo json_encode(["response" => false]);
    }
}

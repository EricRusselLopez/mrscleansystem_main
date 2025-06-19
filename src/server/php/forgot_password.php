<?php
header("Content-Type: application/json");
define('ACCESS_ALLOWED', true);
require_once "../php/conn.php";
$mail_config = require_once "../php/email_config.php";
session_start();

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer-master/src/PHPMailer.php';
require 'PHPMailer-master/src/SMTP.php';
require 'PHPMailer-master/src/Exception.php';

$token = ";;mslaundryshop2025;;";

if (
    $_SERVER['REQUEST_METHOD'] !== 'POST' ||
    !isset($_POST['client_fetch_token_request']) ||
    $_POST['client_fetch_token_request'] !== $token
) {
    echo json_encode(["response" => false, "error" => "Invalid request"]);
    exit;
}

$action = $_POST['action'] ?? '';
switch ($action) {
    case 'request':
        $email  = trim($_POST['email']  ?? '');
        $branch = trim($_POST['branch'] ?? '');

        if ($email === '' || $branch === '') {
            echo json_encode(["response" => false, "error" => "Missing email or branch"]);
            exit;
        }

        $stmt = $conn->prepare(
            "SELECT email FROM user_credentials WHERE email = ? AND branch = ?"
        );
        $stmt->bind_param("ss", $email, $branch);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows === 0) {
            echo json_encode(["response" => false, "error" => "No account found."]);
            exit;
        }
        $stmt->close();

        $code = rand(100000, 999999);
        $_SESSION['fp_email_code']       = $code;
        $_SESSION['fp_email_to_verify']  = $email;
        $_SESSION['fp_branch_to_verify'] = $branch;

        try {
            $mail = new PHPMailer(true);
            $mail->isSMTP();
            $mail->Host       = 'smtp.gmail.com';
            $mail->SMTPAuth   = true;
            $mail->Username   = $mail_config['username'];
            $mail->Password   = $mail_config['password'];
            $mail->SMTPSecure = 'tls';
            $mail->Port       = 587;

            $mail->setFrom($mail_config['username'], 'Mrs. Clean Laundry Ease');
            $mail->addAddress($email);
            $mail->isHTML(true);
            $mail->Subject = 'Your Password Reset Code';
            $mail->Body    = "
                <div style='font-family:Arial,sans-serif;padding:20px;background:#f9f9f9;border-radius:8px;'>
                  <h2>Password Reset Verification</h2>
                  <p>Your one-time code is:</p>
                  <p style='font-size:24px;font-weight:bold;color:#28a745;'>{$code}</p>
                  <p>If you didn't request this, please ignore.</p>
                  <hr>
                  <p style='font-size:12px;color:#999;'>&copy; " . date('Y') . " Mrs. Clean Laundry Ease</p>
                </div>
            ";
            $mail->send();

            echo json_encode(["response" => true]);
        } catch (Exception $e) {
            echo json_encode(["response" => false, "error" => $mail->ErrorInfo]);
        }
        exit;

    case 'verify_code':
        $code   = $_POST['code']   ?? '';
        $email  = $_POST['email']  ?? '';
        $branch = $_POST['branch'] ?? '';

        if (
            empty($_SESSION['fp_email_code']) ||
            $code != $_SESSION['fp_email_code'] ||
            $email !== $_SESSION['fp_email_to_verify'] ||
            $branch !== $_SESSION['fp_branch_to_verify']
        ) {
            echo json_encode(["response" => false]);
        } else {
            echo json_encode(["response" => true]);
        }
        exit;

    case 'reset':
        $email    = $_POST['email']           ?? '';
        $branch   = $_POST['branch']          ?? '';
        $newPass  = $_POST['new_password']    ?? '';
        $confirm  = $_POST['confirm_password'] ?? '';

        if (
            empty($_SESSION['fp_email_code']) ||
            $email !== $_SESSION['fp_email_to_verify'] ||
            $branch !== $_SESSION['fp_branch_to_verify'] ||
            $newPass === '' ||
            $newPass !== $confirm
        ) {
            echo json_encode(["response" => false, "error" => "Validation failed"]);
            exit;
        }

        $hash = password_hash($newPass, PASSWORD_DEFAULT);
        $stmt = $conn->prepare(
            "UPDATE user_credentials SET password = ? WHERE email = ? AND branch = ?"
        );
        $stmt->bind_param("sss", $hash, $email, $branch);

        if ($stmt->execute()) {
            unset(
                $_SESSION['fp_email_code'],
                $_SESSION['fp_email_to_verify'],
                $_SESSION['fp_branch_to_verify']
            );
            echo json_encode(["response" => true]);
        } else {
            echo json_encode(["response" => false, "error" => "DB update failed"]);
        }
        $stmt->close();
        exit;

    case 'cancel':
        unset(
            $_SESSION['fp_email_code'],
            $_SESSION['fp_email_to_verify'],
            $_SESSION['fp_branch_to_verify']
        );
        echo json_encode(["response" => true]);
        exit;

    default:
        echo json_encode(["response" => false, "error" => "Unknown action"]);
        exit;
}

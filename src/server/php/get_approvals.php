<?php
header("Content-Type: application/json");
define('ACCESS_ALLOWED', true);
require_once "../php/conn.php";
$mail_config = require_once "../php/email_config.php";

require 'PHPMailer-master/src/PHPMailer.php';
require 'PHPMailer-master/src/SMTP.php';
require 'PHPMailer-master/src/Exception.php';

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

$token = ";;mslaundryshop2025;;";

if ($_SERVER['REQUEST_METHOD'] !== 'POST' || !isset($_POST['client_fetch_token_request']) || $_POST['client_fetch_token_request'] !== $token) {
    echo json_encode(["response" => false]);
    exit;
} else {
    if ($_POST['action'] === "get") {
        $since = $_POST['since'] ?? '1970-01-01 00:00:00';

        $stmt = $conn->prepare("
                SELECT time, firstname, lastname, email, branch, gender, status
                FROM approvals
                WHERE time > ?
                ORDER BY time ASC
            ");
        $stmt->bind_param("s", $since);
        $stmt->execute();
        $result = $stmt->get_result();

        $data = [];
        while ($row = $result->fetch_assoc()) {
            $data[] = $row;
        }

        echo json_encode($data);

        $stmt->close();
        $conn->close();
    }

    if ($_POST['action'] === "approved" && isset($_POST['email'])) {
        $stmt = $conn->prepare("UPDATE approvals SET status = ? WHERE email = ? AND branch = ?");
        $status = "approved";
        $stmt->bind_param("sss", $status, $_POST['email'], $_POST['branch']);

        if ($stmt->execute()) {
            if ($stmt->affected_rows > 0) {
                $stmt = $conn->prepare("SELECT firstname, lastname, email, password, role, branch FROM approvals WHERE email = ? AND branch = ?");
                $stmt->bind_param("ss", $_POST['email'], $_POST['branch']);
                $stmt->execute();
                $result = $stmt->get_result();

                if ($row = $result->fetch_assoc()) {
                    $time = date("Y-m-d H:i:s");
                    $stmt = $conn->prepare("INSERT INTO user_credentials (firstname, lastname, email, password, role, branch) VALUES (?, ?, ?, ?, ?, ?)");
                    $stmt->bind_param("ssssss", $row['firstname'], $row['lastname'], $row['email'], $row['password'], $row['role'], $row['branch']);

                    if ($stmt->execute()) {
                        $mail = new PHPMailer(true);
                        try {
                            $mail->isSMTP();
                            $mail->Host       = 'smtp.gmail.com';
                            $mail->SMTPAuth   = true;
                            $mail->Username   = $mail_config['username'];
                            $mail->Password   = $mail_config['password'];
                            $mail->SMTPSecure = 'tls';
                            $mail->Port       = 587;

                            $mail->setFrom($mail_config['username'], 'Mrs. Clean Laundry Ease');
                            $mail->addAddress($row['email'], $row['firstname'] . ' ' . $row['lastname']);
                            $mail->isHTML(true);
                            $mail->Subject = 'Your Account Has Been Approved!';
                            $mail->Body = '
                                <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9; border-radius: 8px;">
                                    <div style="text-align: center;">
                                        <img src="https://i.imgur.com/xmdt55d.png" alt="Logo" style="max-width: 200px; margin-bottom: 20px;">
                                        <h2 style="color: #28a745;">Welcome to Mrs. Clean Laundry Ease!</h2>
                                    </div>
                                    <p>Dear <b>' . htmlspecialchars($row['firstname'] . ' ' . $row['lastname']) . '</b>,</p>
                                    <p>Your account has been <strong>approved</strong> and is now active. You can now log in and start using the system based on your assigned role: <b>' . $row['role'] . '</b>.</p>
                                    <p style="margin-top: 20px;">If you have any questions or need help, feel free to contact the admin.</p>
                                    <hr>
                                    <p style="font-size: 12px; color: #777;">&copy; ' . date("Y") . ' Mrs. Clean Laundry Ease. All rights reserved.</p>
                                </div>
                            ';

                            $mail->send();
                        } catch (Exception $e) {
                            error_log("Approval email error: " . $mail->ErrorInfo);
                        }

                        echo json_encode(["response" => true]);
                    } else {
                        echo json_encode(["response" => false, "error" => "Insert failed: " . $stmt->error]);
                    }
                } else {
                    echo json_encode(["response" => false, "error" => "User data not found."]);
                }

                $stmt->close();
                $conn->close();
            } else {
                echo json_encode(["response" => false, "error" => "No rows updated."]);
            }
        } else {
            echo json_encode(["response" => false, "error" => "Approval failed: " . $stmt->error]);
        }
    } else if ($_POST['action'] === "reject" && isset($_POST['email']) && isset($_POST['branch'])) {
        $stmt = $conn->prepare(
            "SELECT firstname, lastname 
                FROM approvals 
                WHERE email = ? AND branch = ?"
        );
        $stmt->bind_param("ss", $_POST['email'], $_POST['branch']);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($user = $result->fetch_assoc()) {
            $fname = $user['firstname'];
            $lname = $user['lastname'];
            $stmt->close();

            $stmt = $conn->prepare(
                "DELETE FROM approvals 
                    WHERE email = ? AND branch = ?"
            );
            $stmt->bind_param("ss", $_POST['email'], $_POST['branch']);

            if ($stmt->execute() && $stmt->affected_rows > 0) {
                $mail = new PHPMailer(true);
                try {
                    $mail->isSMTP();
                    $mail->Host       = 'smtp.gmail.com';
                    $mail->SMTPAuth   = true;
                    $mail->Username   = $mail_config['username'];
                    $mail->Password   = $mail_config['password'];
                    $mail->SMTPSecure = 'tls';
                    $mail->Port       = 587;

                    $mail->setFrom($mail_config['username'], 'Mrs. Clean Laundry Ease');
                    $mail->addAddress($_POST['email'], "$fname $lname");
                    $mail->isHTML(true);
                    $mail->Subject = 'Account Request Rejected - Mrs. Clean Laundry Ease';
                    $mail->Body    = '
                            <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9; border-radius: 8px;">
                            <div style="text-align: center;">
                                <img src="https://i.imgur.com/xmdt55d.png" alt="Logo" 
                                    style="max-width: 200px; margin-bottom: 20px;">
                                <h2 style="color: #dc3545;">Account Request Rejected</h2>
                            </div>
                            <p>Dear <b>' . htmlspecialchars("$fname $lname") . '</b>,</p>
                            <p>Thank you for your interest in joining <b>Mrs. Clean Laundry Ease</b>. 
                                After careful review, we’re unable to approve your request at this time 
                                because your details do not match our current employee records.</p>
                            <p>If you believe this is an error or wish to update your information, 
                                please feel free to submit a new request.</p>
                            <p style="margin-top: 20px;">
                                If you have any questions or need assistance, contact our administrator. 
                                We’re happy to help.</p>
                            <hr>
                            <p style="font-size: 12px; color: #777;">
                                &copy; ' . date("Y") . ' Mrs. Clean Laundry Ease. All rights reserved.
                            </p>
                            </div>
                        ';

                    $mail->send();
                    echo json_encode(["response" => true]);
                } catch (Exception $e) {
                    error_log("Rejection email error: " . $mail->ErrorInfo);
                    echo json_encode(["response" => false, "error" => "Email send failed."]);
                }
            } else {
                echo json_encode(["response" => false, "error" => "Deletion failed or no matching record."]);
            }
        } else {
            echo json_encode(["response" => false, "error" => "User not found in approvals."]);
        }

        $stmt->close();
        $conn->close();
    } else if (
        $_POST['action'] === "remove"
        && isset($_POST['email'])
        && isset($_POST['branch'])
    ) {
        $email  = $_POST['email'];
        $branch = $_POST['branch'];

        $stmt = $conn->prepare(
            "SELECT firstname, lastname
                FROM (
                SELECT firstname, lastname, email, branch FROM approvals
                UNION ALL
                SELECT firstname, lastname, email, branch FROM user_credentials
                ) AS combined
                WHERE email = ? AND branch = ?"
        );
        $stmt->bind_param("ss", $email, $branch);
        $stmt->execute();
        $result = $stmt->get_result();

        if (! $user = $result->fetch_assoc()) {
            echo json_encode(["response" => false, "error" => "User not found."]);
            $stmt->close();
            $conn->close();
            exit;
        }
        $fname = $user['firstname'];
        $lname = $user['lastname'];
        $stmt->close();

        foreach (
            [
                "DELETE FROM approvals WHERE email = ? AND branch = ?",
                "DELETE FROM user_credentials WHERE email = ? AND branch = ?"
            ] as $sql
        ) {
            $stmt = $conn->prepare($sql);
            $stmt->bind_param("ss", $email, $branch);
            $stmt->execute();
            $stmt->close();
        }

        $mail = new PHPMailer(true);
        try {
            $mail->isSMTP();
            $mail->Host       = 'smtp.gmail.com';
            $mail->SMTPAuth   = true;
            $mail->Username   = $mail_config['username'];
            $mail->Password   = $mail_config['password'];
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
            $mail->Port       = 587;

            $mail->setFrom($mail_config['username'], 'Mrs. Clean Laundry Ease');
            $mail->addAddress($email, "$fname $lname");
            $mail->isHTML(true);
            $mail->Subject = 'Account Removed - Mrs. Clean Laundry Ease';
            $mail->Body    = '
                <div style="font-family: Arial, sans-serif; padding:20px; background:#f9f9f9; border-radius:8px;">
                    <div style="text-align:center;">
                    <img src="https://i.imgur.com/xmdt55d.png" alt="Logo"
                        style="max-width:200px; margin-bottom:20px;">
                    <h2 style="color:#dc3545;">Account Removed</h2>
                    </div>
                    <p>Dear <b>' . htmlspecialchars("$fname $lname") . '</b>,</p>
                    <p>Your account for branch <b>' . htmlspecialchars($branch) . '</b> has been removed from our system.</p>
                    <p>If this was in error or you wish to reapply, please contact the administrator or submit a new request.</p>
                    <p style="margin-top:20px;">Thank you for your understanding.</p>
                    <hr>
                    <p style="font-size:12px; color:#777;">&copy; ' . date("Y") . ' Mrs. Clean Laundry Ease. All rights reserved.</p>
                </div>';

            $mail->send();
            echo json_encode(["response" => true]);
        } catch (Exception $e) {
            error_log("Removal email error: " . $mail->ErrorInfo);
            echo json_encode(["response" => false, "error" => "Notification email failed."]);
        }

        $conn->close();
        exit;
    }
}

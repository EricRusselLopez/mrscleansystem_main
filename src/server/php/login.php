<?php
header("Content-Type: application/json");
define('ACCESS_ALLOWED', true);
require_once "../php/conn.php";
$mail_config = require_once "../php/email_config.php";

$token        = ";;mslaundryshop2025;;";
$securityPIN  = $mail_config['owner_pin'];

if (
    $_SERVER['REQUEST_METHOD'] !== 'POST'
    || !isset($_POST['client_fetch_token_request'])
    || $_POST['client_fetch_token_request'] !== $token
) {
    echo json_encode([
        "response" => false,
        "message"  => "Invalid request."
    ]);
    exit;
}

// 1) SECURITY PIN CHECK (owner only)
if (isset($_POST['securityRequest'])) {
    if ($_POST['securityRequest'] === $securityPIN) {
        echo json_encode(["response" => true]);
    } else {
        echo json_encode([
            "response" => false,
            "message"  => "Incorrect security PIN."
        ]);
    }
    exit;
}

// 2) LOGIN CHECK
if (! isset($_POST['email'], $_POST['password'])) {
    echo json_encode([
        "response" => false,
        "message"  => "Email and password required."
    ]);
    exit;
}

$email = $_POST['email'];
$pass  = $_POST['password'];

$stmt = $conn->prepare("
        SELECT email
            , password
            , role
            , branch
            , firstname
            , lastname
        FROM user_credentials
        WHERE email = ?
        AND (branch = ? OR branch = 'all')
    ");
$stmt->bind_param("ss", $email, $_POST["branch"]);
$stmt->execute();
$result = $stmt->get_result();
if ($result && $result->num_rows === 1) {
    $data = $result->fetch_assoc();
    if (password_verify($pass, $data['password'])) {
        unset($data['password']);
        echo json_encode(array_merge([
            "response" => true
        ], $data));
    } else {
        echo json_encode([
            "response" => false,
            "message"  => "Incorrect email or password."
        ]);
    }
} else {
    echo json_encode([
        "response" => false,
        "message"  => "Incorrect email or password."
    ]);
}


$stmt->close();
$conn->close();
exit;

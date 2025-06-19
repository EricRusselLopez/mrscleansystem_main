<?php
header("Content-Type: application/json");
define('ACCESS_ALLOWED', true);
require_once "../php/conn.php";


$token = ";;mslaundryshop2025;;";

if (
    $_SERVER['REQUEST_METHOD'] !== 'POST'
    || !isset($_POST['email'], $_POST['password'], $_POST['role'], $_POST['branch'], $_POST['client_fetch_token_request'])
    || $_POST['client_fetch_token_request'] !== $token
) {
    echo json_encode(["response" => false]);
    exit;
}

$email  = $_POST['email'];
$pass   = $_POST['password'];
$role   = $_POST['role'];
$branch = $_POST['branch'];

$stmt = $conn->prepare("
    SELECT firstname, lastname, email, password, role, branch
      FROM user_credentials
     WHERE email  = ?
       AND role   = ?
       AND branch = ?
     LIMIT 1
");
$stmt->bind_param("sss", $email, $role, $branch);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 1) {
    $data = $result->fetch_assoc();

    if (password_verify($pass, $data['password'])) {
        unset($data['password']);
        echo json_encode(array_merge(["response" => true], $data));
    } else {
        echo json_encode(["response" => false]);
    }
} else {
    echo json_encode(["response" => false]);
}

$stmt->close();
$conn->close();

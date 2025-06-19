<?php
header("Content-Type: application/json");
define('ACCESS_ALLOWED', true);
require_once "../php/conn.php";

$token = ";;mslaundryshop2025;;";

if ($_SERVER['REQUEST_METHOD'] !== 'POST' || !isset($_POST['client_fetch_token_request']) || $_POST['client_fetch_token_request'] !== $token) {
    echo json_encode(["response" => false]);
    exit;
} else {
    if ($_POST['action'] === "get") {
        $since = $_POST['since'] ?? '1970-01-01 00:00:00';
        $stmt = $conn->prepare("
                SELECT id, time, message
                FROM notifications
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
    } else if ($_POST['action'] === "delete") {
        $stmt = $conn->prepare("DELETE FROM notifications WHERE id = ?");
        $stmt->bind_param("i", $_POST['id']);
        if ($stmt->execute()) {
            echo json_encode(value: ["response" => true]);
        } else {
            echo json_encode(["response" => false]);
        }
        $stmt->close();
    }
}

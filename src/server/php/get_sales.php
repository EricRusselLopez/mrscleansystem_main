<?php
header("Content-Type: application/json");
define('ACCESS_ALLOWED', true);
require_once "../php/conn.php";
$token = ";;mslaundryshop2025;;";

if ($_SERVER['REQUEST_METHOD'] !== 'POST' || !isset($_POST['client_fetch_token_request']) || $_POST['client_fetch_token_request'] !== $token) {
    echo json_encode(["response" => false]);
    exit;
} else {
    if ($_POST['action'] === 'get') {
        $since = $_POST['since'] ?? '1970-01-01';

        $stmt = $conn->prepare("
            SELECT time, month_name, month_number, year, branch, total_sales
            FROM statistics
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
}

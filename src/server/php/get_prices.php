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
                SELECT time, service_name, price_per_kilo, branch, status
                FROM pricing
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
    } else if ($_POST['action'] === "update_status") {
        $stmt = $conn->prepare("UPDATE pricing SET status = ? WHERE service_name = ? AND price_per_kilo = ? AND branch = ?");
        $stmt->bind_param("ssss", $_POST['status'], $_POST['service_name'], $_POST['price_per_kilo'], $_POST['branch']);
        if ($stmt->execute()) {
            echo json_encode(["response" => true]);
        } else {
            echo json_encode(["response" => false]);
        }
        $stmt->close();
    } else if ($_POST['action'] === "update") {
        $newservice = trim($_POST['newservice']);
        $newprice = trim($_POST['newprice']);
        $oldservice = trim($_POST['oldservice']);
        $oldprice = trim($_POST['oldprice']);

        $stmt = $conn->prepare("
                UPDATE pricing
                SET service_name = ?, price_per_kilo = ?
                WHERE service_name = ? AND price_per_kilo = ? AND branch = ?
            ");
        $stmt->bind_param("sssss", $newservice, $newprice, $oldservice, $oldprice, $_POST['branch']);
        $stmt->execute();
        $stmt->close();
        echo json_encode(["response" => true]);
    }
}

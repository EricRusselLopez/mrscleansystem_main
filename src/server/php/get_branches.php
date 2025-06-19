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
        $data = [];
        $stmt = $conn->prepare("SELECT branchid, name FROM branches");
        $stmt->execute();
        $result = $stmt->get_result();
        while ($row = $result->fetch_assoc()) {
            $data[] = $row;
        }
        echo json_encode($data);

        $stmt->close();
        $conn->close();
    } else if (
        $_POST['action'] === "update"
        && isset($_POST['newbranchid'], $_POST['newname'], $_POST['oldbranchid'], $_POST['oldbranchname'])
    ) {
        $newId      = trim($_POST['newbranchid']);
        $newName    = trim($_POST['newname']);
        $oldId      = trim($_POST['oldbranchid']);
        $oldName    = trim($_POST['oldbranchname']);

        $chk = $conn->prepare("
                SELECT 1
                FROM branches
                WHERE (branchid = ? OR name = ?)
                AND NOT (branchid = ? AND name = ?)
                LIMIT 1
            ");
        $chk->bind_param("ssss", $newId, $newName, $oldId, $oldName);
        $chk->execute();
        $chk->store_result();

        if ($chk->num_rows > 0) {
            echo json_encode([
                "response" => false,
                "error"    => "Another branch with that ID or name already exists."
            ]);
            $chk->close();
            $conn->close();
            exit;
        }
        $chk->close();

        $stmt = $conn->prepare("
                UPDATE branches
                SET branchid = ?, name = ?
                WHERE branchid = ? AND name = ?
            ");
        $stmt->bind_param("ssss", $newId, $newName, $oldId, $oldName);
        $stmt->execute();
        $stmt->close();

        $newBranchValue = "{$newId} ({$newName})";
        $oldBranchValue = "{$oldId} ({$oldName})";

        $stmt = $conn->prepare("
                UPDATE user_credentials
                SET branch = ?
                WHERE branch = ?
            ");
        $stmt->bind_param("ss", $newBranchValue, $oldBranchValue);
        $stmt->execute();
        $stmt->close();

        $stmt = $conn->prepare("
                UPDATE approvals
                SET branch = ?
                WHERE branch = ?
            ");
        $stmt->bind_param("ss", $newBranchValue, $oldBranchValue);
        $stmt->execute();
        $stmt->close();

        echo json_encode(["response" => true]);
        $conn->close();
        exit;
    } else if ($_POST['action'] === "add") {
        $newId   = trim($_POST['newbranchid']  ?? '');
        $newName = trim($_POST['newname']      ?? '');

        if ($newId === '' || $newName === '') {
            echo json_encode([
                "response" => false,
                "error"    => "Branch ID and name cannot be empty."
            ]);
            exit;
        }

        $chk = $conn->prepare("
                SELECT 1
                FROM branches
                WHERE branchid = ? AND name = ?
                LIMIT 1
            ");
        $chk->bind_param("ss", $newId, $newName);
        $chk->execute();
        $chk->store_result();

        if ($chk->num_rows > 0) {
            echo json_encode([
                "response" => false,
                "error"    => "That branch ID and name pair already exists."
            ]);
            $chk->close();
            $conn->close();
            exit;
        }
        $chk->close();

        $stmt = $conn->prepare(
            "INSERT INTO branches (branchid, name) VALUES (?, ?)"
        );
        $stmt->bind_param("ss", $newId, $newName);

        if ($stmt->execute() && $stmt->affected_rows > 0) {
            echo json_encode(["response" => true]);
        } else {
            echo json_encode([
                "response" => false,
                "error"    => $stmt->error ?: "Insert failed."
            ]);
        }

        $stmt->close();
        $conn->close();
        exit;
    } else if ($_POST['action'] === "remove") {
        $stmt = $conn->prepare(
            "DELETE FROM branches WHERE branchid = ? AND name = ?"
        );

        $stmt->bind_param(
            "ss",
            $_POST['branchid'],
            $_POST['name'],
        );

        if ($stmt->execute() && $stmt->affected_rows > 0) {
            echo json_encode(["response" => true]);
        } else {
            echo json_encode([
                "response" => false,
                "error"    => $stmt->error ?: "No rows updated."
            ]);
        }

        $stmt->close();
        $conn->close();
    }
}

<?php

if (!defined('ACCESS_ALLOWED')) {
    die("Restricted Access");
}

$host = "localhost";
$user = "root";
$password = "";
$db = "mrscleanlaundryease";

$conn = new mysqli($host, $user, $password, $db);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
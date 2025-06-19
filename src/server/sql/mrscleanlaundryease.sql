-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 19, 2025 at 09:33 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mrscleanlaundryease`
--

-- --------------------------------------------------------

--
-- Table structure for table `approvals`
--

CREATE TABLE `approvals` (
  `id` int(11) NOT NULL,
  `time` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `branch` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `branches`
--

CREATE TABLE `branches` (
  `id` int(11) NOT NULL,
  `branchid` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `branches`
--

INSERT INTO `branches` (`id`, `branchid`, `name`) VALUES
(1, 'Main', 'Baliwag'),
(10, 'Branch 2', 'San Rafael');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `time` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `quantity` int(255) NOT NULL,
  `threshold` int(255) NOT NULL,
  `branch` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `last_restock` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`id`, `time`, `item_name`, `quantity`, `threshold`, `branch`, `status`, `last_restock`) VALUES
(42, '2025-06-19 09:31:19', 'Surf', 1000, 1000, 'Main (Baliwag)', 'Normal', '2025-06-19 09:31:19'),
(43, '2025-06-19 09:31:19', 'Surf', 1000, 1000, 'Branch 2 (San Rafael)', 'Normal', '2025-06-19 09:31:19'),
(44, '2025-06-19 09:31:34', 'Zonrox (ml)', 5000, 5000, 'Main (Baliwag)', 'Normal', '2025-06-19 09:31:34'),
(45, '2025-06-19 09:31:34', 'Zonrox (ml)', 5000, 5000, 'Branch 2 (San Rafael)', 'Normal', '2025-06-19 09:31:34'),
(46, '2025-06-19 09:31:48', 'Tag', 1000, 1000, 'Main (Baliwag)', 'Normal', '2025-06-19 09:31:48'),
(47, '2025-06-19 09:31:48', 'Tag', 1000, 1000, 'Branch 2 (San Rafael)', 'Normal', '2025-06-19 09:31:48'),
(48, '2025-06-19 09:32:01', 'Plastic Bag', 500, 500, 'Main (Baliwag)', 'Normal', '2025-06-19 09:32:01'),
(49, '2025-06-19 09:32:01', 'Plastic Bag', 500, 500, 'Branch 2 (San Rafael)', 'Normal', '2025-06-19 09:32:01'),
(50, '2025-06-19 09:32:17', 'Fabric', 1000, 1000, 'Main (Baliwag)', 'Normal', '2025-06-19 09:32:17'),
(51, '2025-06-19 09:32:17', 'Fabric', 1000, 1000, 'Branch 2 (San Rafael)', 'Normal', '2025-06-19 09:32:17');

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `time` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pricing`
--

CREATE TABLE `pricing` (
  `id` int(11) NOT NULL,
  `time` varchar(255) NOT NULL,
  `service_name` varchar(255) NOT NULL,
  `price_per_kilo` varchar(255) NOT NULL,
  `branch` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pricing`
--

INSERT INTO `pricing` (`id`, `time`, `service_name`, `price_per_kilo`, `branch`, `status`) VALUES
(1, '2025-06-11 19:00:00', 'Wash', '60', 'Main (Baliwag)', '1'),
(2, '2025-06-11 19:00:00', 'Dry', '70', 'Main (Baliwag)', '1'),
(3, '2025-06-11 19:00:00', 'Fold', '40', 'Main (Baliwag)', '1'),
(4, '2025-06-11 19:00:00', 'Drop-off', '10', 'Main (Baliwag)', '1'),
(5, '2025-06-11 19:00:00', 'DIY', '5', 'Main (Baliwag)', '1'),
(6, '2025-06-11 19:00:00', 'Dry Clean', '60', 'Main (Baliwag)', '0'),
(7, '2025-06-11 19:00:00', 'Classic Wash', '200', 'Main (Baliwag)', '1'),
(8, '2025-06-11 19:00:00', 'Premium Wash', '240', 'Main (Baliwag)', '1'),
(9, '2025-06-11 19:00:00', 'Pick-UP and DELIVER', '40', 'Main (Baliwag)', '0'),
(10, '2025-06-11 19:00:00', 'Wash', '60', 'Branch 2 (San Rafael)', '1'),
(11, '2025-06-11 19:00:00', 'Dry', '70', 'Branch 2 (San Rafael)', '1'),
(12, '2025-06-11 19:00:00', 'Fold', '40', 'Branch 2 (San Rafael)', '1'),
(13, '2025-06-11 19:00:00', 'Drop-off', '10', 'Branch 2 (San Rafael)', '1'),
(14, '2025-06-11 19:00:00', 'DIY', '5', 'Branch 2 (San Rafael)', '1'),
(15, '2025-06-11 19:00:00', 'Dry Clean', '60', 'Branch 2 (San Rafael)', '0'),
(16, '2025-06-11 19:00:00', 'Classic Wash', '200', 'Branch 2 (San Rafael)', '1'),
(17, '2025-06-11 19:00:00', 'Premium Wash', '240', 'Branch 2 (San Rafael)', '1'),
(18, '2025-06-11 19:00:00', 'Pick-UP and DELIVER', '40', 'Branch 2 (San Rafael)', '0');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `id` int(11) NOT NULL,
  `transaction_id` varchar(255) NOT NULL,
  `time` varchar(255) NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `servicetype` varchar(255) NOT NULL,
  `c_pay` varchar(255) NOT NULL,
  `c_change` varchar(255) NOT NULL,
  `total_amount` varchar(255) NOT NULL,
  `receipt` varchar(1000) NOT NULL,
  `branch` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `statistics`
--

CREATE TABLE `statistics` (
  `id` int(11) NOT NULL,
  `time` varchar(255) NOT NULL,
  `month_name` varchar(20) NOT NULL,
  `month_number` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `total_sales` decimal(10,2) NOT NULL,
  `branch` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_credentials`
--

CREATE TABLE `user_credentials` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `branch` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_credentials`
--

INSERT INTO `user_credentials` (`id`, `firstname`, `lastname`, `email`, `password`, `role`, `branch`) VALUES
(20, 'Owner', 'Owner', 'russel@gmail.com', '$2y$10$q40maXHOJB4WBCsMs0GWRuXEVWCJRrtJI9kBXKxNdHGaUKCDN9FPK', 'owner', 'all'),
(34, 'R', 'R', 'r@gmail.com', '$2y$10$q40maXHOJB4WBCsMs0GWRuXEVWCJRrtJI9kBXKxNdHGaUKCDN9FPK', 'employee', 'Main (Baliwag)'),
(35, 'r', 'r', 'r@gmail.com', '$2y$10$q40maXHOJB4WBCsMs0GWRuXEVWCJRrtJI9kBXKxNdHGaUKCDN9FPK', 'employee', 'Branch 2 (San Rafael)');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `approvals`
--
ALTER TABLE `approvals`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `branches`
--
ALTER TABLE `branches`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pricing`
--
ALTER TABLE `pricing`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `statistics`
--
ALTER TABLE `statistics`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_monthly_branch` (`month_number`,`year`,`branch`);

--
-- Indexes for table `user_credentials`
--
ALTER TABLE `user_credentials`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `approvals`
--
ALTER TABLE `approvals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `branches`
--
ALTER TABLE `branches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=126;

--
-- AUTO_INCREMENT for table `pricing`
--
ALTER TABLE `pricing`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;

--
-- AUTO_INCREMENT for table `statistics`
--
ALTER TABLE `statistics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `user_credentials`
--
ALTER TABLE `user_credentials`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

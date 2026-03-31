-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: mysql-db
-- Generation Time: Mar 31, 2026 at 04:02 PM
-- Server version: 9.6.0
-- PHP Version: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `product-service`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Computers'),
(2, 'Printers'),
(3, 'Smart phones');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` bigint NOT NULL,
  `available` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo_name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `promotion` bit(1) NOT NULL,
  `selected` bit(1) NOT NULL,
  `stock` bigint NOT NULL,
  `category_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `available`, `description`, `name`, `photo_name`, `price`, `promotion`, `selected`, `stock`, `category_id`) VALUES
(1, b'0', NULL, 'java.util.Random@1435103b', '1.png', 2609, b'0', b'0', 5, 1),
(2, b'0', NULL, 'java.util.Random@1435103b', '2.png', 4778, b'1', b'0', 5, 1),
(3, b'0', NULL, 'java.util.Random@1435103b', '3.png', 6107, b'1', b'0', 5, 1),
(4, b'0', NULL, 'java.util.Random@1435103b', '4.png', 7856, b'0', b'1', 5, 2),
(5, b'0', NULL, 'java.util.Random@1435103b', '5.png', 2316, b'0', b'0', 5, 2),
(6, b'1', NULL, 'java.util.Random@1435103b', '6.png', 462, b'1', b'0', 5, 2),
(7, b'1', NULL, 'java.util.Random@1435103b', '7.png', 3085, b'1', b'1', 5, 3),
(8, b'1', NULL, 'java.util.Random@1435103b', '8.png', 9421, b'0', b'0', 5, 3),
(9, b'0', NULL, 'java.util.Random@1435103b', '9.png', 2755, b'1', b'0', 5, 3),
(14, b'1', ',ghhhhhhhhhh', 'nnnnnnnvvvvvvvvvvvvvvvvvvv', '14.png', 66, b'1', b'1', 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

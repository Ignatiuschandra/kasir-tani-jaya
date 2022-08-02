-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 02, 2022 at 11:27 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasir_tani_jaya`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id` int(11) NOT NULL,
  `kode` varchar(161) NOT NULL,
  `nama` varchar(161) NOT NULL,
  `satuan_id` int(11) NOT NULL,
  `harga_beli` double(18,0) NOT NULL DEFAULT 0,
  `harga_jual` double(18,0) NOT NULL DEFAULT 0,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id`, `kode`, `nama`, `satuan_id`, `harga_beli`, `harga_jual`, `deleted_at`, `created_at`, `updated_at`) VALUES
(1, 'kode123', 'Tes Nama Barang Edit Lagi', 2, 22000, 60000, NULL, '2021-05-26 10:07:56', NULL),
(2, '2u4j4uj4', 'TEssfsdafd', 2, 23000, 45000, NULL, '2021-05-30 13:14:21', NULL),
(3, 'B123', 'Bungkil', 2, 25000, 27000, '2022-05-01 08:28:00', '2021-05-31 09:36:34', NULL),
(4, 'B456', 'Bungkil', 3, 50000, 60000, NULL, '2021-05-31 09:37:17', NULL),
(5, 'WKS', 'Whiskas', 4, 20000, 25000, NULL, '2022-04-29 20:06:37', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `satuan`
--

CREATE TABLE `satuan` (
  `id` int(11) NOT NULL,
  `satuan` varchar(161) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `satuan`
--

INSERT INTO `satuan` (`id`, `satuan`, `created_at`, `updated_at`) VALUES
(1, 'Ons', '2021-05-26 06:30:02', NULL),
(2, 'Kg', '2021-05-26 06:30:02', NULL),
(3, 'Karung', '2021-05-26 06:30:02', NULL),
(4, 'Pcs', '2021-05-26 06:30:02', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id` varchar(20) NOT NULL,
  `pelanggan` varchar(100) DEFAULT NULL,
  `total` double(18,0) NOT NULL DEFAULT 0,
  `uang_diserahkan` double(18,0) NOT NULL DEFAULT 0,
  `potongan` double(18,0) NOT NULL DEFAULT 0,
  `is_hutang` tinyint(4) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `pelanggan`, `total`, `uang_diserahkan`, `potongan`, `is_hutang`, `created_at`, `updated_at`) VALUES
('TRX1656306835632', 'dadang', 145000, 150000, 0, 0, '2022-06-27 05:13:55', '2022-06-27 05:13:55'),
('TRX1656306982127', '', 75000, 100000, 0, 0, '2022-06-27 05:16:22', '2022-06-27 05:16:22'),
('TRX1656329144749', 'Dadang', 25000, 20000, 0, 1, '2022-06-27 11:25:44', '2022-06-27 11:25:44'),
('TRX1656329309902', '', 50000, 100000, 0, 0, '2022-06-27 11:28:29', '2022-06-27 11:28:29'),
('TRX1656329335645', '', 180000, 150000, 0, 1, '2022-06-27 11:28:55', '2022-06-27 11:28:55');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_detail`
--

CREATE TABLE `transaksi_detail` (
  `transaksi_id` varchar(20) NOT NULL,
  `barang_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `harga_beli` double(18,0) NOT NULL,
  `harga_jual` double(18,0) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi_detail`
--

INSERT INTO `transaksi_detail` (`transaksi_id`, `barang_id`, `qty`, `harga_beli`, `harga_jual`, `created_at`, `updated_at`) VALUES
('TRX1656306835632', 5, 1, 20000, 25000, '2022-06-27 05:13:55', '2022-06-27 05:13:55'),
('TRX1656306835632', 4, 2, 50000, 60000, '2022-06-27 05:13:55', '2022-06-27 05:13:55'),
('TRX1656306982127', 5, 3, 20000, 25000, '2022-06-27 05:16:22', '2022-06-27 05:16:22'),
('TRX1656329144749', 5, 1, 20000, 25000, '2022-06-27 11:25:44', '2022-06-27 11:25:44'),
('TRX1656329309902', 5, 2, 20000, 25000, '2022-06-27 11:28:30', '2022-06-27 11:28:30'),
('TRX1656329335645', 4, 3, 50000, 60000, '2022-06-27 11:28:55', '2022-06-27 11:28:55');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id`),
  ADD KEY `deleted_at` (`deleted_at`);

--
-- Indexes for table `satuan`
--
ALTER TABLE `satuan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaksi_detail`
--
ALTER TABLE `transaksi_detail`
  ADD KEY `transaksi_id` (`transaksi_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `satuan`
--
ALTER TABLE `satuan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

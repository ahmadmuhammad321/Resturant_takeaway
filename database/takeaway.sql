-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 23, 2022 at 08:13 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `takeaway`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbladmin`
--

CREATE TABLE `tbladmin` (
  `ADMIN_ID` int(11) NOT NULL,
  `ADMIN_NAME` varchar(45) NOT NULL,
  `ADMIN_EMAIL` varchar(45) NOT NULL,
  `ADMIN_PASSWORD` varchar(45) NOT NULL,
  `ADMIN_STATUS` varchar(45) DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  `ISACTIVE` char(1) DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED_WHEN` datetime DEFAULT NULL,
  `MODIFIED_WORKSTATION` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbladmin`
--

INSERT INTO `tbladmin` (`ADMIN_ID`, `ADMIN_NAME`, `ADMIN_EMAIL`, `ADMIN_PASSWORD`, `ADMIN_STATUS`, `MENU_ID`, `ISACTIVE`, `MODIFIED_BY`, `MODIFIED_WHEN`, `MODIFIED_WORKSTATION`) VALUES
(1, 'ADMIN_1', 'admin_1@gmail.com', 'admin_1pass', NULL, NULL, 'Y', NULL, NULL, NULL),
(2, 'ADMIN_2', 'admin_2@gmail.com', 'admin_2pass', NULL, NULL, 'N', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tblchef`
--

CREATE TABLE `tblchef` (
  `CHEF_ID` int(11) NOT NULL,
  `CHEF_NAME` varchar(45) NOT NULL,
  `CHEF_EMAIL` varchar(45) NOT NULL,
  `CHEF_PASSWORD` varchar(45) NOT NULL,
  `ORDER_ID` int(11) NOT NULL,
  `ISACTIVE` char(1) DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED_WHEN` datetime DEFAULT NULL,
  `MODIFIED_WORKSTATION` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblchef`
--

INSERT INTO `tblchef` (`CHEF_ID`, `CHEF_NAME`, `CHEF_EMAIL`, `CHEF_PASSWORD`, `ORDER_ID`, `ISACTIVE`, `MODIFIED_BY`, `MODIFIED_WHEN`, `MODIFIED_WORKSTATION`) VALUES
(1, 'chef_1', 'chef_1@gmail.com', 'chef_1pass', 0, 'Y', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tblcustomer`
--

CREATE TABLE `tblcustomer` (
  `CUSTOMER_ID` int(11) NOT NULL,
  `CUSTOMER_NAME` varchar(45) NOT NULL,
  `CUSTOMER_EMAIL` varchar(45) NOT NULL,
  `CUSTOMER_PHONE` int(11) DEFAULT NULL,
  `FOOD_ID` int(11) NOT NULL,
  `PAYMENT_ID` int(11) NOT NULL,
  `ISACTIVE` char(1) DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED_WHEN` datetime DEFAULT NULL,
  `MODIFIED_WORKSTATION` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tblfood`
--

CREATE TABLE `tblfood` (
  `FOOD_ID` int(11) NOT NULL,
  `FOOD_NAME` varchar(45) NOT NULL,
  `FOOD_QUANTITY` varchar(45) NOT NULL,
  `UNIT_PRICE` int(11) NOT NULL,
  `FOOD_CATEGORY` varchar(45) DEFAULT NULL,
  `ISACTIVE` char(1) DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED_WHEN` datetime DEFAULT NULL,
  `MODIFIED_WORKSTATION` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tblmenu`
--

CREATE TABLE `tblmenu` (
  `MENU_ID` int(11) NOT NULL,
  `PRICE` int(11) NOT NULL,
  `FOOD_ID` varchar(45) NOT NULL,
  `ISACTIVE` char(1) DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED_WHEN` datetime DEFAULT NULL,
  `MODIFIED_WORKSTATION` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tblorder`
--

CREATE TABLE `tblorder` (
  `ORDER_ID` int(11) NOT NULL,
  `ORDER_DATE` varchar(45) DEFAULT NULL,
  `CUSTOMER_ID` int(11) NOT NULL,
  `PICKUP_DATE` varchar(45) DEFAULT NULL,
  `ISACTIVE` char(1) DEFAULT NULL,
  `MODIFIED_BY` int(11) DEFAULT NULL,
  `MODIFIED_WHEN` datetime DEFAULT NULL,
  `MODIFIED_WORKSTATION` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tblpayment`
--

CREATE TABLE `tblpayment` (
  `PAYMENT_ID` int(11) NOT NULL,
  `CUSTOMER_ID` int(11) NOT NULL,
  `ORDER_ID` int(11) NOT NULL,
  `PAYMENT_AMOUNT` int(11) NOT NULL,
  `PAYMENT_DATE` varchar(45) DEFAULT NULL,
  `PAYMENT_TYPE` varchar(45) DEFAULT NULL,
  `ISACTIVE` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` char(1) DEFAULT NULL,
  `MODIFIED_WHEN` datetime DEFAULT NULL,
  `MODIFIED_WORKSTATION` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbladmin`
--
ALTER TABLE `tbladmin`
  ADD PRIMARY KEY (`ADMIN_ID`);

--
-- Indexes for table `tblchef`
--
ALTER TABLE `tblchef`
  ADD PRIMARY KEY (`CHEF_ID`);

--
-- Indexes for table `tblcustomer`
--
ALTER TABLE `tblcustomer`
  ADD PRIMARY KEY (`CUSTOMER_ID`);

--
-- Indexes for table `tblfood`
--
ALTER TABLE `tblfood`
  ADD PRIMARY KEY (`FOOD_ID`);

--
-- Indexes for table `tblmenu`
--
ALTER TABLE `tblmenu`
  ADD PRIMARY KEY (`MENU_ID`);

--
-- Indexes for table `tblorder`
--
ALTER TABLE `tblorder`
  ADD PRIMARY KEY (`ORDER_ID`);

--
-- Indexes for table `tblpayment`
--
ALTER TABLE `tblpayment`
  ADD PRIMARY KEY (`PAYMENT_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbladmin`
--
ALTER TABLE `tbladmin`
  MODIFY `ADMIN_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tblchef`
--
ALTER TABLE `tblchef`
  MODIFY `CHEF_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tblcustomer`
--
ALTER TABLE `tblcustomer`
  MODIFY `CUSTOMER_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tblfood`
--
ALTER TABLE `tblfood`
  MODIFY `FOOD_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tblmenu`
--
ALTER TABLE `tblmenu`
  MODIFY `MENU_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tblorder`
--
ALTER TABLE `tblorder`
  MODIFY `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tblpayment`
--
ALTER TABLE `tblpayment`
  MODIFY `PAYMENT_ID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

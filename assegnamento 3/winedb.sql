-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 20, 2020 alle 17:17
-- Versione del server: 10.4.16-MariaDB
-- Versione PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `winedb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `order`
--

CREATE TABLE `order` (
  `ID` int(10) NOT NULL,
  `USER_ID` int(6) NOT NULL,
  `WINE_ID` int(11) NOT NULL,
  `Quantity` int(100) NOT NULL,
  `Shipped` tinyint(1) UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `order`
--

INSERT INTO `order` (`ID`, `USER_ID`, `WINE_ID`, `Quantity`, `Shipped`) VALUES
(4, 6, 2, 3, 0),
(5, 6, 1, 2, 1),
(7, 3, 2, 1, 0),
(8, 6, 1, 5, 1),
(12, 6, 1, 2, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `person`
--

CREATE TABLE `person` (
  `ID` int(6) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Surname` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` int(1) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `person`
--

INSERT INTO `person` (`ID`, `Name`, `Surname`, `Email`, `Password`, `Role`) VALUES
(1, 'admin', 'admin', 'admin', '1234', 0),
(2, 'Danilo', 'Paglialunga', 'danilo.paglialunga@gmail.com', '1234', 2),
(3, 'Luca', 'Mariano', 'luca.mariano@gmail.com', '1234', 2),
(4, 'Francesco', 'Murolo', 'francesco.murolo@gmail.com', '1234', 2),
(5, 'employee', 'employee', 'employee', '1234', 1),
(6, 'user', 'user', 'user', '1234', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `request`
--

CREATE TABLE `request` (
  `ID` int(10) NOT NULL,
  `USER_ID` int(6) NOT NULL,
  `WINE_ID` int(11) NOT NULL,
  `Quantity` int(100) NOT NULL,
  `Processed` tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  `Seen` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `request`
--

INSERT INTO `request` (`ID`, `USER_ID`, `WINE_ID`, `Quantity`, `Processed`, `Seen`) VALUES
(2, 6, 3, 2, 0, 0),
(4, 6, 2, 5, 1, 1),
(7, 6, 1, 3, 1, 1),
(8, 6, 1, 5, 1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `wine`
--

CREATE TABLE `wine` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Producer` varchar(255) NOT NULL,
  `Year` varchar(4) NOT NULL DEFAULT '',
  `Notes` varchar(255) DEFAULT NULL,
  `Vines` varchar(255) NOT NULL,
  `Quantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `wine`
--

INSERT INTO `wine` (`ID`, `Name`, `Producer`, `Year`, `Notes`, `Vines`, `Quantity`) VALUES
(1, 'Negroamaro', 'Cantine Due Palme', '2019', 'PAIRING: First courses of read meat. ALCOHOL: 13.5%', 'Negroamaro Salento', 28),
(2, 'Primitivo', 'Notte Rosse', '2018', 'PAIRING: First courses of read meat. ALCOHOL: 14%', 'Primitivo Salento', 20),
(3, 'Verdeca', 'Notte Rosse', '2019', 'PAIRING: First courses of fish. ALCOHOL: 12%', 'Verdeca Salento', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_order_person` (`USER_ID`),
  ADD KEY `FK_order_wine` (`WINE_ID`);

--
-- Indici per le tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indici per le tabelle `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_request_person` (`USER_ID`),
  ADD KEY `FK_request_wine` (`WINE_ID`);

--
-- Indici per le tabelle `wine`
--
ALTER TABLE `wine`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `order`
--
ALTER TABLE `order`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT per la tabella `person`
--
ALTER TABLE `person`
  MODIFY `ID` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `request`
--
ALTER TABLE `request`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `wine`
--
ALTER TABLE `wine`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `FK_order_person` FOREIGN KEY (`USER_ID`) REFERENCES `person` (`ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_order_wine` FOREIGN KEY (`WINE_ID`) REFERENCES `wine` (`ID`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `FK_request_person` FOREIGN KEY (`USER_ID`) REFERENCES `person` (`ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_request_wine` FOREIGN KEY (`WINE_ID`) REFERENCES `wine` (`ID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 09, 2013 at 10:36 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

Create Database music_library;
--
-- Database: `music_library`
--
Use music_library;
-- --------------------------------------------------------

--
-- Table structure for table `album`
--

CREATE TABLE IF NOT EXISTS `album` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `album_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3C68E4FD0B97452` (`album_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `album`
--

INSERT INTO `album` (`id`, `name`, `album_id`) VALUES
(1, 'Nevermind', 2),
(2, 'Core', 3),
(3, 'Rotten Apples', 1),
(4, 'Adore', 1);

-- --------------------------------------------------------

--
-- Table structure for table `album_track`
--

CREATE TABLE IF NOT EXISTS `album_track` (
  `track_id` bigint(20) NOT NULL,
  `album_id` bigint(20) NOT NULL,
  KEY `FK4621BB3B508EF9BA` (`track_id`),
  KEY `FK4621BB3B5F18373A` (`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `album_track`
--

INSERT INTO `album_track` (`track_id`, `album_id`) VALUES
(1, 4),
(1, 3),
(2, 2),
(3, 2),
(4, 2),
(5, 1),
(6, 1),
(7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `artist`
--

CREATE TABLE IF NOT EXISTS `artist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `artist`
--

INSERT INTO `artist` (`id`, `name`) VALUES
(1, 'The Smashing Pumpkins'),
(2, 'Nirvana'),
(3, 'Stone Temple Pilots');

-- --------------------------------------------------------

--
-- Table structure for table `composer`
--

CREATE TABLE IF NOT EXISTS `composer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `composer`
--

INSERT INTO `composer` (`id`, `name`) VALUES
(1, 'Robert DeLeo'),
(2, 'Kurt Cobain'),
(3, 'Billy Corgan');

-- --------------------------------------------------------

--
-- Table structure for table `track`
--

CREATE TABLE IF NOT EXISTS `track` (
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `composer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `FK4D5012BADE6C1DA` (`composer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `track`
--

INSERT INTO `track` (`code`, `name`, `composer_id`) VALUES
(1, 'Perfect', 3),
(2, 'Creep', 1),
(3, 'Wicked Garden', 1),
(4, 'Dead & Bloated', 1),
(5, 'Smells Like Teen Spirit', 2),
(6, 'Come as You Are', 2),
(7, 'Polly', 2);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `album`
--
ALTER TABLE `album`
  ADD CONSTRAINT `FK3C68E4FD0B97452` FOREIGN KEY (`album_id`) REFERENCES `artist` (`id`);

--
-- Constraints for table `album_track`
--
ALTER TABLE `album_track`
  ADD CONSTRAINT `FK4621BB3B5F18373A` FOREIGN KEY (`album_id`) REFERENCES `album` (`id`),
  ADD CONSTRAINT `FK4621BB3B508EF9BA` FOREIGN KEY (`track_id`) REFERENCES `track` (`code`);

--
-- Constraints for table `track`
--
ALTER TABLE `track`
  ADD CONSTRAINT `FK4D5012BADE6C1DA` FOREIGN KEY (`composer_id`) REFERENCES `composer` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

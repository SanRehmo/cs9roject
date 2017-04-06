-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: bedrock
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Events`
--

DROP TABLE IF EXISTS `Events`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Events` (
  `EVENT_ID`   INT(11) NOT NULL,
  `TITLE`      VARCHAR(50) DEFAULT NULL,
  `START_TIME` VARCHAR(50) DEFAULT NULL,
  `END_TIME`   VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`EVENT_ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Events`
--

LOCK TABLES `Events` WRITE;
/*!40000 ALTER TABLE `Events`
  DISABLE KEYS */;
INSERT INTO `Events` VALUES (1, 'Fishing', '1/1/1900 12:00:00', '1/1/1901 12:00:00');
/*!40000 ALTER TABLE `Events`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Projects`
--

DROP TABLE IF EXISTS `Projects`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Projects` (
  `PROJECT_ID`  INT(11) NOT NULL,
  `TIMELINE_ID` INT(11) DEFAULT NULL,
  PRIMARY KEY (`PROJECT_ID`),
  KEY `TIMELINE_ID` (`TIMELINE_ID`),
  CONSTRAINT `Projects_ibfk_1` FOREIGN KEY (`TIMELINE_ID`) REFERENCES `Timelines` (`TIMELINE_ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Projects`
--

LOCK TABLES `Projects` WRITE;
/*!40000 ALTER TABLE `Projects`
  DISABLE KEYS */;
INSERT INTO `Projects` VALUES (1, 1);
/*!40000 ALTER TABLE `Projects`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Timelines`
--

DROP TABLE IF EXISTS `Timelines`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Timelines` (
  `TIMELINE_ID` INT(11) NOT NULL,
  `EVENT_ID`    INT(11) DEFAULT NULL,
  PRIMARY KEY (`TIMELINE_ID`),
  KEY `EVENT_ID` (`EVENT_ID`),
  CONSTRAINT `Timelines_ibfk_1` FOREIGN KEY (`EVENT_ID`) REFERENCES `Events` (`EVENT_ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Timelines`
--

LOCK TABLES `Timelines` WRITE;
/*!40000 ALTER TABLE `Timelines`
  DISABLE KEYS */;
INSERT INTO `Timelines` VALUES (1, 1);
/*!40000 ALTER TABLE `Timelines`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `Name`     CHAR(20) DEFAULT NULL,
  `Dept`     CHAR(20) DEFAULT NULL,
  `jobTitle` CHAR(20) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee`
  DISABLE KEYS */;
INSERT INTO `employee`
VALUES ('Fred Flinstone', 'Quarry Worker', 'Rock Digger'), ('Wilma Flinstone', 'Finance', 'Analyst'),
  ('Barney Rubble', 'Sales', 'Neighbor'), ('Betty Rubble', 'IT', 'Neighbor');
/*!40000 ALTER TABLE `employee`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2017-04-06 14:11:59

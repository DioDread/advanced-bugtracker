CREATE DATABASE  IF NOT EXISTS `advanced-bugtracker` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `advanced-bugtracker`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: advanced-bugtracker
-- ------------------------------------------------------
-- Server version	5.7.15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bug_report`
--

DROP TABLE IF EXISTS `bug_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bug_report` (
  `bug_report_id` int(11) NOT NULL,
  `date_reported` datetime(6) NOT NULL,
  `reporter` varchar(255) NOT NULL,
  `description` mediumtext,
  `desired_resolution_date` date DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `date_resolved` datetime(6) DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `project` int(11) NOT NULL,
  `labels` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bug_report_id`),
  UNIQUE KEY `bug_report_id_UNIQUE` (`bug_report_id`),
  KEY `fk_state_id_idx` (`state`),
  KEY `fk_priority_id_idx` (`priority`),
  KEY `fk_project_id_idx` (`project`),
  CONSTRAINT `fk_priority_id` FOREIGN KEY (`priority`) REFERENCES `priority` (`priority_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_id` FOREIGN KEY (`project`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_state_id` FOREIGN KEY (`state`) REFERENCES `bug_state` (`bug_state_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=cp1250;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug_report`
--

LOCK TABLES `bug_report` WRITE;
/*!40000 ALTER TABLE `bug_report` DISABLE KEYS */;
INSERT INTO `bug_report` VALUES (0,'2016-09-26 00:00:00.000000','John Doe','Some more test bug from some part of app','2016-10-05',0,2,NULL,NULL,1,NULL),(1,'2016-09-26 00:00:00.000000','Ales Tsvil','Some more test bug from some part of app','2016-09-30',1,0,NULL,NULL,2,NULL);
/*!40000 ALTER TABLE `bug_report` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-26 17:46:50

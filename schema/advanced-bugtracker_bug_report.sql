CREATE DATABASE  IF NOT EXISTS `advanced-bugtracker` /*!40100 DEFAULT CHARACTER SET cp1250 */;
USE `advanced-bugtracker`;
-- MySQL dump 10.13  Distrib 5.7.13, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: advanced-bugtracker
-- ------------------------------------------------------
-- Server version	5.7.16

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
  `name` tinytext NOT NULL,
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
  `assigned_user` int(11) NOT NULL,
  `reported_user` int(11) NOT NULL,
  PRIMARY KEY (`bug_report_id`),
  UNIQUE KEY `bug_report_id_UNIQUE` (`bug_report_id`),
  KEY `fk_state_id_idx` (`state`),
  KEY `fk_priority_id_idx` (`priority`),
  KEY `fk_project_id_idx` (`project`),
  KEY `fk_user_reported_id_idx` (`reported_user`),
  KEY `fk_user_assgnd_id_idx` (`assigned_user`),
  CONSTRAINT `fk_priority_id` FOREIGN KEY (`priority`) REFERENCES `priority` (`priority_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_id` FOREIGN KEY (`project`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_state_id` FOREIGN KEY (`state`) REFERENCES `bug_state` (`bug_state_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usr_assigned_id` FOREIGN KEY (`assigned_user`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usr_reported_id` FOREIGN KEY (`reported_user`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=cp1250;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug_report`
--

LOCK TABLES `bug_report` WRITE;
/*!40000 ALTER TABLE `bug_report` DISABLE KEYS */;
INSERT INTO `bug_report` VALUES (4,'Implement Bug Tracker upd','2016-10-08 00:00:00.000000','Ales Tsvil','Implement Advanced-Bugtracker Application for Java External Lab','2016-11-25',4,1,NULL,'2016-10-11 18:12:29.000000',0,'[{\"name\":\"1\",\"color\":\"#07b3eb\"}]',0,0),(5,'Test 222 Updated','2016-10-11 00:00:00.000000','Many Van Man','Labels added.','2016-12-12',1,1,NULL,'2016-10-16 17:51:36.000000',0,'[]',0,0),(6,'Test Bug # 4','2016-10-12 00:00:00.000000','Normal Man','Regular Description.With long text. Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolorLorem ipsum sit amet dolorLorem ipsum sit amet dolor Lorem ipsum sit amet dolor Lorem ipsum sit amet dolorLorem ipsum sit amet dolorLorem ipsum sit amet dolor Lorem ipsum sit amet dolorLorem ipsum sit amet dolorLorem ipsum sit amet dolor Lorem ipsum sit amet dolor','2016-12-05',2,1,NULL,'2016-10-13 02:15:14.000000',1,'',0,0),(8,'Test Bug','2016-10-12 20:52:36.000000','Some Dude','dasdasdas','2016-12-05',1,0,NULL,NULL,0,'[{\"name\":\"1\",\"color\":\"#07b3eb\"}]',0,0),(9,'Color Test','2016-10-16 00:00:00.000000','asdasd','Updated tasd asdas dasd','2016-12-05',2,0,NULL,'2016-10-18 01:58:25.000000',0,'[{\"name\":\"asd asd asdasd\",\"color\":\"#14272d\"},{\"name\":\"asdasdasd \",\"color\":\"#81d0e9\"}]',0,0),(10,'Toast test','2016-10-16 00:00:00.000000','Manly Man','adasdasd asd asdasd','2016-12-05',1,0,NULL,'2016-10-16 21:38:13.000000',0,'[{\"name\":\"Toast nice\",\"color\":\"#421c6e\"},{\"name\":\"yellow\",\"color\":\"#a1a509\"},{\"name\":\"2\",\"color\":\"#5c5e08\"}]',0,0),(11,'Test moar bugs','2016-10-16 22:37:46.000000','Many Man','asdasd asdasdasd','2016-12-05',1,0,NULL,NULL,0,'[{\"name\":\"yellow\",\"color\":\"#07b3eb\"}]',0,0),(12,'one moar bug','2016-10-17 22:50:26.000000','Many Man','adasdasdasdasd','2016-12-05',2,0,NULL,NULL,0,'[{\"name\":\"yellow\",\"color\":\"#bceb07\"}]',0,0),(13,'New Bug','2016-10-18 01:56:40.000000','Some Dude','tetasd asd asdasdasd','2016-12-05',1,0,NULL,NULL,0,'[{\"name\":\"Label 1\",\"color\":\"#2fa3c8\"},{\"name\":\"Some more\",\"color\":\"#167512\"}]',0,0),(14,'New Bug 2','2016-10-18 01:56:59.000000','Some Dude','tetasd asd asdasdasd sad asda sdasd asd','2016-12-04',1,0,NULL,NULL,0,'[{\"name\":\"Label 1\",\"color\":\"#2fa3c8\"}]',0,0);
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

-- Dump completed on 2016-11-03 23:12:12

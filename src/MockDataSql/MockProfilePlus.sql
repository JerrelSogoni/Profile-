-- MySQL dump 10.13  Distrib 5.7.15, for osx10.11 (x86_64)
--
-- Host: localhost    Database: ProfilePlus
-- ------------------------------------------------------
-- Server version	5.7.15

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
-- Table structure for table `AdData`
--

DROP TABLE IF EXISTS `AdData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AdData` (
  `AdId` int(11) NOT NULL,
  `EmpId` char(11) NOT NULL,
  `Type` char(20) DEFAULT NULL,
  `Company` char(50) DEFAULT NULL,
  `ItemName` char(50) DEFAULT NULL,
  `Content` varchar(200) DEFAULT NULL,
  `UnitPrice` float DEFAULT NULL,
  `NumOfAvaUnits` int(11) DEFAULT NULL,
  PRIMARY KEY (`AdId`),
  KEY `EmpId` (`EmpId`),
  CONSTRAINT `addata_ibfk_1` FOREIGN KEY (`EmpId`) REFERENCES `Employee` (`SSN`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AdData`
--

LOCK TABLES `AdData` WRITE;
/*!40000 ALTER TABLE `AdData` DISABLE KEYS */;
INSERT INTO `AdData` VALUES (1,'153581835','Cars','Google','Google Car','First flying car',100,100),(2,'168375077','Cars','Apple','Apple Car','Second flying car',100000,200),(3,'239251935','Server','Google','Google Server','First AI security server',10,100),(4,'264939771','Etc','Oracle','Oracle AI','See into the future',100000000,1),(5,'269768789','Book','Amazon','Textbook1','First flying book',1000,20),(6,'269768789','Book','Amazon','Textbook2','First diving book',2000,10),(7,'269768789','TV','Samsung','Samsung TV','First 5D TV',100,2),(8,'273874496','TV','LG','LG Car','First teleport TV',200,999),(9,'285530393','Human','Google','Jerrel','Enjoy',10000,2),(10,'285530393','Human','Apple','Jerrel2','Enjoy2',100,100);
/*!40000 ALTER TABLE `AdData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Buy`
--

DROP TABLE IF EXISTS `Buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Buy` (
  `TransId` int(11) NOT NULL,
  `EmpId` char(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  PRIMARY KEY (`TransId`,`EmpId`,`UserId`),
  KEY `EmpId` (`EmpId`),
  KEY `UserId` (`UserId`),
  CONSTRAINT `buy_ibfk_1` FOREIGN KEY (`TransId`) REFERENCES `Sales` (`TransId`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `buy_ibfk_2` FOREIGN KEY (`EmpId`) REFERENCES `Employee` (`SSN`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `buy_ibfk_3` FOREIGN KEY (`UserId`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Buy`
--

LOCK TABLES `Buy` WRITE;
/*!40000 ALTER TABLE `Buy` DISABLE KEYS */;
INSERT INTO `Buy` VALUES (1,'153581835',1),(2,'168375077',2),(3,'168375077',3),(4,'264939771',4),(5,'153581835',5),(6,'264939771',6),(7,'153581835',7),(8,'269768789',8),(9,'269768789',9),(10,'269768789',10);
/*!40000 ALTER TABLE `Buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Comment`
--

DROP TABLE IF EXISTS `Comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comment` (
  `CommentID` int(11) NOT NULL AUTO_INCREMENT,
  `DateCreated` datetime DEFAULT NULL,
  `Content` varchar(100) DEFAULT NULL,
  `AuthorID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CommentID`),
  KEY `AuthorID` (`AuthorID`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`AuthorID`) REFERENCES `UserPlus` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comment`
--

LOCK TABLES `Comment` WRITE;
/*!40000 ALTER TABLE `Comment` DISABLE KEYS */;
INSERT INTO `Comment` VALUES (1,'2016-11-06 15:00:25','2',1),(2,'2016-11-06 15:00:25','3',2),(3,'2016-11-06 15:00:25','4',3),(4,'2016-11-06 15:00:25','5',4),(5,'2016-11-06 15:00:25','6',5),(6,'2016-11-06 15:00:25','7',6),(7,'2016-11-06 15:00:25','8',7),(8,'2016-11-06 15:00:25','9',8),(9,'2016-11-06 15:00:25','10',9),(10,'2016-11-06 15:00:25','11',10),(11,'2016-11-06 15:00:25','12',11),(12,'2016-11-06 15:00:25','13',12),(13,'2016-11-06 15:00:25','14',13),(14,'2016-11-06 15:00:25','15',14),(15,'2016-11-06 15:00:25','16',15),(16,'2016-11-06 15:00:25','17',16),(17,'2016-11-06 15:00:25','18',17),(18,'2016-11-06 15:00:25','19',18),(19,'2016-11-06 15:00:25','20',19),(20,'2016-11-06 15:00:25','21',20),(21,'2016-11-06 15:00:25','22',21),(22,'2016-11-06 15:00:25','23',22),(23,'2016-11-06 15:00:25','24',23),(24,'2016-11-06 15:00:25','25',24),(25,'2016-11-06 15:00:25','1',25);
/*!40000 ALTER TABLE `Comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommentOn`
--

DROP TABLE IF EXISTS `CommentOn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommentOn` (
  `CommentID` int(11) NOT NULL,
  `PostID` int(11) NOT NULL,
  PRIMARY KEY (`CommentID`,`PostID`),
  KEY `PostID` (`PostID`),
  CONSTRAINT `commenton_ibfk_1` FOREIGN KEY (`CommentID`) REFERENCES `Comment` (`CommentID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `commenton_ibfk_2` FOREIGN KEY (`PostID`) REFERENCES `Post` (`PostID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentOn`
--

LOCK TABLES `CommentOn` WRITE;
/*!40000 ALTER TABLE `CommentOn` DISABLE KEYS */;
INSERT INTO `CommentOn` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1);
/*!40000 ALTER TABLE `CommentOn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CreatesGroup`
--

DROP TABLE IF EXISTS `CreatesGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CreatesGroup` (
  `UserID` int(11) NOT NULL,
  `GroupID` int(11) NOT NULL,
  PRIMARY KEY (`UserID`,`GroupID`),
  KEY `GroupID` (`GroupID`),
  CONSTRAINT `createsgroup_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `createsgroup_ibfk_2` FOREIGN KEY (`GroupID`) REFERENCES `GroupPlus` (`GroupID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CreatesGroup`
--

LOCK TABLES `CreatesGroup` WRITE;
/*!40000 ALTER TABLE `CreatesGroup` DISABLE KEYS */;
INSERT INTO `CreatesGroup` VALUES (1,26),(11,26),(2,27),(12,27),(3,28),(14,28),(4,29),(14,29),(5,30),(15,30),(6,31),(7,32),(8,33),(9,34),(10,35);
/*!40000 ALTER TABLE `CreatesGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Employee` (
  `SSN` char(11) NOT NULL,
  `LastName` char(30) DEFAULT NULL,
  `FirstName` char(10) DEFAULT NULL,
  `Address` char(50) DEFAULT NULL,
  `City` char(60) DEFAULT NULL,
  `State` char(20) DEFAULT NULL,
  `ZipCode` char(16) DEFAULT NULL,
  `Telephone` char(20) DEFAULT NULL,
  `StartDate` date DEFAULT NULL,
  `HourlyRate` float DEFAULT NULL,
  PRIMARY KEY (`SSN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES ('153581835','Cortez','Azalia','P.O. Box 171, 6263 Vulputate, St.','Essex','Vermont','98163','(594) 213-2948','2016-03-04',40.11),('168375077','Vargas','Ahmed','Ap #913-7885 Aliquam Av.','West Valley City','Utah','80233','(680) 721-9592','2016-10-19',95.97),('239251935','Kline','Stacy','P.O. Box 264, 7449 Sodales St.','St. Petersburg','Florida','44404','(858) 316-1527','2016-12-01',30.68),('264939771','Landry','Dorian','Ap #324-4954 Ad Ave','Baltimore','Maryland','38110','(341) 785-7012','2017-02-26',76.17),('269768789','Nolan','Driscoll','P.O. Box 197, 2598 Lobortis Ave','West Valley City','Utah','24427','(258) 236-5195','2015-11-26',21.44),('273874496','Bond','Xerxes','166-6265 Scelerisque Rd.','Lawton','Oklahoma','61533','(644) 695-3687','2016-02-27',70.92),('285530393','Ware','Karen','510-8225 Est. Ave','Missoula','Montana','98289','(299) 138-5280','2016-11-18',35.56),('319144404','Harrell','Justin','Ap #285-8898 Donec Avenue','Butte','Montana','21088','(431) 359-7765','2017-10-04',86.4),('323455805','Brooks','Brianna','175-183 Suspendisse St.','Jefferson City','Missouri','89105','(113) 485-3017','2017-08-24',43.58),('352369810','Gardner','Neville','P.O. Box 588, 9440 Nullam St.','Cleveland','Ohio','40195','(937) 304-0073','2016-11-10',33.35),('365398046','Hubbard','Aretha','5971 Fusce Rd.','South Bend','Indiana','37591','(702) 386-6800','2017-09-29',71.75),('379548784','Curtis','Octavia','P.O. Box 877, 5076 Elit, Street','Naperville','Illinois','18460','(550) 590-2634','2017-07-26',68.52),('395359320','Russo','Orson','454 Mus. Rd.','Montgomery','Alabama','35310','(474) 147-3496','2017-07-13',62.78),('439086475','Vasquez','Ignatius','537-6243 At St.','Stamford','Connecticut','15700','(317) 724-8680','2017-07-27',26.36),('58971316','Riley','Desiree','270-9361 Lorem Av.','Casper','Wyoming','12432','(402) 756-7169','2015-12-25',27.78),('61029656','Fernandez','Athena','200-8990 Est. Street','Indianapolis','Indiana','89805','(472) 543-8504','2016-03-28',21.61);
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FriendsWith`
--

DROP TABLE IF EXISTS `FriendsWith`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FriendsWith` (
  `UserID` int(11) NOT NULL,
  `FriendID` int(11) NOT NULL,
  PRIMARY KEY (`UserID`,`FriendID`),
  KEY `FriendID` (`FriendID`),
  CONSTRAINT `friendswith_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `friendswith_ibfk_2` FOREIGN KEY (`FriendID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FriendsWith`
--

LOCK TABLES `FriendsWith` WRITE;
/*!40000 ALTER TABLE `FriendsWith` DISABLE KEYS */;
INSERT INTO `FriendsWith` VALUES (2,1),(3,1),(20,1),(25,1),(1,2),(1,3),(6,4),(4,6),(25,10),(1,20),(1,25),(10,25);
/*!40000 ALTER TABLE `FriendsWith` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GroupPage`
--

DROP TABLE IF EXISTS `GroupPage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GroupPage` (
  `PageID` int(11) NOT NULL AUTO_INCREMENT,
  `GroupID` int(11) DEFAULT NULL,
  PRIMARY KEY (`PageID`),
  KEY `GroupID` (`GroupID`),
  CONSTRAINT `grouppage_ibfk_1` FOREIGN KEY (`GroupID`) REFERENCES `GroupPlus` (`GroupID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GroupPage`
--

LOCK TABLES `GroupPage` WRITE;
/*!40000 ALTER TABLE `GroupPage` DISABLE KEYS */;
INSERT INTO `GroupPage` VALUES (26,26),(27,27),(28,28),(29,29),(30,30),(31,31),(32,32),(33,33),(34,34),(35,35);
/*!40000 ALTER TABLE `GroupPage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GroupPlus`
--

DROP TABLE IF EXISTS `GroupPlus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GroupPlus` (
  `GroupID` int(11) NOT NULL AUTO_INCREMENT,
  `GroupName` varchar(50) DEFAULT NULL,
  `Owner` int(11) DEFAULT NULL,
  `Type` set('Club','Organization','Event','News') DEFAULT NULL,
  PRIMARY KEY (`GroupID`),
  KEY `Owner` (`Owner`),
  CONSTRAINT `groupplus_ibfk_1` FOREIGN KEY (`Owner`) REFERENCES `UserPlus` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GroupPlus`
--

LOCK TABLES `GroupPlus` WRITE;
/*!40000 ALTER TABLE `GroupPlus` DISABLE KEYS */;
INSERT INTO `GroupPlus` VALUES (26,'Automated value-added process improvement',1,NULL),(27,'Diverse next generation knowledge base',2,NULL),(28,'Inverse contextually-based forecast',3,NULL),(29,'Team-oriented contextually-based solution',4,NULL),(30,'Enterprise-wide encompassing portal',5,NULL),(31,'Event',6,NULL),(32,'Event',7,NULL),(33,'Event',8,NULL),(34,'Event',9,NULL),(35,'Event',10,NULL);
/*!40000 ALTER TABLE `GroupPlus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HasAGroupPage`
--

DROP TABLE IF EXISTS `HasAGroupPage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HasAGroupPage` (
  `GroupID` int(11) NOT NULL,
  `GroupPageID` int(11) NOT NULL,
  PRIMARY KEY (`GroupID`,`GroupPageID`),
  KEY `GroupPageID` (`GroupPageID`),
  CONSTRAINT `hasagrouppage_ibfk_1` FOREIGN KEY (`GroupID`) REFERENCES `GroupPlus` (`GroupID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `hasagrouppage_ibfk_2` FOREIGN KEY (`GroupPageID`) REFERENCES `GroupPage` (`PageID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HasAGroupPage`
--

LOCK TABLES `HasAGroupPage` WRITE;
/*!40000 ALTER TABLE `HasAGroupPage` DISABLE KEYS */;
INSERT INTO `HasAGroupPage` VALUES (26,26),(27,27),(28,28),(29,29),(30,30),(31,31),(32,32),(33,33),(34,34),(35,35);
/*!40000 ALTER TABLE `HasAGroupPage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HasAPersonal`
--

DROP TABLE IF EXISTS `HasAPersonal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HasAPersonal` (
  `UserID` int(11) NOT NULL,
  `PersonalPageID` int(11) NOT NULL,
  PRIMARY KEY (`UserID`,`PersonalPageID`),
  KEY `PersonalPageID` (`PersonalPageID`),
  CONSTRAINT `hasapersonal_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `hasapersonal_ibfk_2` FOREIGN KEY (`PersonalPageID`) REFERENCES `PersonalPage` (`PageID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HasAPersonal`
--

LOCK TABLES `HasAPersonal` WRITE;
/*!40000 ALTER TABLE `HasAPersonal` DISABLE KEYS */;
INSERT INTO `HasAPersonal` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10),(11,11),(12,12),(13,13),(14,14),(15,15),(16,16),(17,17),(18,18),(19,19),(20,20),(21,21),(22,22),(23,23),(24,24),(25,25);
/*!40000 ALTER TABLE `HasAPersonal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HasAccessToGroup`
--

DROP TABLE IF EXISTS `HasAccessToGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HasAccessToGroup` (
  `UserID` int(11) NOT NULL,
  `AdderID` int(11) DEFAULT NULL,
  `PageID` int(11) NOT NULL,
  `GroupID` int(11) NOT NULL,
  PRIMARY KEY (`UserID`,`PageID`,`GroupID`),
  KEY `PageID` (`PageID`),
  KEY `GroupID` (`GroupID`),
  KEY `AdderID` (`AdderID`),
  CONSTRAINT `hasaccesstogroup_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `hasaccesstogroup_ibfk_2` FOREIGN KEY (`PageID`) REFERENCES `PagePlus` (`PageID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `hasaccesstogroup_ibfk_3` FOREIGN KEY (`GroupID`) REFERENCES `GroupPlus` (`GroupID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `hasaccesstogroup_ibfk_4` FOREIGN KEY (`AdderID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HasAccessToGroup`
--

LOCK TABLES `HasAccessToGroup` WRITE;
/*!40000 ALTER TABLE `HasAccessToGroup` DISABLE KEYS */;
INSERT INTO `HasAccessToGroup` VALUES (1,NULL,26,26),(2,NULL,27,27),(3,NULL,28,28),(4,NULL,29,29),(5,NULL,30,30),(6,NULL,31,31),(7,NULL,32,32),(8,NULL,33,33),(9,NULL,34,34),(10,NULL,35,35),(11,1,26,26),(12,2,27,27),(14,3,28,28),(14,4,29,29),(15,5,30,30);
/*!40000 ALTER TABLE `HasAccessToGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IsIn`
--

DROP TABLE IF EXISTS `IsIn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IsIn` (
  `UserId` int(11) NOT NULL,
  `GroupId` int(11) NOT NULL,
  PRIMARY KEY (`UserId`,`GroupId`),
  KEY `GroupId` (`GroupId`),
  CONSTRAINT `isin_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `isin_ibfk_2` FOREIGN KEY (`GroupId`) REFERENCES `GroupPlus` (`GroupID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IsIn`
--

LOCK TABLES `IsIn` WRITE;
/*!40000 ALTER TABLE `IsIn` DISABLE KEYS */;
INSERT INTO `IsIn` VALUES (1,26),(2,26),(3,26),(4,26),(5,26),(6,26),(2,27),(3,28),(4,29),(5,30),(6,31),(7,32),(8,33),(9,34),(10,35);
/*!40000 ALTER TABLE `IsIn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LikesComment`
--

DROP TABLE IF EXISTS `LikesComment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LikesComment` (
  `CommentID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  PRIMARY KEY (`CommentID`,`UserID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `likescomment_ibfk_1` FOREIGN KEY (`CommentID`) REFERENCES `Comment` (`CommentID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `likescomment_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LikesComment`
--

LOCK TABLES `LikesComment` WRITE;
/*!40000 ALTER TABLE `LikesComment` DISABLE KEYS */;
INSERT INTO `LikesComment` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25);
/*!40000 ALTER TABLE `LikesComment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LikesPost`
--

DROP TABLE IF EXISTS `LikesPost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LikesPost` (
  `PostID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  PRIMARY KEY (`PostID`,`UserID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `likespost_ibfk_1` FOREIGN KEY (`PostID`) REFERENCES `Post` (`PostID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `likespost_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LikesPost`
--

LOCK TABLES `LikesPost` WRITE;
/*!40000 ALTER TABLE `LikesPost` DISABLE KEYS */;
INSERT INTO `LikesPost` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25);
/*!40000 ALTER TABLE `LikesPost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Message`
--

DROP TABLE IF EXISTS `Message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message` (
  `MessageID` int(11) NOT NULL AUTO_INCREMENT,
  `DateSent` datetime DEFAULT NULL,
  `ReceiverID` int(11) DEFAULT NULL,
  `SenderID` int(11) DEFAULT NULL,
  `Subject` varchar(100) DEFAULT NULL,
  `Content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MessageID`),
  KEY `ReceiverID` (`ReceiverID`),
  KEY `SenderID` (`SenderID`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`ReceiverID`) REFERENCES `UserPlus` (`UserID`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`SenderID`) REFERENCES `UserPlus` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message`
--

LOCK TABLES `Message` WRITE;
/*!40000 ALTER TABLE `Message` DISABLE KEYS */;
INSERT INTO `Message` VALUES (1,'2016-11-06 15:00:16',2,1,'subject','apples'),(2,'2016-11-06 15:00:16',3,2,'subject','one'),(3,'2016-11-06 15:00:16',4,3,'subject','q'),(4,'2016-11-06 15:00:16',5,4,'subject','e'),(5,'2016-11-06 15:00:16',6,5,'subject','y'),(6,'2016-11-06 15:00:16',7,6,'subject','er'),(7,'2016-11-06 15:00:16',8,7,'subject','dsgdfg'),(8,'2016-11-06 15:00:16',9,8,'subject','yetikty'),(9,'2016-11-06 15:00:16',10,9,'subject','raehuy56h46'),(10,'2016-11-06 15:00:16',11,10,'subject','rstj7e5jurt'),(11,'2016-11-06 15:00:16',12,11,'subject','jrtwjety6j'),(12,'2016-11-06 15:00:16',13,12,'subject','tyjrstyjkrsty'),(13,'2016-11-06 15:00:16',14,13,'subject','jkmrastyjsrthjurt6h'),(14,'2016-11-06 15:00:16',15,14,'subject','rthuertyjrstwjrtyjrtyu'),(15,'2016-11-06 15:00:16',16,15,'subject','rt6h'),(16,'2016-11-06 15:00:16',17,16,'subject','56hurty6u46h'),(17,'2016-11-06 15:00:16',18,17,'subject','56hjnrt'),(18,'2016-11-06 15:00:16',19,18,'subject','jursyhju'),(19,'2016-11-06 15:00:16',20,19,'subject','rs6tjhdty6jrstj'),(20,'2016-11-06 15:00:16',21,20,'subject','srtyjdty'),(21,'2016-11-06 15:00:16',22,21,'subject','jrs6hj'),(22,'2016-11-06 15:00:16',23,22,'subject','rs6thj'),(23,'2016-11-06 15:00:16',24,23,'subject','htyj'),(24,'2016-11-06 15:00:16',25,24,'subject','tyje57tyjtyjty'),(25,'2016-11-06 15:00:16',1,25,'subject','jrj6');
/*!40000 ALTER TABLE `Message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PagePlus`
--

DROP TABLE IF EXISTS `PagePlus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PagePlus` (
  `PageID` int(11) NOT NULL,
  `PostCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`PageID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PagePlus`
--

LOCK TABLES `PagePlus` WRITE;
/*!40000 ALTER TABLE `PagePlus` DISABLE KEYS */;
INSERT INTO `PagePlus` VALUES (1,20),(2,0),(3,0),(4,0),(5,0),(6,0),(7,0),(8,0),(9,0),(10,0),(11,0),(12,0),(13,0),(14,0),(15,0),(16,0),(17,0),(18,0),(19,0),(20,0),(21,0),(22,0),(23,0),(24,0),(25,0),(26,0),(27,0),(28,0),(29,0),(30,0),(31,0),(32,0),(33,0),(34,0),(35,0);
/*!40000 ALTER TABLE `PagePlus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PersonalPage`
--

DROP TABLE IF EXISTS `PersonalPage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PersonalPage` (
  `PageID` int(11) NOT NULL AUTO_INCREMENT,
  `OwnerID` int(11) DEFAULT NULL,
  PRIMARY KEY (`PageID`),
  KEY `OwnerID` (`OwnerID`),
  CONSTRAINT `personalpage_ibfk_1` FOREIGN KEY (`OwnerID`) REFERENCES `UserPlus` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PersonalPage`
--

LOCK TABLES `PersonalPage` WRITE;
/*!40000 ALTER TABLE `PersonalPage` DISABLE KEYS */;
INSERT INTO `PersonalPage` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10),(11,11),(12,12),(13,13),(14,14),(15,15),(16,16),(17,17),(18,18),(19,19),(20,20),(21,21),(22,22),(23,23),(24,24),(25,25);
/*!40000 ALTER TABLE `PersonalPage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Post`
--

DROP TABLE IF EXISTS `Post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Post` (
  `PostID` int(11) NOT NULL AUTO_INCREMENT,
  `DateCreated` datetime DEFAULT NULL,
  `Content` varchar(100) DEFAULT NULL,
  `CommentCount` int(11) DEFAULT NULL,
  `AuthorID` int(11) DEFAULT NULL,
  PRIMARY KEY (`PostID`),
  KEY `AuthorID` (`AuthorID`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`AuthorID`) REFERENCES `UserPlus` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Post`
--

LOCK TABLES `Post` WRITE;
/*!40000 ALTER TABLE `Post` DISABLE KEYS */;
INSERT INTO `Post` VALUES (1,'2016-11-06 15:00:08','envisioneer frictionless deliverables',10,1),(2,'2016-11-06 15:00:08','engineer back-end paradigms',0,2),(3,'2016-11-06 15:00:08','redefine efficient infrastructures',0,3),(4,'2016-11-06 15:00:08','embrace sticky experiences',0,4),(5,'2016-11-06 15:00:08','synthesize innovative niches',0,5),(6,'2016-11-06 15:00:08','engage compelling methodologies',0,6),(7,'2016-11-06 15:00:08','exploit dot-com platforms',0,7),(8,'2016-11-06 15:00:08','benchmark seamless portals',0,8),(9,'2016-11-06 15:00:08','utilize leading-edge networks',0,9),(10,'2016-11-06 15:00:08','generate collaborative interfaces',0,10),(11,'2016-11-06 15:00:08','visualize bleeding-edge mindshare',0,11),(12,'2016-11-06 15:00:08','drive virtual initiatives',0,12),(13,'2016-11-06 15:00:08','empower cutting-edge portals',0,13),(14,'2016-11-06 15:00:08','mesh revolutionary networks',0,14),(15,'2016-11-06 15:00:08','mesh granular metrics',0,15),(16,'2016-11-06 15:00:08','incentivize transparent ROI',0,16),(17,'2016-11-06 15:00:08','iterate back-end deliverables',0,17),(18,'2016-11-06 15:00:08','generate dynamic web services',0,18),(19,'2016-11-06 15:00:08','transform visionary eyeballs',0,19),(20,'2016-11-06 15:00:08','architect dot-com e-services',0,20);
/*!40000 ALTER TABLE `Post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PostedTo`
--

DROP TABLE IF EXISTS `PostedTo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PostedTo` (
  `PostID` int(11) NOT NULL,
  `PageID` int(11) NOT NULL,
  PRIMARY KEY (`PageID`,`PostID`),
  KEY `PostID` (`PostID`),
  CONSTRAINT `postedto_ibfk_1` FOREIGN KEY (`PageID`) REFERENCES `PagePlus` (`PageID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `postedto_ibfk_2` FOREIGN KEY (`PostID`) REFERENCES `Post` (`PostID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PostedTo`
--

LOCK TABLES `PostedTo` WRITE;
/*!40000 ALTER TABLE `PostedTo` DISABLE KEYS */;
INSERT INTO `PostedTo` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1);
/*!40000 ALTER TABLE `PostedTo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Receive`
--

DROP TABLE IF EXISTS `Receive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Receive` (
  `Receiver` int(11) NOT NULL,
  `MessageId` int(11) NOT NULL,
  PRIMARY KEY (`Receiver`,`MessageId`),
  KEY `MessageId` (`MessageId`),
  CONSTRAINT `receive_ibfk_1` FOREIGN KEY (`Receiver`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `receive_ibfk_2` FOREIGN KEY (`MessageId`) REFERENCES `Message` (`MessageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Receive`
--

LOCK TABLES `Receive` WRITE;
/*!40000 ALTER TABLE `Receive` DISABLE KEYS */;
INSERT INTO `Receive` VALUES (2,1),(3,2),(4,3),(5,4),(6,5),(7,6),(8,7),(9,8),(10,9),(11,10),(12,11),(13,12),(14,13),(15,14),(16,15),(17,16),(18,17),(19,18),(20,19),(21,20),(22,21),(23,22),(24,23),(25,24);
/*!40000 ALTER TABLE `Receive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sales`
--

DROP TABLE IF EXISTS `Sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sales` (
  `TransId` int(11) NOT NULL,
  `TransDate` date DEFAULT NULL,
  `TransTime` time DEFAULT NULL,
  `AdId` int(11) DEFAULT NULL,
  `NumOfUnits` int(11) DEFAULT NULL,
  `AccountNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`TransId`),
  KEY `AdId` (`AdId`),
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`AdId`) REFERENCES `AdData` (`AdId`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sales`
--

LOCK TABLES `Sales` WRITE;
/*!40000 ALTER TABLE `Sales` DISABLE KEYS */;
INSERT INTO `Sales` VALUES (1,'2016-10-01','11:10:00',1,100,12341234),(2,'2016-10-02','11:10:00',2,10,11111111),(3,'2016-10-03','11:10:00',3,1,22222222),(4,'2016-11-03','11:10:00',3,1,33333333),(5,'2015-09-03','11:10:00',4,1,22222222),(6,'2016-10-03','11:10:00',5,19,222333333),(7,'2012-07-03','11:10:00',9,1,12342222),(8,'2011-10-23','11:10:00',9,1,12341111),(9,'2016-01-13','11:10:00',3,1,4321222),(10,'2012-06-03','11:10:00',3,1,33333333);
/*!40000 ALTER TABLE `Sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Send`
--

DROP TABLE IF EXISTS `Send`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Send` (
  `Sender` int(11) NOT NULL,
  `MessageId` int(11) NOT NULL,
  PRIMARY KEY (`Sender`,`MessageId`),
  KEY `MessageId` (`MessageId`),
  CONSTRAINT `send_ibfk_1` FOREIGN KEY (`Sender`) REFERENCES `UserPlus` (`UserID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `send_ibfk_2` FOREIGN KEY (`MessageId`) REFERENCES `Message` (`MessageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Send`
--

LOCK TABLES `Send` WRITE;
/*!40000 ALTER TABLE `Send` DISABLE KEYS */;
INSERT INTO `Send` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10),(11,11),(12,12),(13,13),(14,14),(15,15),(16,16),(17,17),(18,18),(19,19),(20,20),(21,21),(22,22),(23,23),(24,24);
/*!40000 ALTER TABLE `Send` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserPlus`
--

DROP TABLE IF EXISTS `UserPlus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserPlus` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Password` char(20) DEFAULT NULL,
  `FirstName` char(20) DEFAULT NULL,
  `LastName` char(20) DEFAULT NULL,
  `Address` char(50) DEFAULT NULL,
  `City` varchar(20) DEFAULT NULL,
  `State` varchar(2) DEFAULT NULL,
  `ZipCode` int(11) DEFAULT NULL,
  `Phone` char(10) DEFAULT NULL,
  `Email` char(50) DEFAULT NULL,
  `AccountNum` int(11) DEFAULT NULL,
  `AccountCreationDate` datetime DEFAULT NULL,
  `CreditCardNum` char(16) DEFAULT NULL,
  `Preferences` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserPlus`
--

LOCK TABLES `UserPlus` WRITE;
/*!40000 ALTER TABLE `UserPlus` DISABLE KEYS */;
INSERT INTO `UserPlus` VALUES (1,'apples','Michael','Thorne','4363 Arrowood Drive','Jacksonville','FL',32216,'9044166609','MichaelCThorne@gmail.com',1,'2016-11-06 14:59:54','5443350608517143','applePicking'),(2,'apples','Paul','Fodor','2635 Simons Hollow Road','New York','NY',10001,'5707512415','paulfodor@gmail.com',2,'2016-11-06 14:59:54','4958858084473102','applePicking'),(3,'apples','Michael','Kife','885 Augusta Park','Charleston','WV',25301,'9046366609','MichaelKife@gmail.com',3,'2016-11-06 14:59:54','4370457802840195','applePicking'),(4,'apples','Bob','Marvel','1 Arrowood Drive','Jacksonville','FL',32216,'9044366619','bobmarvel@gmail.com',4,'2016-11-06 14:59:54','4992869275848026','applePicking'),(5,'apples','yay','grovey','342 Arrowood Drive','Jacksonville','FL',32216,'6044366609','yaygrovey@gmail.com',5,'2016-11-06 14:59:54','4489636511827803','applePicking'),(6,'apples','apple','seed','321 Arrowood Drive','Jacksonville','FL',32216,'3044366609','appleseed@gmail.com',6,'2016-11-06 14:59:54','4349162290877844','applePicking'),(7,'apples','new','you','34 Arrowood Drive','Jacksonville','FL',32216,'9244366609','newyou@gmail.com',7,'2016-11-06 14:59:54','4718671748810093','applePicking'),(8,'apples','qqqq','lol','399 Arrowood Drive','Jacksonville','FL',32216,'5044366609','qqqqlol@gmail.com',8,'2016-11-06 14:59:54','4064305551916263','applePicking'),(9,'apples','oop','yo','9 Arrowood Drive','Jacksonville','FL',32216,'9084366609','oopyo@gmail.com',9,'2016-11-06 14:59:54','4556094238457322','applePicking'),(10,'apples','one','two','100 Arrowood Drive','Jacksonville','FL',32216,'9047366609','onetwo@gmail.com',10,'2016-11-06 14:59:54','4606913205667284','applePicking'),(11,'apples','suretrip','forever','4363 Arrowood Drive','Jacksonville','FL',32216,'9044386609','suretripforever@gmail.com',11,'2016-11-06 14:59:54','4444224543026845','applePicking'),(12,'apples','forever','young','10101 Arrowood Drive','Jacksonville','FL',32216,'9244366609','foreveryoung@gmail.com',12,'2016-11-06 14:59:54','4511410796714397','applePicking'),(13,'apples','life','video','3575 Arrowood Drive','Jacksonville','FL',32216,'9044346609','lifevideo@gmail.com',13,'2016-11-06 14:59:54','4347655744177127','applePicking'),(14,'apples','hit','rewind','999 Arrowood Drive','Jacksonville','FL',32216,'9044365609','hitrewind@gmail.com',14,'2016-11-06 14:59:54','4925225424543897','applePicking'),(15,'apples','iwanna','forever','11111 Arrowood Drive','Jacksonville','FL',32216,'9044366611','iwannaforever@gmail.com',15,'2016-11-06 14:59:54','4684613424931728','applePicking'),(16,'apples','ayyy','boy','777 Arrowood Drive','Jacksonville','FL',32216,'9044311609','ayyyboy@gmail.com',16,'2016-11-06 14:59:54','4467280523131221','applePicking'),(17,'apples','kitchen','stove','32232 Arrowood Drive','Jacksonville','FL',32216,'9114366609','kitchenstove@gmail.com',17,'2016-11-06 14:59:54','4398508659825438','applePicking'),(18,'apples','younger','year','8888 Arrowood Drive','Jacksonville','FL',32216,'9022366609','youngeryear@gmail.com',18,'2016-11-06 14:59:54','4256714925322579','applePicking'),(19,'apples','applesseed','seedsofapples','1471 Arrowood Drive','Jacksonville','FL',32216,'9045566609','applesseedseedsofapple@gmail.com',19,'2016-11-06 14:59:54','4028364081915282','applePicking'),(20,'apples','Michael','Bender','4363 Bentley Drive','Jacksonville','FL',32216,'9044666609','MichaelBender@gmail.com',20,'2016-11-06 14:59:54','4665229547305113','applePicking'),(21,'apples','Michael','Tashbook','4363 Book Drive','Jacksonville','FL',32216,'9010366609','MichaelTashbook@gmail.com',21,'2016-11-06 14:59:54','4152320677939159','applePicking'),(22,'apples','Richard','Mckenna','4363 Boxman Drive','Jacksonville','FL',32216,'9049166609','RichardMckenna@gmail.com',22,'2016-11-06 14:59:54','4741260848579989','applePicking'),(23,'apples','Leo','Backmire','4363 Compsci Drive','Jacksonville','FL',32216,'9082366609','LeoBackmire@gmail.com',23,'2016-11-06 14:59:54','4258176354221789','applePicking'),(24,'apples','Moms','Spagetti','4363 loud Drive','Jacksonville','FL',32216,'8844366609','MomsSpagetti@gmail.com',24,'2016-11-06 14:59:54','4295992126620602','applePicking'),(25,'apples','Opps','Gravity','4363 gravity Drive','Jacksonville','FL',32216,'9044106609','oppsGravity@gmail.com',25,'2016-11-06 14:59:54','4765034221596741','applePicking');
/*!40000 ALTER TABLE `UserPlus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-07 11:19:34

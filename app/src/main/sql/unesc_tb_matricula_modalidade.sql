-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: unesc
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_matricula_modalidade`
--

DROP TABLE IF EXISTS `tb_matricula_modalidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_matricula_modalidade` (
  `codigo_matricula` int NOT NULL,
  `modalidade` varchar(45) NOT NULL,
  `graduacao` varchar(45) NOT NULL,
  `data_inicio` date DEFAULT NULL,
  `data_fim` date DEFAULT NULL,
  `plano` varchar(45) NOT NULL,
  PRIMARY KEY (`codigo_matricula`,`modalidade`),
  KEY `matriculas_modalidades_f2_idx` (`modalidade`),
  KEY `matriculas_modalidades_f3_idx` (`modalidade`,`graduacao`),
  KEY `matriculas_modalidades_f4_idx` (`modalidade`,`plano`),
  CONSTRAINT `matriculas_modalidades_f1` FOREIGN KEY (`codigo_matricula`) REFERENCES `tb_matricula` (`codigo_matricula`),
  CONSTRAINT `matriculas_modalidades_f2` FOREIGN KEY (`modalidade`) REFERENCES `tb_modalidade` (`modalidade`),
  CONSTRAINT `matriculas_modalidades_f3` FOREIGN KEY (`modalidade`, `graduacao`) REFERENCES `tb_graduacao` (`modalidade`, `graduacao`),
  CONSTRAINT `matriculas_modalidades_f4` FOREIGN KEY (`modalidade`, `plano`) REFERENCES `tb_plano` (`modalidade`, `plano`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_matricula_modalidade`
--

LOCK TABLES `tb_matricula_modalidade` WRITE;
/*!40000 ALTER TABLE `tb_matricula_modalidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_matricula_modalidade` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-29 14:56:39

CREATE TABLE `toon_table` (
  `Toon_idx` int NOT NULL,
  `Toon_name` varchar(105) NOT NULL,
  `Toon_category` varchar(105) DEFAULT NULL,
  `Toon_link` varchar(300) DEFAULT NULL,
  `Toon_update` int DEFAULT NULL,
  `Toon_newupdate` int DEFAULT NULL,
  `Toon_exist` int DEFAULT '0',
  `Toon_imagelink` varchar(300) DEFAULT NULL,
  `Toon_platform` int DEFAULT NULL,
  `Toon_likes` int DEFAULT NULL,
  PRIMARY KEY (`Toon_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `toon_table` (
  `toon_num` int NOT NULL,
  `N_toon` varchar(105) NOT NULL,
  `Toon_category` varchar(105) DEFAULT NULL,
  `Toon_link` varchar(300) DEFAULT NULL,
  `Toon_update` int DEFAULT NULL,
  `Toon_newupdate` int DEFAULT NULL,
  `Toon_Hit_toon` int DEFAULT NULL,
  `Toon_imagelink` varchar(300) DEFAULT NULL,
  `Toon_platform` int DEFAULT NULL,
  `Toon_likes` int DEFAULT NULL,
  PRIMARY KEY (`toon_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
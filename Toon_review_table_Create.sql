CREATE TABLE `toon_review` (
  `Toon_name` varchar(300) NOT NULL,
  `User_id` longtext,
  `Review_content` longtext,
  `Toon_exist_for_review` int DEFAULT '0',
  PRIMARY KEY (`Toon_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
CREATE TABLE `user_table` (
  `USER_ID` varchar(50) NOT NULL,
  `USER_PASSWORD` varchar(45) NOT NULL,
  `USER_NICKNAME` varchar(45) NOT NULL,
  `USER_PICK1` varchar(45) DEFAULT NULL,
  `USER_PICK2` varchar(45) DEFAULT NULL,
  `USER_PICK3` varchar(45) DEFAULT NULL,
  `USER_MAIL` varchar(300) DEFAULT NULL,
  `USER_GENDER` varchar(45) DEFAULT NULL,
  `USER_NAME` varchar(45) DEFAULT NULL,
  `USER_MYPAGE` varchar(45) DEFAULT NULL,
  `USER_FAVORITE` longtext,
  `USER_LIKES` longtext,
  `USER_INTRODUCE` longtext,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

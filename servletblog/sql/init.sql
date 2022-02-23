CREATE TABLE `users` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL,
  `nickname` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  `avatar` varchar(200) NOT NULL
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username_UNIQUE` (`username`)
);


CREATE TABLE `articles` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `type` varchar(20) NOT NULL,
  `content` text NOT NULL,
  `published_date` date NOT NULL,
  PRIMARY KEY (`aid`)
);

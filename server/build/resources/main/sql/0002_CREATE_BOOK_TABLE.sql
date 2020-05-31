CREATE TABLE `mysqlBook` (
  `id` bigint(20) NOT NULL,
  `actual_page` int(11) DEFAULT NULL,
  `mongo_id` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
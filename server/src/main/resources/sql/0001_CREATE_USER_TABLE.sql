CREATE TABLE user (
  `id` bigint(20) NOT NULL,
  `uid` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `dictionary` varchar(1024) DEFAULT NULL,
  `demo_limit` int(11) DEFAULT NULL,
  `premium` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
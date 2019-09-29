DROP TABLE IF EXISTS `user_details`;

create table `user_details` (
  `phone` VARCHAR(20) NOT NULL,
  `user_name` VARCHAR(20) DEFAULT NULL,
  `check_code` VARCHAR(256) DEFAULT NULL,
  `authority` VARCHAR(2048) NOT NULL,
  `login_time` TIMESTAMP,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User details';
DROP TABLE IF EXISTS `oauth_access_token`;

create table `oauth_access_token` (
  `token_id` VARCHAR(256) DEFAULT NULL,
  `token` BLOB DEFAULT NULL,
  `authentication_id` VARCHAR(256) NOT NULL,
  `user_name` VARCHAR(256) DEFAULT NULL,
  `client_id` VARCHAR(256) DEFAULT NULL,
  `authentication` BLOB DEFAULT NULL,
  `refresh_token` VARCHAR(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Oauth access token';
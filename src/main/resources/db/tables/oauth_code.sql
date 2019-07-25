DROP TABLE IF EXISTS `oauth_code`;

create table `oauth_code` (
  `code` VARCHAR(256) DEFAULT NULL,
  `authentication` BLOB DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Oauth code';
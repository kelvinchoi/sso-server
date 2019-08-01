DROP TABLE IF EXISTS `oauth_client_details`;

create table `oauth_client_details` (
  `client_id` VARCHAR(256) NOT NULL,
  `resource_ids` VARCHAR(256) DEFAULT NULL,
  `client_secret` VARCHAR(256) NOT NULL,
  `scope` VARCHAR(256) NOT NULL,
  `authorized_grant_types` VARCHAR(256) NOT NULL,
  `web_server_redirect_uri` VARCHAR(256) DEFAULT NULL,
  `authorities` VARCHAR(256) DEFAULT NULL,
  `access_token_validity` INT DEFAULT 1800,
  `refresh_token_validity` INT DEFAULT NULL,
  `additional_information` VARCHAR(4096) DEFAULT NULL,
  `autoapprove` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Oauth client details';
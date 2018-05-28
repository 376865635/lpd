CREATE TABLE `dianying_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `film_id` int(11) DEFAULT NULL COMMENT '电影对应id',
  `content` longtext COMMENT '页面内容',
  `down_url` varchar(255) DEFAULT NULL COMMENT '下载地址',
  `intime` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `film_id` (`film_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


CREATE TABLE `dianying_id` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '电影id表',
  `film_id` int(11) NOT NULL,
  `type` int(4) DEFAULT NULL COMMENT '0：正常',
  `page_num` int(11) DEFAULT NULL,
  `intime` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `film_id` (`film_id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `TEST`;
CREATE TABLE `TEST` (
  `id` bigint(10) NOT NULL COMMENT 'ID',
  `field1` varchar(10) NOT NULL COMMENT '字段一',
  `field2` varchar(10) NOT NULL COMMENT '字段二',
  `field3` varchar(10) NOT NULL COMMENT '字段三',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TEST
-- ----------------------------
BEGIN;
INSERT INTO `TEST` VALUES (1, '11', '12', '13');
INSERT INTO `TEST` VALUES (2, '21', '22', '23');
INSERT INTO `TEST` VALUES (3, '31', '32', '33');
INSERT INTO `TEST` VALUES (4, '41', '42', '43');
INSERT INTO `TEST` VALUES (5, '51', '52', '53');
INSERT INTO `TEST` VALUES (6, '61', '62', '63');
COMMIT;
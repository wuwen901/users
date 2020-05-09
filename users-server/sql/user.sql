CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `company_id` bigint(20) NOT NULL COMMENT '租户ID',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(255) NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
);
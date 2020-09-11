CREATE TABLE `t_fund_007874` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fund_code` varchar(200) NOT NULL COMMENT 'fund_code',
  `fund_date` date DEFAULT NULL,
  `fund_value` double DEFAULT NULL,
  `fund_percent` double DEFAULT NULL,
  `fund_bull_1` double DEFAULT NULL,
  `fund_bull_2` double DEFAULT NULL,
  `fund_bull_3` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
ALTER TABLE t_fund_day ADD COLUMN `fund_dif` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_dea` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_ema_10` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_ema_20` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_30` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_60` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_120` double NOT NULL DEFAULT '0.0';

ALTER TABLE t_fund_pool6 ADD COLUMN `fund_money` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_pool6 ADD COLUMN `fund_manager` varchar(200) DEFAULT NULL;
ALTER TABLE t_fund_pool6 ADD COLUMN `fund_cdate` date DEFAULT NULL;
ALTER TABLE t_fund_pool6 ADD COLUMN `fund_company` varchar(200) DEFAULT NULL;


ALTER TABLE t_fund_pool6 ADD COLUMN `fund_money` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_pool6 ADD COLUMN `hy_name` varchar(600) DEFAULT NULL;
ALTER TABLE t_fund_pool6 ADD COLUMN `gn_name` varchar(600) DEFAULT NULL;
ALTER TABLE t_fund_pool6 ADD COLUMN `fund_cdate` date DEFAULT NULL;
ALTER TABLE t_fund_pool6 ADD COLUMN `fund_company` varchar(200) DEFAULT NULL;

alter table t_fund_pool6 modify column gn_name varchar(30000);
alter table t_fund_pool6  gn_name column title varchar(20000);

t_fund_day
fund_bull_1
ALTER TABLE t_fund_day ADD COLUMN `fund_bull_1` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_bull_2` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_bull_3` double NOT NULL DEFAULT '0.0';

ALTER TABLE t_s_100 ADD COLUMN `fund_bull_1` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_s_100 ADD COLUMN `fund_bull_2` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_s_100 ADD COLUMN `fund_bull_3` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_s_100 ADD COLUMN `s_type` varchar(200)  DEFAULT NULL;

ALTER TABLE t_fund_day ADD COLUMN `fund_bull_4` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_bull_5` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_bull_6` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_20` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_30` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_60` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_120` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_sum_7` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_sum_30` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_sum_90` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_sum_180` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_fund_day ADD COLUMN `fund_sum_365` double NOT NULL DEFAULT '0.0';

ALTER TABLE t_fund_day ADD COLUMN `fund_sum_365` double NOT NULL DEFAULT '0.0';

ALTER TABLE t_fund_day ADD COLUMN `fund_count` bigint NOT NULL DEFAULT '0';
ALTER TABLE t_fund_day ADD COLUMN `fund_count_value` double NOT NULL DEFAULT '0.0';
varchar(65532)

1.Convert转成日期时间型,在用Like查询。

select * from t_fund_day where convert(fund_date,DATETIME) like '%2019%';


DROP TABLE IF EXISTS `t_fund_gn2_mx`;
CREATE TABLE `t_fund_gn2_mx` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
  `fund_percent` double DEFAULT NULL,
  `gn_name` varchar(20) DEFAULT NULL,
  `fund_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


RENAME TABLE t_s_100 TO t_s_001;



CREATE TABLE `t_s_mx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `week_id` int(3) DEFAULT NULL,
  `value_1` int(3) DEFAULT NULL,
  `value_2` int(3) DEFAULT NULL,
  `value_3` int(3) DEFAULT NULL,
  `value_4` int(3) DEFAULT NULL,
  `value_5` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
ALTER TABLE t_s_mx ADD COLUMN `s_type` varchar(200)  DEFAULT NULL;
ALTER TABLE t_s_mx ADD COLUMN `year_id` int(4)  DEFAULT NULL;
ALTER TABLE t_s_mx ADD COLUMN `s_date` date  DEFAULT NULL;

`fund_date` date DEFAULT NULL,
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



insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent,s_type)values(STR_TO_DATE('2020-09-11', '%Y-%m-%d'),3260.35,3225.78,3262.50,3220.54,221.699,0.79,'sh_001');
insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent,s_type)values(STR_TO_DATE('2020-09-11', '%Y-%m-%d'),12942.95,12711.09,12952.47,12702.62,128.249,1.57,'sz_001');
insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent,s_type)values(STR_TO_DATE('2020-09-11', '%Y-%m-%d'),24503.31,24352.63,24572.66,24293.03,0,0.78,'hk_001');
insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent,s_type)values(STR_TO_DATE('2020-09-11', '%Y-%m-%d'),11087.40,11251.19,11280.41,10945.22,221.514,-0.60,'us_001');


ALTER TABLE t_s_001 ADD COLUMN `fund_bull_4` double NOT NULL DEFAULT '0.0';
ALTER TABLE t_s_001 ADD COLUMN `fund_bull_5` double NOT NULL DEFAULT '0.0';


delete from t_fund_day where fund_date = STR_TO_DATE('2020-10-14', '%Y-%m-%d');
delete from t_fund_day where fund_date = STR_TO_DATE('2020-10-15', '%Y-%m-%d');
delete from t_fund_day where fund_date = STR_TO_DATE('2020-10-16', '%Y-%m-%d');

select count(*) from  t_fund_day where fund_date = STR_TO_DATE('2020-10-14', '%Y-%m-%d');

Alpha	69.9816	19.5299	实际收益和平均预期风险收益的差额高于同类
Beta	1.0565	0.8418	收益受大盘波动的影响高于同类
Sharpe	3.2509	1.5225	承担单位风险所获得的风险收益高于同类
收益标准差	29.4827	22.0930	收益的波动程度高于同类
下行风险	14.0550	12.6504	亏损风险高于同类
最大回撤	-20.2568	-14.9725	最大回撤幅度高于同类

 MySQL 添加列，修改列，删除列

ALTER TABLE：添加，修改，删除表的列，约束等表的定义。

    查看列：desc 表名;
    修改表名：alter table t_book rename to bbb;
    添加列：alter table 表名 add column 列名 varchar(30);
    删除列：alter table 表名 drop column 列名;
    修改列名MySQL： alter table bbb change nnnnn hh int;
    修改列名SQLServer：exec sp_rename't_student.name','nn','column';
    修改列名Oracle：lter table bbb rename column nnnnn to hh int;
    修改列属性：alter table t_book modify name varchar(22);

sp_rename：SQLServer 内置的存储过程，用与修改表的定义。





MySQL 查看约束，添加约束，删除约束 添加列，修改列，删除列


    查看表的字段信息：desc 表名;
    查看表的所有信息：show create table 表名;
    添加主键约束：alter table 表名 add constraint 主键 （形如：PK_表名） primary key 表名(主键字段);
    添加外键约束：alter table 从表 add constraint 外键（形如：FK_从表_主表） foreign key 从表(外键字段) references 主表(主键字段);
    删除主键约束：alter table 表名 drop primary key;
    删除外键约束：alter table 表名 drop foreign key 外键（区分大小写）;

    修改表名：alter table t_book rename to bbb;
    添加列：alter table 表名 add column 列名 varchar(30);
    删除列：alter table 表名 drop column 列名;
    修改列名MySQL： alter table bbb change nnnnn hh int;
    修改列名SQLServer：exec sp_rename't_student.name','nn','column';
    修改列名Oracle：alter table bbb rename column nnnnn to hh int;
    修改列属性：alter table t_book modify name varchar(22);

CREATE TABLE `t_fund_pool9` (
 `id` int(20) NOT NULL AUTO_INCREMENT,
 fund_code varchar(100),
 fund_type varchar(100),
 fund_name varchar(100),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `t_fund_pool9` (
 `id` bigint(20) PRIMARY KEY,
 fund_code varchar(100),
 fund_type varchar(100),
 fund_name varchar(100)
) ;

MSCI 	MSCI指数
CSI 	中证指数
SSE 	上交所指数
SZSE 	深交所指数
CICC 	中金指数
SW 	申万指数
OTH 	其他指数

ALTER TABLE t_fund_mx ADD COLUMN `zs_code` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `zs_name` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `zs_mx` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `zs_bd` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_1` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_3` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_6` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_y` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_p` varchar(100) DEFAULT NULL;


ALTER TABLE t_fund_mx ADD COLUMN `v_m` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_j` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_d` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_s` varchar(100) DEFAULT NULL;
ALTER TABLE t_fund_mx ADD COLUMN `v_price` decimal(10,2) not null default '0.0';

alter table news  modify column title varchar(130);

ALTER TABLE t_fund_mx MODIFY v_y decimal(10,2) not null default '0.0';
ALTER TABLE t_fund_mx MODIFY v_1 decimal(10,2) not null default '0.0';
ALTER TABLE t_fund_mx MODIFY v_3 decimal(10,2) not null default '0.0';
ALTER TABLE t_fund_mx MODIFY v_6 decimal(10,2) not null default '0.0';


在A股市场长期投资就是四大方向：消费、医药、金融、科技。
这四大板块往往是一轮牛熊之后会创一次新高。
另外周期性板块方向是券商、有色、军工、钢铁、煤炭、半导体、芯片等等。

BIAS
乖离率=[(当日收盘价-N日平均价)/N日平均价]*100%
其中N，一般5、6、10、12、24、30和72。在实际运用中，短线使用6日乖离率较为有效，中线则放大为10日或12日。


1.股价高于平均线，视为强势；股价低于平均线，视为弱势
2.平均线向上涨升，具有助涨力道；平均线向下跌降，具有助跌力道；
3.二条以上平均线向上交叉时，买进；
4.二条以上平均线向下交叉时，卖出；
5.移动平均线的信号经常落后股价，若以EXPMA、VMA辅助，可以改善。

1.股价高于平均线，视为强势；股价低于平均线，视为弱势；
2.平均线向上涨升，具有助涨力道；平均线向下跌降，具有助跌力道；
3.二条以上平均线向上交叉时，买进；
4.二条以上平均线向下交叉时，卖出；
5.VMA比一般平均线的敏感度更高，消除了部份平均线落后的缺陷。

1.EXPMA一般以观察12日和50日二条均线为主；
2.12日指数平均线向上交叉50日指数平均线时，买进；
3.12日指数平均线向下交叉50日指数平均线时，卖出；
4.EXPMA是多种平均线计算方法的一；
5.EXPMA配合MTM指标使用，效果更佳。

MTM指标技术分析要点：

1、动量指标以0轴为中心线，可以分为上下六等份的超买超卖区。

2、在极端行情中，当动量指标大于+3时，表明股价将强劲上升；当动量指标小于-3时，表明股价处于弱势行情中，小散不宜抢反弹。

3、在中期行情中，当动量指标大于+2时，表明股价波段上涨行情即将结束；当动量指标小于-2时，表明股价波段下跌行情即将结束。

4、在短线行情中，当动量指标大于+1时，表明股价将面临调整压力；当动量指标小于-1时，表明股价面临反弹要求。

CREATE TABLE `zs_mx` (
 `id` int(20) NOT NULL AUTO_INCREMENT,
 zs_name varchar(100),
 zs_code varchar(100),
 `v_date` varchar(100),
 `v_open` varchar(100),
 `v_close` varchar(100),
 `v_high` varchar(100),
 `v_low` varchar(100),
 `v_p` decimal(10,2) not null default '0.0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE fund_mx (
id int(20) NOT NULL AUTO_INCREMENT,
fund_code varchar(200),
fund_date date,
fund_value decimal(10,2) not null default '0.0',
fund_percent decimal(10,2) not null default '0.0',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

//葛兰碧八大法则：
    //    MA均线
    //（1）平均线从下降逐渐转为盘式上升，而价格从平均线下方突破平均线，为买进信号。
    //            （2）价格虽然跌破平均线，但又立刻回升到平均线上，此时平均线仍然持续上升，仍为买进信号。
    //            （3）价格趋势走在平均线上，价格下跌并未跌破平均线且立刻反转上升，也是买进信号。
    //            （4）价格突然暴跌，跌破平均线，且远离平均线，则有可能反弹上升，也为买进时机。
    //            （5）平均线从上升逐渐转为盘局或下跌，而价格向下跌破平均线，为卖出信号。
    //            （6）价格虽然向上突破平均线，但又立刻回跌至平均线下，此时平均线仍然持续下降，仍为卖出信号。
    //            （7）价格趋势走在平均线下，价格上升并未突破平均线且立刻反转下跌，也是卖出信号。
    //            （8）价格突然暴涨，突破平均线，且远离平均线，则有可能反弹回跌，也为卖出时机。
    //    对葛兰碧法则的记忆，只要掌握了支撑和压力的思想就不难记住。

    //①乖离率可分为正乖离率与负乖离率，若股价在平均线之上，则为正乖离；股价在平均线之下，则为负乖离；当股价与平均线相交时，则乖离率为零。正的乖离率愈大，表示短期获利愈大，则获利回吐可能性愈高；负的乖离率愈大，则空头回补的可能性也愈高。
//②股价与十日平均线乖离率达+8%以上为超买现象，是卖出时机；当其达-8%以下时为超卖现象，为买入时机。
//            ③股价与三十日平均线乖离率达+16%以上为超买现象，是卖出时机；当其达－16%以下为超卖现象，为买入时机。
//            ④个别股因受多空激战的影响，股价与各种平均线的乖离容易偏高，但发生次数并不多。
//            ⑤每股行情股价与平均线之间的乖离率达到最大百分比时，就会向零值靠近，甚至会低于零或高于零，这是正常现象。
//            ⑥多头市场的暴涨与空头市场的暴跌，会使乖离达到意想不到的百分比，但出现次数极少，时间也短，可视为一特例。
//            ⑦在大势上升市场如遇负乖离，可以持回跌价买进，因为进场危险性小。
//            ⑧在大势下跌的走势中如遇正乖离，可以持回升高价出售。


乖离率=[(当日收盘价-N日平均价)/N日平均价]*100%
其中N，一般5、6、10、12、24、30和72

 MA = (C1+C2+C3+C4+C5+....+Cn)/n C 为收盘价，n 为移动平均周期数


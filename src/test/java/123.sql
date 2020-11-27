CREATE TABLE `t_fund_pool8` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fund_code` varchar(100) DEFAULT NULL,
  `fund_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


SELECT concat('DROP TABLE IF EXISTS ', table_name, ';')
FROM information_schema.tables
WHERE table_schema = 'stock';

String str = URLEncoder.encode("中国","utf-8");
System.out.println(str);
//解码
String str1=URLDecoder.decode(str, "UTF-8");
System.out.println(str1);
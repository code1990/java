import org.junit.Test;
import util.JDBCUtil;
import util.TxtUtil;

import java.io.File;
import java.sql.Connection;
import java.util.List;

/**
 * @author 911
 * @date 2020-11-21 12:40
 */
public class SqlTest {

    @Test
    public void createTable(){
        StringBuilder sb1 = new StringBuilder("CREATE TABLE fund_mx (\n" +
                "id int(20) NOT NULL AUTO_INCREMENT,\n" +
                "fund_code varchar(200),\n" +
                "fund_date date,\n" +
                "fund_value decimal(10,2) not null default '0.0',\n" +
                "fund_percent decimal(10,2) not null default '0.0',\n" +
                "PRIMARY KEY (id)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");

//        StringBuilder sb2 = new StringBuilder("CREATE TABLE `t_fund_pool9` (\n" +
//                " `id` bigint(20) PRIMARY KEY,\n" +
//                " fund_code varchar(100),\n" +
//                " fund_type varchar(100),\n" +
//                " fund_name varchar(100)\n" +
//                ") ;");
        Connection connection1 = JDBCUtil.getConnection("stock");
        JDBCUtil.executeSql(connection1,sb1.toString());

//        String path ="D:\\github\\java\\stock.db";
//        Connection connection2 = SQLiteUtil.getConnection(path);
//        SQLiteUtil.executeSql(connection2,sb2.toString());

    }

    @Test
    public void getInfo(){
        List<String> list = TxtUtil.readTxt(new File("C:\\Users\\xiala\\Desktop\\111.txt"));
        for (int i = 0; i < list.size(); i++) {
            String info = list.get(i).replace("TABLE_COLUMN.put(","").split(",")[0];
//            System.out.println(info);
            System.out.println("else if(value.equals("+info+")) {\n" +
                    "\t\t\t\t\t\tif(count"+(i+1)+"%2==0) {\n" +
                    "\t\t\t\t\t\t\tallFltInfos.sort((fi1,fi2) -> fi1.getSchDepDt().compareTo(fi2.getSchDepDt()));\n" +
                    "\t\t\t\t\t\t}else {\n" +
                    "\t\t\t\t\t\t\tallFltInfos.sort((fi1,fi2) -> fi2.getSchDepDt().compareTo(fi1.getSchDepDt()));\n" +
                    "\t\t\t\t\t\t}\n" +
                    "\t\t\t\t\t\tcount"+(i+1)+"++;\n" +
                    "\t\t\t\t\t}");
        }
    }
}

package word;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import util.JDBCUtil;

import java.awt.*;

/**
 * @author 911
 * @date 2020-09-25 09:23
 */
public class SimpleReportDemo {

    public static void buildReport() throws Exception {
        JasperReportBuilder report = DynamicReports.report();//创建空报表
        //样式
        StyleBuilder boldStl = DynamicReports.stl.style().bold();
        StyleBuilder boldCenteredStl = DynamicReports.stl.style(boldStl).setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder titleStl = DynamicReports.stl.style(boldCenteredStl).setFontSize(16);
        StyleBuilder columnTitleStl = DynamicReports.stl.style(boldCenteredStl).setBorder(DynamicReports.stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
        String sql = "select id,s_close,s_date,fund_bull_1,fund_bull_2,fund_bull_3,fund_bull_4  from t_s_001 where s_type='us_001' and s_date >= str_to_date('2020-01-01', '%Y-%m-%d')  order by  s_date;";
        report.columns(Columns.column("ID", "id", DataTypes.integerType()).
                        setHorizontalAlignment(HorizontalAlignment.CENTER),//列
                Columns.column("手机号段", "s_close", DataTypes.stringType()),
                Columns.column("运营商", "s_date", DataTypes.stringType()),
                Columns.column("省份", "fund_bull_1", DataTypes.stringType()),
                Columns.column("城市", "fund_bull_2", DataTypes.stringType()),
                Columns.column("品牌", "fund_bull_3", DataTypes.stringType()))
                .setColumnTitleStyle(columnTitleStl)
                .setHighlightDetailEvenRows(true)
                .title(Components.text("手机号段").setStyle(titleStl))//标题
                .pageFooter(Components.pageXofY().setStyle(boldCenteredStl))//页角
                .setDataSource(sql, JDBCUtil.getConnection());//数据源


        //显示报表
        report.show();
        //生成PDF文件
        //report.toPdf(new FileOutputStream("D:/test.pdf"));
    }

    public static void main(String orgs[]) throws Exception {
        buildReport();
    }
}
package word;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import util.JDBCUtil;

import java.awt.*;
import java.util.List;

/**
 * @author 911
 * @date 2020-09-25 08:28
 */
public class ChartTest {

    public static void main(String[] args) {
        test01();
        test02();
    }

    //    @Test
    public static void test01() {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        CategoryDataset mDataset = GetDataset();
        JFreeChart mChart = ChartFactory.createLineChart(
                "折线图",//图名字
                "年份",//横坐标
                "数量",//纵坐标
                mDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                true, // 采用标准生成器
                false);// 是否生成超链接

        CategoryPlot mPlot = (CategoryPlot) mChart.getPlot();
        mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
        mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
        mPlot.setOutlinePaint(Color.RED);//边界线

        ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);
    }

    public static CategoryDataset GetDataset() {
        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
        mDataset.addValue(1, "First", "2013");
        mDataset.addValue(3, "First", "2014");
        mDataset.addValue(2, "First", "2015");
        mDataset.addValue(6, "First", "2016");
        mDataset.addValue(5, "First", "2017");
        mDataset.addValue(12, "First", "2018");
        mDataset.addValue(14, "Second", "2013");
        mDataset.addValue(13, "Second", "2014");
        mDataset.addValue(12, "Second", "2015");
        mDataset.addValue(9, "Second", "2016");
        mDataset.addValue(5, "Second", "2017");
        mDataset.addValue(7, "Second", "2018");
        return mDataset;
    }

    public static void test02() {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        XYSeriesCollection mCollection = GetCollection();
        JFreeChart mChart = ChartFactory.createXYLineChart(
                "折线图",
                "X",
                "Y",
                mCollection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);

    }

    public static XYSeriesCollection GetCollection() {
        XYSeriesCollection mCollection = new XYSeriesCollection();
        XYSeries mSeriesFirst = new XYSeries("First");
        mSeriesFirst.add(1.0D, 1.0D);
        mSeriesFirst.add(2D, 4D);
        mSeriesFirst.add(3D, 3D);
        mSeriesFirst.add(4D, 5D);
        mSeriesFirst.add(5D, 5D);
        mSeriesFirst.add(6D, 7D);
        mSeriesFirst.add(7D, 7D);
        mSeriesFirst.add(8D, 8D);
        XYSeries mSeriesSecond = new XYSeries("Second");
        mSeriesSecond.add(1.0D, 5D);
        mSeriesSecond.add(2D, 7D);
        mSeriesSecond.add(3D, 6D);
        mSeriesSecond.add(4D, 8D);
        mSeriesSecond.add(5D, 4D);
        mSeriesSecond.add(6D, 4D);
        mSeriesSecond.add(7D, 2D);
        mSeriesSecond.add(8D, 1.0D);
        mCollection.addSeries(mSeriesFirst);
        mCollection.addSeries(mSeriesSecond);
        return mCollection;
    }

    public void test03(){

        String sql = "select s_close,s_date,fund_bull_1,fund_bull_2,fund_bull_3,fund_bull_4  from t_s_001 where s_type='us_001' and s_date >= str_to_date('2020-01-01', '%Y-%m-%d')  order by  s_date;";
        List<String[]> list = JDBCUtil.getResultList(sql);
        // 绘图数据集
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (String[] array: list) {
//            dataSet.setValue(new Double(array[0]), array[1], new Double(array[2]),new Double(array[3]),new Double(array[4]),new Double(array[5]));
        }
        //如果把createLineChart改为createLineChart3D就变为了3D效果的折线图
        JFreeChart  chart = ChartFactory.createLineChart("图表标题", "X轴标题", "Y轴标题", dataSet,
                PlotOrientation.VERTICAL, // 绘制方向
                true, // 显示图例
                true, // 采用标准生成器
                false // 是否生成超链接
        );
//        chart.getTitle().setFont(titleFont); // 设置标题字体
//        chart.getLegend().setItemFont(font);// 设置图例类别字体
//        chart.setBackgroundPaint(bgColor);// 设置背景色
//        获取绘图区对象
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY); // 设置绘图区背景色
        plot.setRangeGridlinePaint(Color.WHITE); // 设置水平方向背景线颜色
        plot.setRangeGridlinesVisible(true);// 设置是否显示水平方向背景线,默认值为true
        plot.setDomainGridlinePaint(Color.WHITE); // 设置垂直方向背景线颜色
        plot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false


        CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setLabelFont(font); // 设置横轴字体
//        domainAxis.setTickLabelFont(font);// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(2);

        ValueAxis rangeAxis = plot.getRangeAxis();
//        rangeAxis.setLabelFont(font);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//Y轴显示整数
        rangeAxis.setAutoRangeMinimumSize(1);   //最小跨度
        rangeAxis.setUpperMargin(0.18);//上边距,防止最大的一个数据靠近了坐标轴。
        rangeAxis.setLowerBound(0);   //最小值显示0
        rangeAxis.setAutoRange(false);   //不自动分配Y轴数据
        rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));     // 设置坐标标记大小
        rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色



        // 获取折线对象
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        BasicStroke realLine = new BasicStroke(1.8f); // 设置实线
        // 设置虚线
        float dashes[] = { 5.0f };
        BasicStroke brokenLine = new BasicStroke(2.2f, // 线条粗细
                BasicStroke.CAP_ROUND, // 端点风格
                BasicStroke.JOIN_ROUND, // 折点风格
                8f, dashes, 0.6f);
        for (int i = 0; i < dataSet.getRowCount(); i++) {
            if (i % 2 == 0) {
                renderer.setSeriesStroke(i, realLine); // 利用实线绘制
            }
            else {
                renderer.setSeriesStroke(i, brokenLine); // 利用虚线绘制
            }
        }

        plot.setNoDataMessage("无对应的数据，请重新查询。");
//        plot.setNoDataMessageFont(titleFont);//字体的大小
        plot.setNoDataMessagePaint(Color.RED);//字体颜色

    }

}

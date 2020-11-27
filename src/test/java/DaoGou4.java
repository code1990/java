import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 911
 * @date 2020-11-08 17:46
 *
 * 通达信短绝底强升追涨指标公式
 */
public class DaoGou4 {

    @Test
    public void getInfo111(){
        String path ="C:\\Users\\xiala\\Desktop\\test\\22.txt";
        List<String> list = TxtUtil.readTxt(new File(path));
        String path2 ="C:\\Users\\xiala\\Desktop\\test\\11.txt";
        List<String> list2 = TxtUtil.readTxt(new File(path2));
        for (int i = 0; i <list.size() ; i++) {
            System.out.println("//"+list.get(i));
            System.out.println(list2.get(i));
        }
    }

    /**
     * @throws Exception 连续跌3天 或者跌幅在3%以上给与加仓
     */
    @Test
    public void getInfo() throws Exception {
        //第一件事情是更新排名信息
        List<String> list = TxtUtil.readTxt(new File("pool3.txt"));

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String logStr = TxtUtil.readTxtStr(new File("000.log"));
//        if (!logStr.contains(sdf.format(new Date()))) {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < list.size(); i++) {
//                String[] array = list.get(i).split("\t");
//                String code = list.get(i).split("\t")[0];
//                Pair tuple = getYearValue(code);
//                sb.append(array[0] + "\t" + array[1] + "\t" + array[2] + "\t" + tuple.getLeft() + "\t" + tuple.getRight() + "\n");
//            }
//            TxtUtil.writeTxt(new File("pool3.txt"), sb.toString());
//            TxtUtil.writeTxt(new File("000.log"),sdf.format(new Date()));
//            list = TxtUtil.readTxt(new File("pool3.txt"));
//        }
        List<String> codeList = new ArrayList<>();
        Map<String, ImmutableTriple<String, Double, Double>> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            codeList.add(array[0]);
            ImmutableTriple<String, Double, Double> tuple3 = ImmutableTriple.of(array[1], new Double(array[3]), new Double(array[4]));
            map.put(array[0], tuple3);
        }
        String startDate = DateUtil.getMoth(-2);
        String endDate = DateUtil.getCurrentStr();
        List<String> urlList = new ArrayList<>();
        HttpClient httpClient = HttpClients.createDefault();

        for (int i = 0; i < codeList.size(); i++) {
            String code = codeList.get(i);
            String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                    ".aspx?type=lsjz&code=" + code + "&page=1&per=20&sdate=" + startDate + "&edate=" + endDate;
            urlList.add(url);
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            String code = codeList.get(i);
            Document document = HttpUtil.getHtml(url);
            List<String> htmlList = new ArrayList<>();
            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            for (int j = elements.size() - 1; j >= 1; j--) {
                String str = elements.get(j).text();
                if (str.contains("暂无数据")) {
                    continue;
                }
                htmlList.add(str);
            }
            if (htmlList.size() < 20) {
                continue;
            }
            double[] x = new double[20];
            double currentPercent = 0.0;
            double onePercent = 0.0;
            for (int k = 0; k < x.length; k++) {
                String str = htmlList.get(k);
                String[] array = str.split(" ");
//                System.out.println(array.length);
                x[k] = new Double(array[1]);
                if (array.length == 6) {
                    if (k == 19) {
                        currentPercent = new Double(array[3].replace("%", ""));
                    }
                    if (k == 18) {
                        onePercent = new Double(array[3].replace("%", ""));
                    }
                } else if (array.length == 5) {
                    if (k == 19) {
                        currentPercent = new Double(array[2].replace("%", ""));
                    }
                    if (k == 18) {
                        onePercent = new Double(array[2].replace("%", ""));
                    }
                }

            }
            double currentValue = x[19];
//            double oneValue = x[18];
            double bull2 = MathUtil.getAvg(x);
            double stat = MathUtil.getStandardeviation(x);
            double bull1 = bull2 + 1.7 * stat;
            double bull3 = bull2 - 1.7 * stat;
            String status = "";
            Pair<Double, Double> tuple = StockUtil.getGzInfo(code);
            //平均估值偏差
            ImmutableTriple<String, Double, Double> tuple3 = map.get(code);
            double gzRepair = tuple3.right;
            //接近真实的估值百分比
            double gzPercent = tuple.getRight() - gzRepair;
            double gzValue = tuple.getLeft();
            if (gzPercent == 0.0 && gzValue == 0.0) {
                continue;
            }
            //昨天的净值大于中轨
            if (currentValue > bull2) {
                //今日估值接近上轨
                if (gzValue >= bull1) {
                    //今日估值越过上轨卖出
                    if (gzValue >= (bull2 + 2 * stat)) {
                        status = "净值中轨上\t估值突破上轨\t卖出";
                        //今日估值在上轨附近
                    } else {
                        status = "净值中轨上\t估值接近上轨\t卖出";
                    }
                }
//                else {
//                    if (gzPercent <= -2.0) {
//                        if (gzValue <= bull2) {
//                            status = "净值中轨上\t估值跌破中轨\t买入";
//                        } else {
//                            status = "净值中轨上\t估值中轨上\t超卖可买";
//                        }
//                    }
//                }
            } else {
                //今日的估值接近下轨
                if (gzValue <= bull3) {
                    //估值跌破下轨 买入信号
                    if (gzValue <= (bull2 - 2 * stat)) {
                        status = "净值中轨下\t估值突破下轨\t买入";
                        //估值接近下轨
                    } else {
                        status = "净值中轨下\t估值接近下轨\t买入";
                    }
                }
//                else {
//                    if (gzPercent <= -2.0) {
//                        status = "净值中轨下\t估值下轨上\t超卖可买";
//                    }
//                }
            }
            //买入的条件必须是下轨未底线 和超卖行情为底线
            boolean isBuy = false;
            //连续三日下跌 并且下跌幅度大于-3
            if ((onePercent < 0) && (currentPercent < 0) && (gzPercent < 0) && (onePercent + currentPercent + gzPercent) < -3) {
                isBuy = true;
                if (gzValue < bull2) {
                    status = "3日下跌-3估值中轨下\t买入\t"+gzPercent;
                }
                if (gzValue <= (bull2 + 1 * stat)) {
                    status = "3日下跌-3估值中轨上\t买入\t"+gzPercent;
                }
                //连续2日下跌 并且下跌幅度大于-4
            } else if (currentPercent < 0 && gzPercent < 0 && (currentPercent + gzPercent) < -4) {
                isBuy = true;
                if (gzValue < bull2) {
                    status = "2日下跌-4估值中轨下\t买入\t"+gzPercent;
                }
                if (gzValue <= (bull2 + 1 * stat)) {
                    status = "2日下跌-4估值中轨上\t买入\t"+gzPercent;
                }
            }
//            if (isBuy) {
//                if (gzValue < bull2) {
//                    status = "估值中轨下\t买入";
//                }
//                if (gzValue <= (bull2 + 1 * stat)) {
//                    status = "估值中轨上\t买入";
//                }
//            }
            //使用BBI多空指标 捕捉市场短期的超卖机会
            //今日估值大于中轨，小于bbi,并且估值跌幅超过-1.5 是为超卖行为 超卖可以适当介入
            if (gzValue > bull2) {
                double bbiValue = StockUtil.getBbi(x);
                if (gzValue <= bbiValue && gzPercent < -1.5) {
                    status = "中轨上方的超级买点\t"+gzPercent;
                }
            }

            if (!"".equals(status)) {
                if (status.contains("买")) {
//                    System.out.println(currentValue);
                    System.out.println(i + "\t" + code + "\t" + map.get(code) + "\t" + status);
//                    sb.append(code + "\t" + tuple3.middle + "\t" + currentValue + "\t" + gzValue + "\t" + gzPercent + "\t" + tuple3.left + "\t" + "\t" + status + "\n");
                    sb2.append("33#" + code + "\n");
                    sb5.append(list.get(i)+"\t"+status+"\n");
                } else {
                    System.err.println(i + "\t" + code + "\t" + map.get(code) + "\t" + status);
                    sb3.append(code + "\t" + currentValue + "\t" + gzValue + "\t" + gzPercent + "\t" + map.get(code) + "\t" + status + "\n");
                    sb4.append("33#" + code + "\n");
                }
            }
        }
//        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_buy.txt", sb.toString());
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_buy.EBK", sb2.toString());
//        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_sell.txt", sb3.toString());
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_sell.EBK", sb4.toString());
//        getInfo11111();
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\compare.txt",sb5.toString());

    }
    @Test
    public void getInfo11111(){
        //基金成交量为0
        /*int vol = 0;
        //基金的成交额为0.0
        double volM = 0.0;
        //基金的开盘价=收盘价=最高价=最低价
        double open=1.3251,close=1.3251,high=1.3251,low=1.3251;
        //涨幅等于增幅百分比
        double percent =0.0;
        //涨幅0.00
        double percentZ=0.0;*/
        double[] x = new double[20];

        /**
         *
         * //AA赋值:(开盘价+最高价+最低价+收盘价)/4
         * AA:=(O+H+L+C)/4;
         //BB赋值:AA的3日简单移动平均
         BB:=MA(AA,3);
         //CC赋值:如果AA>1日前的AA,返回AA*成交量(手),否则返回0的4日累和/如果AA<1日前的AA,返回AA*成交量(手),否则返回0的4日累和
         CC:=SUM(IF(AA>REF(AA,1),AA*VOL,0),4)/SUM(IF(AA<REF(AA,1),AA*VOL,0),4);
         //DD赋值:1日前的100-(100/(1+CC))
         DD:=REF(100-(100/(1+CC)),1);
         */
        double aa = x[19];//AA赋值:(开盘价+最高价+最低价+收盘价)/4 ; //基金的开盘价=收盘价=最高价=最低价
        double aaPre = MathUtil.getAvg(StockUtil.getArrayByIndex(x,3));
        double bb =  11.0   ;//BB赋值:AA的3日简单移动平均;//最近三日收盘价的平均值
        double cc=0.0;//VOL成交量一直为0 所以cc恒为0
        double dd = 0.0;//获取前一日的cc cc恒为0 dd=0

        /**
         *
         * //LA赋值:1日前的收盘价
         LA:=REF(CLOSE,1);
         //LB赋值:(((收盘价-LA)和0的较大值的5日[1日权重]移动平均 / (收盘价-LA)的绝对值的5日[1日权重]移动平均) * 100)
         LB:=((SMA(MAX((CLOSE - LA),0),5,1) / SMA(ABS((CLOSE - LA)),5,1)) * 100);
         //LC赋值:(最高价 - 1日前的最高价)
         LC:=(HIGH - REF(HIGH,1));
         //LD赋值:(1日前的最低价 - 最低价)
         LD:=(REF(LOW,1) - LOW);
         //LE赋值:(最高价-最低价)和(最高价-1日前的收盘价)的绝对值的较大值和(最低价-1日前的收盘价)的绝对值的较大值的10日累和
         LE:=SUM(MAX(MAX((HIGH - LOW),ABS((HIGH - REF(CLOSE,1)))),ABS((LOW - REF(CLOSE,1)))),10);
         //LF赋值:如果((LC>0)AND(LC>LD)),返回LC,否则返回0的10日累和
         LF:=SUM(IF(((LC > 0) AND (LC > LD)),LC,0),10);
         //LG赋值:如果((LD>0)AND(LD>LC)),返回LD,否则返回0的10日累和
         LG:=SUM(IF(((LD > 0) AND (LD > LC)),LD,0),10);
         //LH赋值:((LF * 100) / LE)
         LH:=((LF * 100) / LE);
         //LI赋值:((LG * 100) / LE)
         LI:=((LG * 100) / LE);
         //LJ赋值:(((LI-LH)的绝对值/(LI+LH))*100)的5日简单移动平均
         LJ:=MA(((ABS((LI - LH)) / (LI + LH)) * 100),5);
         *
         */
        double la = x[18];//获取前一日的收盘价
        //SMA(X,N,M)，求X的N日移动平均，M为权重 在大多数情况下，M取1而不取2
        //SMA 移动平均
        //返回移动平均。
        //用法：　SMA(X，N，M)　X的M日移动平均，M为权重，如Y=(X*M+Y'*(N-M))/N
//        double lb =

        /**
         *

         //AV赋值:(LB + LJ)
         AV:=(LB + LJ);
         //LK赋值:((100 * (10日内最高价的最高值 - 收盘价)) / (10日内最高价的最高值 - 10日内最低价的最低值))
         LK:=((100 * (HHV(HIGH,10) - CLOSE)) / (HHV(HIGH,10) - LLV(LOW,10)));
         //LL赋值:(LB - LK)
         LL:=(LB - LK);
         //LM赋值:(AV + LL)
         LM:=(AV + LL);
         //向导线赋值:1日内LM的最低值
         向导线:=LLV(LM,1);
         //T赋值:如果(收盘价的有效数据周期数>7),返回(100)*((收盘价的7日简单移动平均)/(7日内收盘价的7日简单移动平均的最低值)-1),否则返回1
         T:=IF((BARSCOUNT(C) > 7),(100)*((MA(C,7))/(LLV(MA(C,7),7)) - 1),1);
         //N赋值:如果(T>0.35),返回6,否则返回65
         N:=IF((T > 0.35),6,65);
         //W赋值:((-((100)*(21日内最高价的最高值 - 收盘价))/(21日内最高价的最高值 - 21日内最低价的最低值))),线宽为2
         W:=((-((100)*(HHV(H,21) - C))/(HHV(HIGH,21) - LLV(L,21)))),LINETHICK2;
         //RV2赋值:(收盘价-27日内最低价的最低值)/(27日内最高价的最高值-27日内最低价的最低值)*100
         RV2:=(CLOSE-LLV(LOW,27))/(HHV(HIGH,27)-LLV(LOW,27))*100;
         //RG2赋值:(收盘价-5日内最低价的最低值)/(5日内最高价的最高值-5日内最低价的最低值)*100
         RG2:=(CLOSE-LLV(LOW,5))/(HHV(HIGH,5)-LLV(LOW,5))*100;
         //J01赋值:(最高价+最低价)/2
         J01:=(HIGH+LOW)/2;
         //QJ1赋值:J01的3日指数移动平均
         QJ1:=EMA(J01,3);
         //A1赋值:15日内AA的最高值
         A1:=HHV(AA,15);
         //A2赋值:15日内AA的最低值
         A2:=LLV(AA,15);
         //A3赋值:A1-A2
         A3:=A1-A2;
         //A4赋值:(AA-A2)/A3的2日指数移动平均*100
         A4:=EMA((AA-A2)/A3,2)*100;
         //RG赋值:(收盘价-5日内最低价的最低值)/(5日内最高价的最高值-5日内最低价的最低值)*100
         RG:=(CLOSE-LLV(LOW,5))/(HHV(HIGH,5)-LLV(LOW,5))*100;
         //RV赋值:(收盘价-27日内最低价的最低值)/(27日内最高价的最高值-27日内最低价的最低值)*100
         RV:=(CLOSE-LLV(LOW,27))/(HHV(HIGH,27)-LLV(LOW,27))*100;
         //J11赋值:如果收盘价>QJ1,返回成交量(手),否则返回0
         J11:=IF(CLOSE>QJ1,VOL,0);
         //J21赋值:如果收盘价<QJ1,返回成交量(手),否则返回0
         J21:=IF(CLOSE<QJ1,VOL,0);
         //BB1赋值:J11-J21的22日累和的3日简单移动平均
         BB1:=MA(SUM(J11-J21,22),3);
         //VAR03赋值:(BB-CC)
         VAR03:=(BB-CC);
         //股市黑客赋值:RV的3日[1日权重]移动平均
         股市黑客:=SMA(RV,3,1);
         //普通投资者赋值:股市黑客的5日[1日权重]移动平均
         普通投资者:=SMA(股市黑客,5,1);
         //KK1赋值:RG的3日[1日权重]移动平均的2日指数移动平均,COLORFFFFFF
         KK1:=EMA(SMA(RG,3,1),2),COLORFFFFFF;
         //DD2赋值:KK1的3日[1日权重]移动平均,COLOR00FFFF
         DD2:=SMA(KK1,3,1),COLOR00FFFF;
         //RSV3赋值:(((收盘价 - 8日内最低价的最低值) / (8日内最高价的最高值 - 8日内最低价的最低值)) * 100)
         RSV3:=(((CLOSE - LLV(LOW,8)) / (HHV(HIGH,8) - LLV(LOW,8))) * 100);
         //K11赋值:RSV3的3日[1日权重]移动平均
         K11:=SMA(RSV3,3,1);
         //D11赋值:K11的3日[1日权重]移动平均
         D11:=SMA(K11,3,1);
         //KK5赋值:RSV3的3日[1日权重]移动平均的整数部分
         KK5:=INTPART(SMA(RSV3,3,1));
         //DD5赋值:K11的3日[1日权重]移动平均的整数部分
         DD5:=INTPART(SMA(K11,3,1));
         //JJ5赋值:((3*K11)-(2*D11))的整数部分
         JJ5:=INTPART(((3 * K11) - (2 * D11)));
         //N1赋值:(KK5 + DD5)
         N1:=(KK5 + DD5);
         //KX赋值:(N1 < 25)
         KX:=(N1 < 25);
         //KX1赋值:29日前的KX
         KX1:=REF(KX,29);
         //XX赋值:(N1 < 18)
         XX:=(N1 < 18);
         //XX1赋值:31日前的XX
         XX1:=REF(XX,31);
         //XXX赋值:(N1 < 23)
         XXX:=(N1 < 23);
         //XXX1赋值:198日前的XXX
         XXX1:=REF(XXX,198);
         //KP赋值:((KX1 OR XX1) OR XXX1)
         KP:=((KX1 OR XX1) OR XXX1);
         //B1赋值:60日内AA的最高值
         B1:=HHV(AA,60);
         //B2赋值:60日内AA的最低值
         B2:=LLV(AA,60);
         //B3赋值:B1-B2
         B3:=B1-B2;
         //B4赋值:(AA-B2)/B3的2日指数移动平均*100
         B4:=EMA((AA-B2)/B3,2)*100;
         //KA1赋值:(((2日前的收盘价 / 2日前的开盘价) < 0.95) AND (2日前的收盘价 < 3日前的收盘价))
         KA1:=(((REF(CLOSE,2) / REF(OPEN,2)) < 0.95) AND (REF(CLOSE,2) < REF(CLOSE,3)));
         //KA2赋值:(((1日前的开盘价 < 2日前的收盘价) AND (1日前的收盘价 > 2日前的收盘价)) AND (1日前的收盘价 > ((2日前的收盘价 + 2日前的开盘价) / 2)))
         KA2:=(((REF(OPEN,1) < REF(CLOSE,2)) AND (REF(CLOSE,1) > REF(CLOSE,2))) AND (REF(CLOSE,1) > ((REF(CLOSE,2) + REF(OPEN,2)) / 2)));
         //KA3赋值:(收盘价 > 1日前的收盘价)
         KA3:=(CLOSE > REF(CLOSE,1));
         //KA4赋值:(13日内最低价的最低值 = 1日前的最低价)
         KA4:=(LLV(LOW,13) = REF(LOW,1));
         //KP2赋值:(统计3日中满足(((KA1ANDKA2)ANDKA3)ANDKA4)的天数 > 0)
         KP2:=(COUNT((((KA1 AND KA2) AND KA3) AND KA4),3) > 0);
         //KP3赋值:(-100*(14日内最高价的最高值-收盘价)/(14日内最高价的最高值-14日内最低价的最低值))上穿80
         KP3:=CROSS((-100*(HHV(HIGH,14)-CLOSE)/(HHV(HIGH,14)-LLV(LOW,14))),80);
         //KD3赋值:(N1 < 45)
         KD3:=(N1 < 45);
         //KD1赋值:(((((180日前的KD3 OR 179日前的KD3) OR 31日前的KD3) OR 29日前的KD3) OR 13日前的KD3) OR 11日前的KD3)
         KD1:=(((((REF(KD3,180) OR REF(KD3,179)) OR REF(KD3,31)) OR REF(KD3,29)) OR REF(KD3,13)) OR REF(KD3,11));
         //KP4赋值:((KP2 AND KP3) AND KD1)
         KP4:=((KP2 AND KP3) AND KD1);
         //DIFF赋值:收盘价的12日指数移动平均 - 收盘价的26日指数移动平均
         DIFF:=EMA(CLOSE,12) - EMA(CLOSE,26);
         //DEA赋值:DIFF的9日指数移动平均
         DEA:=EMA(DIFF,9);
         //KP5赋值:2*(DIFF-DEA)
         KP5:=2*(DIFF-DEA);
         //KD2赋值:((((180日前的KD3 OR 261日前的KD3) OR 98日前的KD3) OR 77日前的KD3) OR 1日前的KD3)
         KD2:=((((REF(KD3,180) OR REF(KD3,261)) OR REF(KD3,98)) OR REF(KD3,77)) OR REF(KD3,1));
         //KP6赋值:((KP2 AND KP5) AND KD2)
         KP6:=((KP2 AND KP5) AND KD2);
         //KP7赋值:(N1 < 34)
         KP7:=(N1 < 34);
         //KP8赋值:11日前的KP7
         KP8:=REF(KP7,11);
         //KP9赋值:(N1 < 45)
         KP9:=(N1 < 45);
         //KP10赋值:((83日前的KP9 AND KP8) AND KP2)
         KP10:=((REF(KP9,83) AND KP8) AND KP2);
         //KP11赋值:(N1 < 38)
         KP11:=(N1 < 38);
         //KP12赋值:((19日前的KP11 AND 32日前的KP11) AND KP2)
         KP12:=((REF(KP11,19) AND REF(KP11,32)) AND KP2);
         //KP13赋值:((28日前的KP9 AND 101日前的KP11) AND KP2)
         KP13:=((REF(KP9,28) AND REF(KP11,101)) AND KP2);
         //KP14赋值:(N1 < 43)
         KP14:=(N1 < 43);
         //KP15赋值:((28日前的KP14 AND 189日前的KP14) AND KP2)
         KP15:=((REF(KP14,28) AND REF(KP14,189)) AND KP2);
         //I赋值:((((((2日前的收盘价 / 2日前的开盘价) < 0.95) AND (1日前的开盘价 < 2日前的收盘价)) AND (((1日前的开盘价-1日前的收盘价)的绝对值 / 1日前的收盘价) < 0.03)) AND ((收盘价 / 开盘价) > 1.05)) AND (收盘价 > 2日前的收盘价))
         I:=((((((REF(CLOSE,2) / REF(OPEN,2)) < 0.95) AND (REF(OPEN,1) < REF(CLOSE,2))) AND ((ABS((REF(OPEN,1) - REF(CLOSE,1))) / REF(CLOSE,1)) < 0.03)) AND ((CLOSE / OPEN) > 1.05)) AND (CLOSE > REF(CLOSE,2)));
         //I1赋值:2*(DIFF-DEA)
         I1:=2*(DIFF-DEA);
         //T3赋值:(N1 < 40)
         T3:=(N1 < 40);
         //T4赋值:((34日前的T3 OR 28日前的T3) OR 57日前的T3)
         T4:=((REF(T3,34) OR REF(T3,28)) OR REF(T3,57));
         //C1赋值:240日内AA的最高值
         C1:=HHV(AA,240);
         //C2赋值:240日内AA的最低值
         C2:=LLV(AA,240);
         //C3赋值:C1-C2
         C3:=C1-C2;
         //C4赋值:(AA-C2)/C3的2日指数移动平均*100
         C4:=EMA((AA-C2)/C3,2)*100;
         //KP16赋值:((I AND I1) AND T4)
         KP16:=((I AND I1) AND T4);
         //P1赋值:13
         P1:=13;P2:=5;
         //P2赋值:5
         短期:A4,COLORGREEN;
         //输出短期:A4,画绿色
         中期:B4,COLORYELLOW;
         //输出中期:B4,画黄色
         长期:C4,COLORFF00FF;
         //输出长期:C4,COLORFF00FF
         SSRCJL:=EMA(VOL,13);
         //SSRCJL赋值:成交量(手)的13日指数移动平均
         SSRCJE:=EMA(AMOUNT,13);
         //SSRCJE赋值:成交额(元)的13日指数移动平均
         SSRCBJX:=((SSRCJE / SSRCJL) / 100);
         //SSRCBJX赋值:((SSRCJE / SSRCJL) / 100)
         SSRGL:=(((CLOSE - SSRCBJX) / SSRCBJX) * 100);
         //SSRGL赋值:(((收盘价 - SSRCBJX) / SSRCBJX) * 100)
         趋:=SSRGL<(0-18);
         //趋赋值:SSRGL<(0-18)
         势:=SSRGL<(0-14);
         //势赋值:SSRGL<(0-14)
         而:=SSRGL<(0-10);
         //而赋值:SSRGL<(0-10)
         为:=SSRGL<(0-6);
         //为赋值:SSRGL<(0-6)
         STICKLINE(短期>90 AND 中期>70,86,94,1,0),COLOR00FF00;
         //当满足条件短期>90AND中期>70时,在86和94位置之间画柱状线,宽度为1,0不为0则画空心柱.,COLOR00FF00
         STICKLINE(短期<5 AND 中期<30,0,8,1,0);
         //当满足条件短期<5AND中期<30时,在0和8位置之间画柱状线,宽度为1,0不为0则画空心柱.
         IF(FILTER(中期<REF(中期,1)AND 中期>90 AND BB<REF(BB,1),5),80,100),COLORBLUE,LINETHICK2;
         //如果中期<1日前的中期AND中期>90ANDBB<1日前的BB的5日过滤,返回80,否则返回100,画蓝色,线宽为2
         IF(FILTER(中期>REF(中期,1)AND 中期<5 AND C>REF(C,1),5),20,0),COLORRED,LINETHICK2;
         //如果中期>1日前的中期AND中期<5ANDC>1日前的收盘价的5日过滤,返回20,否则返回0,画红色,线宽为2
         D1:=中期>REF(中期,1) AND 短期>REF(短期,1)AND 长期>REF(长期,1)AND 长期<8 AND 中期<10 AND 短期<15;
         //D1赋值:中期>1日前的中期 AND 短期>1日前的短期AND 长期>1日前的长期AND 长期<8 AND 中期<10 AND 短期<15
         D2:=CROSS(短期,中期) AND 中期<8;
         //D2赋值:短期上穿中期 AND 中期<8
         D3:=CROSS(短期,长期) AND 长期<8 AND 中期<20;
         //D3赋值:短期上穿长期 AND 长期<8 AND 中期<20
         绝对底④:IF((SSRGL < (0 - 18)),23,0),LINETHICK3,COLORBLUE;
         //输出绝对底④:如果(SSRGL<(0-18)),返回23,否则返回0,线宽为3,画蓝色
         DRAWTEXT((FILTER((SSRGL < (0 - 18)),7)),25,'④'),COLORCYAN;
         //当满足条件((SSRGL<(0-18))的7日过滤)时,在25位置书写文字,画青色
         大底③:IF((SSRGL < (0 - 14)),84,0),LINETHICK3,COLORRED;
         //输出大底③:如果(SSRGL<(0-14)),返回84,否则返回0,线宽为3,画红色
         DRAWTEXT((FILTER((SSRGL < (0 - 14)),7)),86.5,'③'),COLORMAGENTA;
         //当满足条件((SSRGL<(0-14))的7日过滤)时,在86.5位置书写文字,画洋红色
         中底②:IF((SSRGL < (0 - 10)),60,0),LINETHICK3,COLORMAGENTA;
         //输出中底②:如果(SSRGL<(0-10)),返回60,否则返回0,线宽为3,画洋红色
         DRAWTEXT((FILTER((SSRGL < (0 - 10)),7)),64.5,'②'),COLORCYAN;
         //当满足条件((SSRGL<(0-10))的7日过滤)时,在64.5位置书写文字,画青色
         短底①:IF((SSRGL < (0 - 6)),44,0),LINETHICK3,COLORBROWN;
         //输出短底①:如果(SSRGL<(0-6)),返回44,否则返回0,线宽为3,画棕色
         DRAWTEXT((FILTER((SSRGL < (0 - 6)),7)),44,'①'),COLORLIGRAY;
         //当满足条件((SSRGL<(0-6))的7日过滤)时,在44位置书写文字,画淡灰色
         WWWGUPANGCOM:=(CLOSE-LLV(LOW,8))/(HHV(HIGH,8)-LLV(LOW,8))*100;
         //WWWGUPANGCOM赋值:(收盘价-8日内最低价的最低值)/(8日内最高价的最高值-8日内最低价的最低值)*100
         DRAWICON(FILTER((D1 OR D2 OR D3) AND DD<15 AND C>REF(C,1),5),5,1);
         //当满足条件(D1ORD2ORD3)ANDDD<15ANDC>1日前的收盘价的5日过滤时,在5位置画1号图标
         DRAWICON(FILTER((中期<REF(中期,1)AND 短期<REF(短期,1)AND 长期<REF(长期,
         //当满足条件(中期<1日前的中期AND短期<1日前的短期AND长期<1日前的长期AND中期>95AND短期>85)OR(长期>100AND中期>100AND短期>100)的5日过滤时,在95位置画2号图标
         1)AND 中期>95 AND 短期>85)OR (长期>100 AND 中期>100 AND 短期>100),5),95,2);
         //输出￥①:如果KPANDKP2ORKP4ORKP6ORKP10ORKP12ORKP13ORKP15ORKP16,返回60,否则返回0,线宽为3,画淡灰色
         ￥①:IF(KP AND KP2 OR KP4 OR KP6 OR KP10 OR KP12 OR KP13 OR KP15 OR KP16,60,0),LINETHICK3,COLORLIGRAY;
         //输出￥②:如果向导线上穿(0-3),返回21,否则返回0,线宽为3,画淡绿色
         ￥②:IF(CROSS(向导线,(0-3)),21,0),LINETHICK3,COLORLIGREEN;
         //输出￥③:如果W上穿-2ANDCROSS(股市黑客,普通投资者)ANDCROSS(KK1,DD2),返回60,否则返回0,线宽为3,画淡青色
         ￥③:IF(CROSS(W,-2) AND CROSS(股市黑客,普通投资者) AND CROSS(KK1,DD2),60,0),LINETHICK3,COLORLICYAN;
         //当满足条件KPANDKP2ORKP4ORKP6ORKP10ORKP12ORKP13ORKP15ORKP16时,在60位置书写文字,画黄色
         DRAWTEXT(KP AND KP2 OR KP4 OR KP6 OR KP10 OR KP12 OR KP13 OR KP15 OR KP16,60,'★★★'),COLORYELLOW;
         //当满足条件W上穿-2ANDCROSS(股市黑客,普通投资者)ANDCROSS(KK1,DD2)时,在60位置书写文字,画黄色
         DRAWTEXT(CROSS(W,-2) AND CROSS(股市黑客,普通投资者) AND CROSS(KK1,DD2),60,'强势追涨'),COLORYELLOW;
         //输出强弱分界:50,POINTDOT,画白色
         强弱分界:50,POINTDOT,COLORWHITE;
         //输出警戒区:90,线宽为1,画白色
         警戒区:90,LINETHICK1,COLORWHITE;
         //输出安全区:20,COLORFFFF00
         安全区:20,COLORFFFF00;
         //当满足条件向导线上穿(0-3)时,在13位置画5号图标
         DRAWICON(CROSS(向导线,(0-3)),13,5);
         //当满足条件235上穿向导线时,在80位置画6号图标
         DRAWICON(CROSS(235,向导线),80,6);
         */

    }
}

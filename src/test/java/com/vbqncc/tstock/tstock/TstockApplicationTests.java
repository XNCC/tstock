package com.vbqncc.tstock.tstock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vbqncc.tstock.tstock.entity.GoodStock;
import com.vbqncc.tstock.tstock.entity.WyStock;
import com.vbqncc.tstock.tstock.entity.WyStockRecord;
import com.vbqncc.tstock.tstock.mapper.WyStockMapper;
import com.vbqncc.tstock.tstock.mapper.WyStockRecordMapper;
import com.vbqncc.tstock.tstock.task.EveryDayTask;
import com.vbqncc.tstock.tstock.utils.HolidayUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class TstockApplicationTests {
//    @Autowired
//    WyStockRecordMapper wyStockRecordMapper;
//    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    private static SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    //拉去沪深b股数据
//    @Test
//    void contextLoads() throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        String today = sdfs.format(new Date());
//        String holidays = "2019-01-01,2019-01-04";
//        String dateEnd = HolidayUtil.getWorkDayStart(holidays, today, 1);
//        String dateStart = HolidayUtil.getWorkDayStart(holidays, today, 2);
//        //判断今天是不是节假日或者周末
//        if (!HolidayUtil.isWeekend(today) && hour >= 16) {
//            List<GoodStock> goodStock1 = wyStockRecordMapper.getStock(sdf.parse(dateEnd), sdf.parse(today));
//            System.out.println(goodStock1);
//        }
//    }

    @Autowired
    WyStockRecordMapper wyStockRecordMapper;

    /**
     * 拉取当日数据
     */
    @Test
    void name() {
        try {
//            startGetNew();
            startAve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取url
    String getUrl(Integer page) {
        String url = "http://quotes.money.163.com/hs/service/diyrank.php?" +
                "host=http%3A%2F%2Fquotes.money.163.com%2Fhs%2Fservice%2Fdiyrank.php" +
                "&page=" + page +
                "&query=STYPE%3AEQA" +
                "&fields=NO%2CSYMBOL%2CNAME%2CPRICE%2CPERCENT%2CUPDOWN%2CFIVE_MINUTE%2COPEN%2CYESTCLOSE%2CHIGH%2CLOW%2CVOLUME%2CTURNOVER%2CHS%2CLB%2CWB%2CZF%2CPE%2CMCAP%2CTCAP%2CMFSUM%2CMFRATIO.MFRATIO2%2CMFRATIO.MFRATIO10%2CSNAME%2CCODE%2CANNOUNMT%2CUVSNEWS" +
                "&sort=PERCENT" +
                "&order=desc" +
                "&count=24" +
                "&type=query";
        return url;
    }

    //发请求
    public static String sendGet(String url) throws ParseException, IOException {
        String result = "";
        BufferedReader in = null;
        String urlNameString = url;
        URL realUrl = new URL(urlNameString);
        URLConnection connection = realUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.connect();
        Map<String, List<String>> map = connection.getHeaderFields();
        in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return result;
    }

    //对象数据转化
    public static List<WyStockRecord> convert(String data) throws ParseException {
        List<WyStockRecord> list = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        String year = now.get(Calendar.YEAR) + "";
        String month = (now.get(Calendar.MONTH) + 1) + "";
        String day = now.get(Calendar.DAY_OF_MONTH) + "";
        String dateStr = year + "-" + month + "-" + day;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date time = sdf.parse(dateStr);

        if (data != null) {
            JSONObject doc = (JSONObject) JSONObject.parse(data);
            JSONArray array = (JSONArray) doc.get("list");
            if (array != null && array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = (JSONObject) array.get(i);
                    try {
                        String code = object.get("CODE").toString();     //获取日线的代码
                        String symbol = object.get("SYMBOL").toString(); //股票代码
                        String uname = object.get("NAME").toString();    //名称
                        BigDecimal closingPrice = new BigDecimal(object.get("PRICE").toString());
                        BigDecimal topPrice = new BigDecimal(object.get("HIGH").toString());
                        BigDecimal lowPrice = new BigDecimal(object.get("LOW").toString());
                        BigDecimal startPrice = new BigDecimal(object.get("OPEN").toString());
                        BigDecimal changeAmount = new BigDecimal(object.get("UPDOWN").toString());
                        BigDecimal changePercent = new BigDecimal(object.get("PERCENT").toString());
                        BigDecimal turnoverRate;
                        if (object.get("HS") != null) {
                            turnoverRate = new BigDecimal(object.get("HS").toString());
                        } else {
                            turnoverRate = null;
                        }
                        BigDecimal vol = new BigDecimal(object.get("VOLUME").toString());
                        BigDecimal vot = new BigDecimal(object.get("TURNOVER").toString());
                        BigDecimal mktCap;
                        if (object.get("MCAP") != null) {
                            mktCap = new BigDecimal(object.get("MCAP").toString());
                        } else {
                            mktCap = null;
                        }
                        BigDecimal cmktCap = new BigDecimal(object.get("TCAP").toString());
                        Integer market = 1;//沪深A股
                        WyStockRecord wyStock = new WyStockRecord
                                (time, symbol, closingPrice, topPrice, lowPrice, startPrice, changeAmount
                                        , changePercent, turnoverRate, vol, vot, mktCap, cmktCap);
                        list.add(wyStock);
                    } catch (Exception e) {
                        System.out.println("》》》" + object);
                        e.printStackTrace();
                    }

                }
            }
        }
        return list;
    }

    //开始获取
    public void startGetNew() {
        int page = -1;
        while (true) {
            page++;
            String url = getUrl(page);
            String data = null;
            try {
                data = sendGet(url);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    data = sendGet(url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            //判断是否跳出循环
            if (data != null) {
                JSONObject doc = (JSONObject) JSONObject.parse(data);
                JSONArray array = (JSONArray) doc.get("list");
                if (array == null || array.size() == 0) {
                    break;
                }
            } else {
                break;
            }
            //数据对象相互转换
            List<WyStockRecord> convert = null;
            try {
                convert = convert(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (convert != null && convert.size() > 0) {
                try {
                    int i = wyStockRecordMapper.insertWyStockRecord(convert);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 更新十日均值,并计算ene下轨
     */
    public void startAve() throws ParseException {
        //获取当前日期
        Calendar now = Calendar.getInstance();
        String year = now.get(Calendar.YEAR) + "";
        String month = (now.get(Calendar.MONTH) + 1) + "";
        String day = now.get(Calendar.DAY_OF_MONTH) + "";
        String dateStr = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowTime = sdf.parse("2021-12-17");
        List<WyStockRecord> wyStockRecords = wyStockRecordMapper.selectTenAve(nowTime);
        for (int i = 0; i < wyStockRecords.size(); i++) {
            String code = wyStockRecords.get(i).getCode();
            Date time = wyStockRecords.get(i).getTime();
            BigDecimal lowPrice = wyStockRecords.get(i).getLowPrice();
            BigDecimal upper = null;
            BigDecimal low = null;
            BigDecimal ave = null;
            BigDecimal mid = null;
            Integer eneReach = null;
            List<WyStockRecord> tenRecords = wyStockRecordMapper.selectTenRecord(time, code);
            if (tenRecords != null && tenRecords.size() == 10) {
                //计算均值
                BigDecimal sum = new BigDecimal(0);
                for (int j = 0; j < tenRecords.size(); j++) {
                    WyStockRecord everyStock = tenRecords.get(j);
                    if (everyStock.getClosingPrice() != null) {
                        sum = sum.add(everyStock.getClosingPrice());
                    } else {
                        break;
                    }
                }
                //ave
                ave = sum.divide(new BigDecimal(10), 2, BigDecimal.ROUND_HALF_DOWN);

                //ene
                //上轨计算
                upper = ave.multiply(new BigDecimal(1.11)).setScale(2, BigDecimal.ROUND_HALF_UP);
                //下轨计算
                low = ave.multiply(new BigDecimal(0.91)).setScale(2, BigDecimal.ROUND_HALF_UP);
                //中轨计算
                BigDecimal eneSum = upper.add(low);
                mid = eneSum.divide(new BigDecimal(2)).setScale(2, BigDecimal.ROUND_HALF_UP);

                //enereach
                eneReach = null;
                if (lowPrice != null) {
                    BigDecimal divide = lowPrice.divide(low, 2, BigDecimal.ROUND_HALF_UP);
                    //divide<1 并且小于0.9
                    if (divide.compareTo(new BigDecimal(1.02)) == -1 && divide.compareTo(new BigDecimal(0.8)) == 1) {
                        eneReach = 1;
                    }
                }
            }
            wyStockRecordMapper.updateAve(time, code, ave, upper, mid, low, eneReach);
        }
    }
    /////////////////////////////////////////////////////////


    @Test
    void name1() {
        System.out.println(System.currentTimeMillis());
    }



    /**
     * 从中国股票网获取，股票的历史数据
     *
     */
    @Autowired
    WyStockMapper wyStockMapper;
    @Test
    void test() throws ParseException {
        QueryWrapper<WyStock> queryWrapper = new QueryWrapper();
        List<WyStock> stocks = wyStockMapper.selectList(queryWrapper);
        for (int i = 0; i < stocks.size(); i++) {
            if(!stocks.get(i).getSymbol().startsWith("6")){
                String url = getUrl(stocks.get(i).getSymbol());
                String s = sendGet(wyStockMapper, wyStockRecordMapper, url, stocks.get(i).getCode());
                save1(wyStockMapper,wyStockRecordMapper,s,stocks.get(i).getSymbol());
            }

        }
    }

    //    url生成
    public static String getUrl(String code) throws ParseException {
        String url = "https://push2his.eastmoney.com/api/qt/stock/kline/get" +
                "?fields1=f1%2Cf2%2Cf3%2Cf4%2Cf5%2Cf6" +
                "&fields2=f51%2Cf52%2Cf53%2Cf54%2Cf55%2Cf56%2Cf57%2Cf58%2Cf59%2Cf60%2Cf61" +
                "&klt=101" +
                "&fqt=1" +
                "&secid=0." +
                code +
                "&beg=0" +
                "&end="+System.currentTimeMillis();
        return url;
    }

    //发请求
    public static String sendGet(WyStockMapper stockMapper, WyStockRecordMapper everyStockMapper, String url, String code) throws ParseException {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (UnknownHostException e) {
            String url2 = getUrl(code);
            String s = sendGet(stockMapper, everyStockMapper, url2, code);
            save1(stockMapper, everyStockMapper, s,code);
            System.out.println("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        catch (MalformedURLException e) {
            System.out.println("其他错误异常！" + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("发送IO异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    //转换数据
    public static WyStockRecord convert1(List list) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        WyStockRecord stock = new WyStockRecord();
        if (list.get(0)!=null&&!list.get(0).equals("")) {
            Date parse = sdf.parse((Integer) list.get(0)+""+(Integer) list.get(1)+""+(Integer) list.get(2));
            Date parse1 = sdf.parse("2021-11-15");
            Date parse2 = sdf.parse("2021-12-16");
            if(parse.getTime()>parse1.getTime()&&parse.getTime()<=parse2.getTime()){
                stock.setTime(parse);
            }else{
                return null;
            }

        }
        //2021-12-17,
        // 17.70,
        // 17.57,
        // 17.75,
        // 17.57,
        // 602370,
        // 1062875488.00,
        // 1.02,
        // -0.85,
        // -0.15,
        // 0.31
        if (list.get(3)!=null&&!list.get(3).equals("-")&&!list.get(3).equals("")) {
            stock.setStartPrice((BigDecimal) list.get(3));
        }
        if (list.get(4)!=null&&!list.get(4).equals("-")&&!list.get(4).equals("")) {
            stock.setClosingPrice((BigDecimal) list.get(4));
        }
        if (list.get(5)!=null&&!list.get(5).equals("-")&&!list.get(5).equals("")) {
            stock.setTopPrice((BigDecimal) list.get(5));
        }
        if (list.get(6)!=null&&!list.get(6).equals("-")&&!list.get(6).equals("")) {
            stock.setLowPrice((BigDecimal) list.get(6));
        }
        if (list.get(7)!=null&&!list.get(7).equals("-")&&!list.get(7).equals("")) {
            stock.setChangePercent(new BigDecimal(list.get(7).toString()) );
        }
        if (list.get(8)!=null&&!list.get(8).equals("-")&&!list.get(8).equals("")) {
            stock.setChangeAmount((BigDecimal) list.get(8));
        }
//        if (list.get(9)!=null&&!list.get(9).equals("-")&&!list.get(9).equals("")) {
//            stock.setTurnover((BigDecimal)list.get(9));
//        }
//        if (list.get(10)!=null&&!list.get(10).equals("-")&&!list.get(10).equals("")) {
//            stock.setTurnoverMoney((BigDecimal) list.get(10));
//        }
//        if (list.get(11)!=null&&!list.get(11).equals("-")&&!list.get(11).equals("")) {
//            stock.setAmplitude((BigDecimal) list.get(11));
//        }
        if (list.get(12)!=null&&!list.get(12).equals("-")&&!list.get(12).equals("")) {
            stock.setTurnoverRate((BigDecimal)list.get(12));
        }
        return stock;
    }
    //保存
    //保存数据
    public static int save1(WyStockMapper stockMapper, WyStockRecordMapper everyStockMapper, String s,String code) {
        JSONObject doc = (JSONObject) JSONObject.parse(s);
        JSONObject data = (JSONObject) doc.get("data");
        if (data != null) {
            JSONArray klines = (JSONArray) data.get("klines");
            for (int i = 0; i < klines.size(); i++) {
                List list = JSONObject.parseObject("[" + (String) klines.get(i) + "]", List.class);
                try {
                    WyStockRecord convert = convert1(list);
                    if(convert==null){
                        continue;
                    }
                    convert.setCode(code);
                    everyStockMapper.insert(convert);
                } catch (Exception e) {
                    System.out.println("》》》" + e.getMessage());
                }
            }
        } else {
            System.out.println("》》》结束");
            return 1;
        }
        return 0;
    }








}

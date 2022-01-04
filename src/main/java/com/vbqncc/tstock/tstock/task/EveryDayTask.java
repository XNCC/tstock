package com.vbqncc.tstock.tstock.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vbqncc.tstock.tstock.entity.WyStockRecord;
import com.vbqncc.tstock.tstock.mapper.WyStockRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 每日定时任务
 */
@Component
public class EveryDayTask {
    @Autowired
    WyStockRecordMapper wyStockRecordMapper;

    @Scheduled(cron = "1 1 16 * * ?")
    public void task() throws ParseException {
        try {
            startGetNew();
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
        Date nowTime = sdf.parse(dateStr);
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


}

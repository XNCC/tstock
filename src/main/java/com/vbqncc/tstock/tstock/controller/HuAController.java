package com.vbqncc.tstock.tstock.controller;

import com.vbqncc.tstock.tstock.entity.Count;
import com.vbqncc.tstock.tstock.entity.GoodStock;
import com.vbqncc.tstock.tstock.entity.WyStockRecord;
import com.vbqncc.tstock.tstock.mapper.CountMapper;
import com.vbqncc.tstock.tstock.mapper.WyStockRecordMapper;
import com.vbqncc.tstock.tstock.utils.HolidayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class HuAController {
    @Autowired
    WyStockRecordMapper wyStockRecordMapper;
    @Autowired
    CountMapper countMapper;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //沪A
    @GetMapping("/hua")
    public String indexs(Model model, HttpServletRequest request) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String today = sdfs.format(new Date());
        String holidays = "2019-01-01,2019-01-04";
        String workDayStart = HolidayUtil.getWorkDayStart(holidays, today, 1);
        String dateStart = HolidayUtil.getWorkDayStart(holidays, today, 2);
        String dateThreeStart = HolidayUtil.getWorkDayStart(holidays, today, 3);
        String datefiveStart = HolidayUtil.getWorkDayStart(holidays, today, 4);
        Date parse = sdf.parse(workDayStart);
        //判断今天是不是节假日或者周末
        if (!HolidayUtil.isWeekend(today) && hour >= 16) {
            List<GoodStock> goodStock1 = wyStockRecordMapper.getGoodStock1(1, sdf.parse(today));
            List<GoodStock> goodStock2 = wyStockRecordMapper.getGoodStock2(1, sdf.parse(today));
            List<GoodStock> goodStock3 = wyStockRecordMapper.getStock(sdf.parse(workDayStart), sdf.parse(today));
            List<GoodStock> goodStock4 = wyStockRecordMapper.getStock(sdf.parse(dateStart), sdf.parse(today));
            List<GoodStock> goodStock5 = wyStockRecordMapper.getStock(sdf.parse(dateThreeStart), sdf.parse(today));

            model.addAttribute("data1", checkData(goodStock1));
            model.addAttribute("data2", checkData(goodStock2));
            model.addAttribute("data3", checkData(goodStock3));
            model.addAttribute("data4", checkData(goodStock4));
            model.addAttribute("data5", checkData(goodStock5));
            model.addAttribute("time", new Date());
        } else {
            List<GoodStock> goodStock1 = wyStockRecordMapper.getGoodStock1(1, parse);
            List<GoodStock> goodStock2 = wyStockRecordMapper.getGoodStock2(1, parse);
            List<GoodStock> goodStock3 = wyStockRecordMapper.getStock(sdf.parse(dateStart), sdf.parse(workDayStart));
            List<GoodStock> goodStock4 = wyStockRecordMapper.getStock(sdf.parse(dateThreeStart), sdf.parse(workDayStart));
            List<GoodStock> goodStock5 = wyStockRecordMapper.getStock(sdf.parse(datefiveStart), sdf.parse(workDayStart));
            model.addAttribute("data1", checkData(goodStock1));
            model.addAttribute("data2", checkData(goodStock2));
            model.addAttribute("data3", checkData(goodStock3));
            model.addAttribute("data4", checkData(goodStock4));
            model.addAttribute("data5", checkData(goodStock5));
            model.addAttribute("time", parse);
        }
        return "index/huia1";
    }

    //获取股票详细数据
    @GetMapping("/getDetail")
    @ResponseBody
    @CrossOrigin
    public WyStockRecord getDetail(String code) throws Exception {
        //个股记录访问次数
        Count count = countMapper.selectById(2);
        Integer newCount = count.getCount();
        newCount++;
        count.setCount(newCount);
        countMapper.updateById(count);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String today = sdfs.format(new Date());
        String holidays = "2019-01-01,2019-01-04";
        String workDayStart = HolidayUtil.getWorkDayStart(holidays, today, 1);
        Date parse = sdf.parse(workDayStart);
        if (!HolidayUtil.isWeekend(today) && hour > 16) {
            WyStockRecord wyStockRecord = wyStockRecordMapper.getDetail(code, sdf.parse(today));
            return wyStockRecord;
        } else {
            WyStockRecord wyStockRecord = wyStockRecordMapper.getDetail(code, parse);
            return wyStockRecord;
        }
    }


    List<GoodStock> checkData(List<GoodStock> data) {
        if (ObjectUtils.isEmpty(data)) {
            return new ArrayList<GoodStock>();
        } else {
            return data;
        }
    }
}

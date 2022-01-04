package com.vbqncc.tstock.tstock.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vbqncc.tstock.tstock.entity.Count;
import com.vbqncc.tstock.tstock.entity.GoodStock;
import com.vbqncc.tstock.tstock.entity.WyStock;
import com.vbqncc.tstock.tstock.entity.WyStockRecord;
import com.vbqncc.tstock.tstock.mapper.CountMapper;
import com.vbqncc.tstock.tstock.mapper.WyStockMapper;
import com.vbqncc.tstock.tstock.mapper.WyStockRecordMapper;
import com.vbqncc.tstock.tstock.utils.HolidayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@CrossOrigin
public class IndexController {
    @Autowired
    CountMapper countMapper;
    @Autowired
    WyStockRecordMapper wyStockRecordMapper;

    @Autowired
    WyStockMapper wyStockMapper;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) throws Exception {
        //记录访问次数
        Count count = countMapper.selectById(1);
        Integer newCount = count.getCount();
        newCount++;
        count.setCount(newCount);
        countMapper.updateById(count);
        return "index/index";
    }

    @GetMapping("/search")
    @ResponseBody
    public WyStock getWyStockRecord(String code) {
        QueryWrapper<WyStock> queryWrapper = new QueryWrapper();
        if (isContainChinese(code)) {
            queryWrapper.eq("name", code);
        } else {
            queryWrapper.eq("symbol", code);
        }
        WyStock wyStock = wyStockMapper.selectOne(queryWrapper);
        return wyStock;
    }

    //判断是否包含中文
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}

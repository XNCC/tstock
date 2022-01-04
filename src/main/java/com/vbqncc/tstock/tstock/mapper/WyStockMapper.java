package com.vbqncc.tstock.tstock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbqncc.tstock.tstock.entity.WyStock;

import java.util.List;

public interface WyStockMapper  extends BaseMapper<WyStock> {

    int insertWyStock1(List<WyStock> list);

    List<WyStock> selectAll();
}

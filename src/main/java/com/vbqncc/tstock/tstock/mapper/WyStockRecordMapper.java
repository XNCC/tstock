package com.vbqncc.tstock.tstock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vbqncc.tstock.tstock.entity.GoodStock;
import com.vbqncc.tstock.tstock.entity.WyStockRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface WyStockRecordMapper extends BaseMapper<WyStockRecord> {

    int insertWyStockRecord(List<WyStockRecord> list);

    List<WyStockRecord> selectTenAve(Date time);

    List<WyStockRecord> selectTenRecord(Date time, String code);

    int updateAve(Date time, String code, BigDecimal tenAve, BigDecimal eneUpper, BigDecimal eneMid, BigDecimal eneLower, Integer eneReach);



    List<GoodStock> getGoodStock1(Integer market, Date time);

    List<GoodStock> getGoodStock2(Integer market, Date time);

    WyStockRecord getDetail(String code, Date time);

    List<GoodStock> getStock(Date dateStart, Date dateEnd);


}

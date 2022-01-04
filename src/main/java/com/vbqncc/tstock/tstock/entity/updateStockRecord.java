package com.vbqncc.tstock.tstock.entity;

import java.math.BigDecimal;
import java.util.Date;

public class updateStockRecord {
    Date time;   //何日股票记录  0
    String code; //股票代码     1
    BigDecimal tenAve;  //十日均线
    BigDecimal eneUpper; //ene上轨
    BigDecimal eneMid;   //ene中轨
    BigDecimal eneLower;  //ene下轨
    Integer eneReach;  //股票最低点是否达到ene下轨

    public updateStockRecord() {
    }

    public updateStockRecord(Date time, String code, BigDecimal tenAve, BigDecimal eneUpper, BigDecimal eneMid, BigDecimal eneLower, Integer eneReach) {
        this.time = time;
        this.code = code;
        this.tenAve = tenAve;
        this.eneUpper = eneUpper;
        this.eneMid = eneMid;
        this.eneLower = eneLower;
        this.eneReach = eneReach;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTenAve() {
        return tenAve;
    }

    public void setTenAve(BigDecimal tenAve) {
        this.tenAve = tenAve;
    }

    public BigDecimal getEneUpper() {
        return eneUpper;
    }

    public void setEneUpper(BigDecimal eneUpper) {
        this.eneUpper = eneUpper;
    }

    public BigDecimal getEneMid() {
        return eneMid;
    }

    public void setEneMid(BigDecimal eneMid) {
        this.eneMid = eneMid;
    }

    public BigDecimal getEneLower() {
        return eneLower;
    }

    public void setEneLower(BigDecimal eneLower) {
        this.eneLower = eneLower;
    }

    public Integer getEneReach() {
        return eneReach;
    }

    public void setEneReach(Integer eneReach) {
        this.eneReach = eneReach;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

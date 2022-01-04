package com.vbqncc.tstock.tstock.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EveryGoodStock {
    Date time;
    BigDecimal topPrice; //最高价  4
    BigDecimal lowPrice; //最低价 5
    BigDecimal startPrice; //开盘价 6
    BigDecimal closingPrice; //收盘价   3
    BigDecimal vol; //成交量 11
    BigDecimal eneUpper; //ene上轨
    BigDecimal eneMid;   //ene中轨
    BigDecimal eneLower;  //ene下轨

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(BigDecimal topPrice) {
        this.topPrice = topPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public BigDecimal getVol() {
        return vol;
    }

    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

}

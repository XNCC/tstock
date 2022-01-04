package com.vbqncc.tstock.tstock.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

@TableName("wy_stock_record")
public class WyStockRecord {

    Date time;   //何日股票记录  0
    String code; //股票代码     1
    BigDecimal closingPrice; //收盘价   3
    BigDecimal topPrice; //最高价  4
    BigDecimal lowPrice; //最低价 5
    BigDecimal startPrice; //开盘价 6
    BigDecimal changeAmount; //涨跌额 8
    BigDecimal changePercent; //涨跌幅 9
    BigDecimal turnoverRate; //换手率 10
    BigDecimal vol; //成交量 11
    BigDecimal vot; //成交金额 12
    BigDecimal mktCap; //总市值 13
    BigDecimal cmktCap; //流通市值 14
    BigDecimal tenAve;  //十日均线
    BigDecimal fiveAve; //五日均线
    BigDecimal tweAve;  //20日均线
    BigDecimal eneUpper; //ene上轨
    BigDecimal eneMid;   //ene中轨
    BigDecimal eneLower;  //ene下轨
    Integer eneReach;  //股票最低点是否达到ene下轨

    public WyStockRecord(Date time, String code, BigDecimal closingPrice, BigDecimal topPrice, BigDecimal lowPrice, BigDecimal startPrice, BigDecimal changeAmount, BigDecimal changePercent,
                         BigDecimal turnoverRate, BigDecimal vol, BigDecimal vot, BigDecimal mktCap, BigDecimal cmktCap) {
        this.time = time;
        this.code = code;
        this.closingPrice = closingPrice;
        this.topPrice = topPrice;
        this.lowPrice = lowPrice;
        this.startPrice = startPrice;
        this.changeAmount = changeAmount;
        this.changePercent = changePercent;
        this.turnoverRate = turnoverRate;
        this.vol = vol;
        this.vot = vot;
        this.mktCap = mktCap;
        this.cmktCap = cmktCap;
    }

    public WyStockRecord() {
    }


    public Integer getEneReach() {
        return eneReach;
    }

    public void setEneReach(Integer eneReach) {
        this.eneReach = eneReach;
    }

    public BigDecimal getTenAve() {
        return tenAve;
    }

    public void setTenAve(BigDecimal tenAve) {
        this.tenAve = tenAve;
    }

    public BigDecimal getFiveAve() {
        return fiveAve;
    }

    public void setFiveAve(BigDecimal fiveAve) {
        this.fiveAve = fiveAve;
    }

    public BigDecimal getTweAve() {
        return tweAve;
    }

    public void setTweAve(BigDecimal tweAve) {
        this.tweAve = tweAve;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
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

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public BigDecimal getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(BigDecimal turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public BigDecimal getVol() {
        return vol;
    }

    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

    public BigDecimal getVot() {
        return vot;
    }

    public void setVot(BigDecimal vot) {
        this.vot = vot;
    }

    public BigDecimal getMktCap() {
        return mktCap;
    }

    public void setMktCap(BigDecimal mktCap) {
        this.mktCap = mktCap;
    }

    public BigDecimal getCmktCap() {
        return cmktCap;
    }

    public void setCmktCap(BigDecimal cmktCap) {
        this.cmktCap = cmktCap;
    }
}

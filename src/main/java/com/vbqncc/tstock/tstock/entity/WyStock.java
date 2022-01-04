package com.vbqncc.tstock.tstock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("wy_stock")
public class WyStock {
    @TableId(type = IdType.INPUT)
    private String code;
    private String name;
    private Integer market;
    private Integer concept;
    private Integer industry;
    private Integer area;
    private String symbol;

    public WyStock() {
    }

    public WyStock(String code, String name, Integer market, Integer concept, Integer industry, Integer area, String symbol) {
        this.code = code;
        this.name = name;
        this.market = market;
        this.concept = concept;
        this.industry = industry;
        this.area = area;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getConcept() {
        return concept;
    }

    public void setConcept(Integer concept) {
        this.concept = concept;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }


    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }
}

package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * Contract currency  <br/>
 * Created by Tony Tian on 2018/2/26 21:33. <br/>
 */
public class Currencies {
    /**
     * symbol
     */
    private Integer id;
    /**
     * currency name
     */
    private String name;
    /**
     * Minimum transaction quantity
     */
    private Double min_size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMin_size() {
        return min_size;
    }

    public void setMin_size(Double min_size) {
        this.min_size = min_size;
    }
}

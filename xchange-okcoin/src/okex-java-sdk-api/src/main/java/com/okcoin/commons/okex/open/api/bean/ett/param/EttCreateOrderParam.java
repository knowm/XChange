package com.okcoin.commons.okex.open.api.bean.ett.param;

import java.math.BigDecimal;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttCreateOrderParam {
    private String ett;
    private Integer type;
    private BigDecimal amount;
    private BigDecimal size;
    private String clientOid;

    public String getEtt() {
        return ett;
    }

    public void setEtt(String ett) {
        this.ett = ett;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getClientOid() {
        return clientOid;
    }

    public void setClientOid(String clientOid) {
        this.clientOid = clientOid;
    }
}

package com.xeiam.xchange.independentreserve.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Author: Kamil Zbikowski
 * Date: 4/9/15
 */
public class IndependentReserveOrderBook {
    private final Long createdTimestampUtc;
    private final List<OrderBookOrder> buyOrders;
    private final List<OrderBookOrder> sellOrders;

    private final String primaryCurrencyCode;
    private final String secondaryCurrencyCode;

    public IndependentReserveOrderBook(@JsonProperty("BuyOrders") List<OrderBookOrder> buyOrders,
                                       @JsonProperty("SellOrders") List<OrderBookOrder> sellOrders,
                                       @JsonProperty("PrimaryCurrencyCode") String primaryCurrencyCode,
                                       @JsonProperty("SecondaryCurrencyCode") String secondaryCurrencyCode,
                                       @JsonProperty("CreatedTimestampUtc") String createdTimestampUtc) {
        this.buyOrders = buyOrders;
        this.createdTimestampUtc = new DateTime(createdTimestampUtc).getMillis();
        this.sellOrders = sellOrders;
        this.primaryCurrencyCode = primaryCurrencyCode;
        this.secondaryCurrencyCode = secondaryCurrencyCode;
    }

    public List<OrderBookOrder> getBuyOrders() {
        return buyOrders;
    }

    public Long getCreatedTimestampUtc() {
        return createdTimestampUtc;
    }

    public String getPrimaryCurrencyCode() {
        return primaryCurrencyCode;
    }

    public String getSecondaryCurrencyCode() {
        return secondaryCurrencyCode;
    }

    public List<OrderBookOrder> getSellOrders() {
        return sellOrders;
    }
}

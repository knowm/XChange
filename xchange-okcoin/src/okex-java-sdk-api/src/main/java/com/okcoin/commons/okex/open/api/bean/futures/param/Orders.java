package com.okcoin.commons.okex.open.api.bean.futures.param;

import java.util.List;

/**
 * New Order
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 15:38
 */
public class Orders {
    /**
     * The id of the futures, eg: BTC-USD-180629
     */
    protected String instrument_id;


    /**
     * lever, default 10.
     */
    protected Double leverage;

    /**
     * batch new order sub element
     */
    List<OrdersItem> orders_data;

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setinstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public Double getLeverage() {
        return leverage;
    }

    public void setLeverage(Double leverage) {
        this.leverage = leverage;
    }

    public List<OrdersItem> getOrders_data() {
        return orders_data;
    }

    public void setOrders_data(List<OrdersItem> orders_data) {
        this.orders_data = orders_data;
    }
}

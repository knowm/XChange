package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelOrders;
import com.okcoin.commons.okex.open.api.bean.futures.param.Order;
import com.okcoin.commons.okex.open.api.bean.futures.param.Orders;
import com.okcoin.commons.okex.open.api.bean.futures.param.OrdersItem;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

/**
 * Futures trade api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 18:52
 */
public class FuturesTradeAPIServiceImpl implements FuturesTradeAPIService {

    private APIClient client;
    private FuturesTradeAPI api;

    public FuturesTradeAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(FuturesTradeAPI.class);
    }

    @Override
    public JSONObject getPositions() {

        return this.client.executeSync(this.api.getPositions());
    }

    @Override
    public JSONObject getInstrumentPosition(String instrumentId) {
        return this.client.executeSync(this.api.getInstrumentPosition(instrumentId));
    }

    @Override
    public JSONObject getAccounts() {
        return this.client.executeSync(this.api.getAccounts());
    }

    @Override
    public JSONObject getAccountsByCurrency(String currency) {
        return this.client.executeSync(this.api.getAccountsByCurrency(currency));
    }

    @Override
    public JSONArray getAccountsLedgerByCurrency(String currency) {
        return this.client.executeSync(this.api.getAccountsLedgerByCurrency(currency));
    }

    @Override
    public JSONObject getAccountsHoldsByInstrumentId(String instrumentId) {
        return this.client.executeSync(this.api.getAccountsHoldsByInstrumentId(instrumentId));
    }

    @Override
    public OrderResult order(Order order) {
        System.out.println(JsonUtils.convertObject(order, Order.class));
        return this.client.executeSync(this.api.order(JsonUtils.convertObject(order, Order.class)));
    }

    @Override
    public JSONObject orders(Orders orders) {
        JSONObject params = new JSONObject();
        params.put("instrument_id", orders.getInstrument_id());
        params.put("leverage", orders.getLeverage());
        params.put("orders_data", JsonUtils.convertList(orders.getOrders_data(), OrdersItem.class));
        System.out.println(params.toString());
        return this.client.executeSync(this.api.orders(params));
    }

    @Override
    public JSONObject cancelOrder(String instrumentId, long orderId) {
        return this.client.executeSync(this.api.cancelOrder(instrumentId, String.valueOf(orderId)));
    }

    @Override
    public JSONObject cancelOrders(String instrumentId, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelOrders(instrumentId, JsonUtils.convertObject(cancelOrders, CancelOrders.class)));
    }

    @Override
    public JSONObject getOrders(String instrument_id, int status, int from, int to, int limit) {
        return this.client.executeSync(this.api.getOrders(instrument_id, status, from, to, limit));
    }

    @Override
    public JSONObject getOrder(String instrumentId, long orderId) {
        return this.client.executeSync(this.api.getOrder(instrumentId, String.valueOf(orderId)));
    }

    @Override
    public JSONArray getFills(String instrumentId, long orderId, int from, int to, int limit) {
        return this.client.executeSync(this.api.getFills(instrumentId, String.valueOf(orderId), from, to, limit));
    }

    @Override
    public JSONObject getInstrumentLeverRate(String instrumentId) {
        return this.client.executeSync(this.api.getLeverRate(instrumentId));
    }


    @Override
    public JSONObject changeLevelRate(String currency, String instrumentId, String direction, int leverage) {
        return this.client.executeSync(this.api.changeLevelRate(currency, instrumentId, direction, leverage));
    }

    @Override
    public JSONObject changequancangLevelRate(String currency, int leverage) {
        return this.client.executeSync(this.api.changequancanLevelRate(currency, leverage));
    }
}

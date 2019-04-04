package com.okcoin.commons.okex.open.api.service.ett.impl;

import com.okcoin.commons.okex.open.api.bean.ett.param.EttCreateOrderParam;
import com.okcoin.commons.okex.open.api.bean.ett.result.CursorPager;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCancelOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCreateOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttOrder;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.ett.EttOrderAPIService;

import java.math.BigDecimal;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttOrderAPIServiceImpl implements EttOrderAPIService {

    private final APIClient client;
    private final EttOrderAPI api;

    public EttOrderAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = this.client.createService(EttOrderAPI.class);
    }

    @Override
    public EttCreateOrderResult createOrder(String ett, Integer type, BigDecimal amount, BigDecimal size, String clientOid) {
        EttCreateOrderParam param = new EttCreateOrderParam();
        param.setEtt(ett);
        param.setType(type);
        param.setAmount(amount);
        param.setSize(size);
        param.setClientOid(clientOid);
        return this.client.executeSync(this.api.createOrder(param));
    }

    @Override
    public EttCancelOrderResult cancelOrder(String orderId) {
        return this.client.executeSync(this.api.cancelOrder(orderId));
    }

    @Override
    public CursorPager<EttOrder> getOrder(String ett, Integer type, Integer status, String before, String after, int limit) {
        return this.client.executeSyncCursorPager(this.api.getOrder(ett, type, status, before, after, limit));
    }

    @Override
    public EttOrder getOrder(String orderId) {
        return this.client.executeSync(this.api.getOrder(orderId));
    }
}

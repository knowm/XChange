package com.okcoin.commons.okex.open.api.test.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.Fills;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderInfo;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderResult;
import com.okcoin.commons.okex.open.api.service.spot.MarginOrderAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.MarginOrderAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarginOrderAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(MarginOrderAPITest.class);

    private MarginOrderAPIService marginOrderAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.marginOrderAPIService = new MarginOrderAPIServiceImpl(this.config);
    }

    /**
     * 单个下单
     */
    @Test
    public void addOrder() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("20180910-1");
        order.setInstrument_id("btc-usdt");
        order.setPrice("10000");
        order.setType("limit");
        order.setSide("sell");
        order.setSize("0.1");
        order.setMargin_trading((byte) 2);

        final OrderResult orderResult = this.marginOrderAPIService.addOrder(order);
        this.toResultString(MarginOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 批量下单
     */
    @Test
    public void batchAddOrder() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("20180728-01");
        order.setInstrument_id("btc-usdt");
        order.setPrice("1");
        order.setType("limit");
        order.setSide("sell");
        order.setSize("0.1");
        order.setMargin_trading((byte) 2);

        final List<PlaceOrderParam> list = new ArrayList<>();
        list.add(order);

        final PlaceOrderParam order1 = new PlaceOrderParam();
        order1.setClient_oid("20180728-02");
        order1.setInstrument_id("ltc-usdt");
        order1.setPrice("1");
        order1.setType("limit");
        order1.setSide("sell");
        order1.setSize("0.1");
        list.add(order1);

        final Map<String, List<OrderResult>> orderResult = this.marginOrderAPIService.addOrders(list);
        this.toResultString(MarginOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 指定订单id撤单 delete协议 暂不使用
     */
    @Test
    public void cancleOrderByProductIdAndOrderId() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc_usd");
        order.setClient_oid("20181009-01");
        final OrderResult orderResult = this.marginOrderAPIService.cancleOrderByOrderId(order, "23850");
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 指定订单id撤单 post协议
     */
    @Test
    public void cancleOrderByProductIdAndOrderId_post() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc_usdt");
        order.setClient_oid("20181009-01");
        final OrderResult orderResult = this.marginOrderAPIService.cancleOrderByOrderId_post(order, "23850");
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 批量撤单 delete协议 暂不使用
     */
    @Test
    public void batchCancleOrders() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();

        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("btc-usdt");
        final List<Long> order_ids = new ArrayList<>();
        order_ids.add(23464L);
        order_ids.add(23465L);
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

        final OrderParamDto dto1 = new OrderParamDto();
        dto1.setInstrument_id("etc_usdt");
        cancleOrders.add(dto1);

        final Map<String, JSONObject> orderResult = this.marginOrderAPIService.cancleOrders(cancleOrders);
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 批量撤单 post协议
     */
    @Test
    public void batchCancleOrders_post() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();

        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("btc-usd");
        final List<Long> order_ids = new ArrayList<>();
        order_ids.add(23464L);
        order_ids.add(23465L);
        order_ids.add(23465L);
        order_ids.add(23465L);
        order_ids.add(23465L);
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

//        final OrderParamDto dto1 = new OrderParamDto();
//        dto1.setInstrument_id("etc_usdt");
//        cancleOrders.add(dto1);

        final Map<String, JSONObject> orderResult = this.marginOrderAPIService.cancleOrders_post(cancleOrders);
        this.toResultString(MarginOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 指定订单查询订单详情
     */
    @Test
    public void getOrderByProductIdAndOrderId() {
        final OrderInfo orderInfo = this.marginOrderAPIService.getOrderByProductIdAndOrderId("btc-usdt", "23844");
        this.toResultString(MarginOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    /**
     * 根据状态查询订单
     */
    @Test
    public void getOrders() {
        final List<OrderInfo> orderInfoList = this.marginOrderAPIService.getOrders("eth_usdt", "open", null, null, "3");
        this.toResultString(MarginOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 查询全部挂单
     */
    @Test
    public void getPendingOrders() {
        final List<OrderInfo> orderInfoList = this.marginOrderAPIService.getPendingOrders(null, null, null, null);
        this.toResultString(MarginOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 查询订单交易信息
     */
    @Test
    public void getFills() {
        final List<Fills> fills = this.marginOrderAPIService.getFills("23855", "btc-usd", null, null, "1");
        this.toResultString(MarginOrderAPITest.LOG, "fills", fills);
    }
}

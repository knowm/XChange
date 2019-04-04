package com.okcoin.commons.okex.open.api.test.spot;

import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.BatchOrdersResult;
import com.okcoin.commons.okex.open.api.bean.spot.result.Fills;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderInfo;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderResult;
import com.okcoin.commons.okex.open.api.service.spot.SpotOrderAPIServive;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotOrderApiServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpotOrderAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpotOrderAPITest.class);

    private SpotOrderAPIServive spotOrderAPIServive;

    @Before
    public void before() {
        this.config = this.config();
        this.spotOrderAPIServive = new SpotOrderApiServiceImpl(this.config);
    }

    /**
     * 下单
     */
    @Test
    public void addOrder() {
        for (int i = 0; i < 1; i++) {
            final long st = System.currentTimeMillis();

            final PlaceOrderParam order = new PlaceOrderParam();
            order.setClient_oid("20180728");
            order.setInstrument_id("btc-usdt");
            order.setPrice("20000");
            order.setType("limit");
            order.setSide("sell");
            order.setSize("0.001");
            order.setMargin_trading((byte) 1);
            final OrderResult orderResult = this.spotOrderAPIServive.addOrder(order);
            this.toResultString(SpotOrderAPITest.LOG, "orders", orderResult);

            final PlaceOrderParam order1 = new PlaceOrderParam();
            order1.setInstrument_id("btc-usdt");
            order1.setClient_oid("1234545");
            final String orderId = orderResult.getOrder_id().toString();
            final OrderResult orderResult1 = this.spotOrderAPIServive.cancleOrderByOrderId_post(order, orderId);
            this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult1);
            System.out.println("================ i=" + i + ", st=" + (System.currentTimeMillis() - st));

//            try {
//                Thread.sleep(100);
//            } catch (final InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     * 批量下单
     */
    @Test
    public void batchAddOrder() {
        final List<PlaceOrderParam> list = new ArrayList<>();

        final PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("20180918");
        order.setInstrument_id("btc-usdt");
        order.setPrice("10001");
        order.setType("limit");
        order.setSide("sell");
        order.setSize("0.01");
        list.add(order);

        final PlaceOrderParam order1 = new PlaceOrderParam();
        order1.setClient_oid("20180917");
        order1.setInstrument_id("btc-usdt");
        order1.setPrice("10002");
        order1.setType("limit");
        order1.setSide("sell");
        order1.setSize("0.01");
        list.add(order1);

        final Map<String, List<OrderResult>> orderResult = this.spotOrderAPIServive.addOrders(list);
        this.toResultString(SpotOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 撤销指定订单 delete协议 暂不使用
     */
    @Test
    public void cancleOrderByOrderId() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc-usdt");
        order.setClient_oid("1234545");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByOrderId(order, "1644664675964928");
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 撤销指定订单 post协议
     */
    @Test
    public void cancleOrderByOrderId_post() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("btc-usdt");
        order.setClient_oid("1234545");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByOrderId_post(order, "1644664675964928");
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
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
//        order_ids.add(1600593327162368L);
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

//        final OrderParamDto dto1 = new OrderParamDto();
//        dto1.setInstrument_id("etc_usdt");
//        cancleOrders.add(dto1);

        final Map<String, BatchOrdersResult> orderResult = this.spotOrderAPIServive.cancleOrders(cancleOrders);
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 批量撤单 post协议
     */
    @Test
    public void batchCancleOrders_post() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();

        final OrderParamDto dto = new OrderParamDto();
        dto.setInstrument_id("btc-usdt");
        final List<Long> order_ids = new ArrayList<>();
//        order_ids.add(1600593327162368L);
//        order_ids.add(1600593327162369L);
//        order_ids.add(1600593327162364L);
//        order_ids.add(1600593327162363L);
//        order_ids.add(1600593327162362L);
        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

//        final OrderParamDto dto1 = new OrderParamDto();
//        dto1.setInstrument_id("etc_usdt");
//        cancleOrders.add(dto1);

        final Map<String, BatchOrdersResult> orderResult = this.spotOrderAPIServive.cancleOrders_post(cancleOrders);
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 获取指定订单信息
     */
    @Test
    public void getOrderByOrderId() {
        final OrderInfo orderInfo = this.spotOrderAPIServive.getOrderByOrderId("bTC-USDT", "1673831663603712");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    /**
     * 查询订单列表
     */
    @Test
    public void getOrders() {
        final List<OrderInfo> orderInfoList = this.spotOrderAPIServive.getOrders("Btc-usdT", "all", null, null, "2");
            this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 批量查询挂单
     */
    @Test
    public void getPendingOrders() {
        final List<OrderInfo> orderInfoList = this.spotOrderAPIServive.getPendingOrders(null, null, "3", null);
        this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 成交明细
     */
    @Test
    public void getFills() {
        final List<Fills> fillsList = this.spotOrderAPIServive.getFills("23852", "btc-usdt", null, null, "2");
        this.toResultString(SpotOrderAPITest.LOG, "fillsList", fillsList);
    }

}

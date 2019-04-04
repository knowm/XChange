package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.Fills;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderInfo;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderResult;

import java.util.List;
import java.util.Map;

/**
 * 杠杆订单相关接口
 */
public interface MarginOrderAPIService {
    /**
     * 添加订单
     *
     * @param order
     * @return
     */
    OrderResult addOrder(PlaceOrderParam order);

    /**
     * 批量下单
     *
     * @param order
     * @return
     */
    Map<String, List<OrderResult>> addOrders(List<PlaceOrderParam> order);

    /**
     * 取消指定的订单 delete协议
     *
     * @param orderId
     */
    OrderResult cancleOrderByOrderId(final PlaceOrderParam order, String orderId);

    /**
     * 取消指定的订单 post协议
     *
     * @param orderId
     */
    OrderResult cancleOrderByOrderId_post(final PlaceOrderParam order, String orderId);

    /**
     * 批量取消订单 delete协议
     *
     * @param cancleOrders
     * @return
     */
    Map<String, JSONObject> cancleOrders(final List<OrderParamDto> cancleOrders);

    /**
     * 批量取消订单 post协议
     *
     * @param cancleOrders
     * @return
     */
    Map<String, JSONObject> cancleOrders_post(final List<OrderParamDto> cancleOrders);

    /**
     * 查询订单
     *
     * @param instrumentId
     * @param orderId
     * @return
     */
    OrderInfo getOrderByProductIdAndOrderId(String instrumentId, String orderId);

    /**
     * 订单列表
     *
     * @param instrumentId
     * @param status
     * @param from
     * @param to
     * @param limit
     * @return
     */
    List<OrderInfo> getOrders(String instrumentId, String status, String from, String to, String limit);

    /**
     * /* 订单列表
     *
     * @param from
     * @param to
     * @param limit
     * @return
     */
    List<OrderInfo> getPendingOrders(String from, String to, String limit, String instrument_id);

    /**
     * 账单列表
     *
     * @param orderId
     * @param instrumentId
     * @param from
     * @param to
     * @param limit
     * @return
     */
    List<Fills> getFills(String orderId, String instrumentId, String from, String to, String limit);
}

package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.BatchOrdersResult;
import com.okcoin.commons.okex.open.api.bean.spot.result.Fills;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderInfo;
import com.okcoin.commons.okex.open.api.bean.spot.result.OrderResult;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface SpotOrderAPI {

    /**
     * 单个下单
     *
     * @param order
     * @return
     */
    @POST("api/spot/v3/orders")
    Call<OrderResult> addOrder(@Body PlaceOrderParam order);

    /**
     * 批量下单
     *
     * @param param
     * @return
     */
    @POST("api/spot/v3/batch_orders")
    Call<Map<String, List<OrderResult>>> addOrders(@Body List<PlaceOrderParam> param);

    /**
     * 指定订单撤单 delete协议
     *
     * @param orderId
     * @param order
     * @return
     */
    @HTTP(method = "DELETE", path = "api/spot/v3/orders/{order_id}", hasBody = true)
    Call<OrderResult> cancleOrderByOrderId(@Path("order_id") String orderId,
                                           @Body PlaceOrderParam order);

    /**
     * 指定订单撤单 post协议
     *
     * @param orderId
     * @param order
     * @return
     */
    @HTTP(method = "POST", path = "api/spot/v3/cancel_orders/{order_id}", hasBody = true)
    Call<OrderResult> cancleOrderByOrderId_1(@Path("order_id") String orderId,
                                             @Body PlaceOrderParam order);

    /**
     * 批量撤单 delete协议
     *
     * @param cancleOrders
     * @return
     */
    @HTTP(method = "DELETE", path = "api/spot/v3/batch_orders", hasBody = true)
    Call<Map<String, BatchOrdersResult>> batchCancleOrders(@Body List<OrderParamDto> cancleOrders);

    /**
     * 批量撤单 post协议
     *
     * @param cancleOrders
     * @return
     */
    @HTTP(method = "POST", path = "api/spot/v3/cancel_batch_orders", hasBody = true)
    Call<Map<String, BatchOrdersResult>> batchCancleOrders_1(@Body List<OrderParamDto> cancleOrders);

    /**
     * 查询指定订单数据
     *
     * @param orderId
     * @param product
     * @return
     */
    @GET("api/spot/v3/orders/{order_id}")
    Call<OrderInfo> getOrderByOrderId(@Path("order_id") String orderId,
                                      @Query("instrument_id") String product);

    /**
     * 分页查询订单
     *
     * @param product
     * @param status
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @GET("api/spot/v3/orders")
    Call<List<OrderInfo>> getOrders(@Query("instrument_id") String product,
                                    @Query("status") String status,
                                    @Query("from") String from,
                                    @Query("to") String to,
                                    @Query("limit") String limit);

    /**
     * 分页查询挂单
     *
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @GET("api/spot/v3/orders_pending")
    Call<List<OrderInfo>> getPendingOrders(@Query("from") String from,
                                           @Query("to") String to,
                                           @Query("limit") String limit,
                                           @Query("instrument_id") String instrument_id);

    /**
     * 分页查询账单
     *
     * @param order_id
     * @param product
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @GET("api/spot/v3/fills")
    Call<List<Fills>> getFills(@Query("order_id") String order_id,
                               @Query("instrument_id") String product,
                               @Query("from") String from,
                               @Query("to") String to,
                               @Query("limit") String limit);

}

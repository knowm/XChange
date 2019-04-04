package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SwapTradeAPI {

    @POST("/api/swap/v3/order")
    Call<String> order(@Body JSONObject ppOrder);

    @POST("/api/swap/v3/orders")
    Call<String> orders(@Body JSONObject ppOrders);

    @POST("/api/swap/v3/cancel_order/{instrument_id}/{order_id}")
    Call<String> cancelOrder(@Path("instrument_id") String instrumentId, @Path("order_id") String orderId);

    @POST("/api/swap/v3/cancel_batch_orders/{instrument_id}")
    Call<String> cancelOrders(@Path("instrument_id") String instrumentId, @Body JSONObject ppOrders);

}

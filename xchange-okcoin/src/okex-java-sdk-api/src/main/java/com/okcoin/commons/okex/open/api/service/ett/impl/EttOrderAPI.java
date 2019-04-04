package com.okcoin.commons.okex.open.api.service.ett.impl;

import com.okcoin.commons.okex.open.api.bean.ett.param.EttCreateOrderParam;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCancelOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCreateOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttOrder;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
interface EttOrderAPI {

    @POST("/api/ett/v3/orders")
    Call<EttCreateOrderResult> createOrder(@Body EttCreateOrderParam param);

    @DELETE("/api/ett/v3/orders/{orderId}")
    Call<EttCancelOrderResult> cancelOrder(@Path("orderId") String orderId);

    @GET("/api/ett/v3/orders")
    Call<List<EttOrder>> getOrder(@Query("ett") String ett, @Query("type") Integer type, @Query("status") Integer status, @Query("before") String before, @Query("after") String after,
                                  @Query("limit") int limit);

    @GET("/api/ett/v3/orders/{orderId}")
    Call<EttOrder> getOrder(@Path("orderId") String orderId);

}

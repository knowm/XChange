package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Futures trade api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 19:20
 */
interface FuturesTradeAPI {

    @GET("/api/futures/v3/position")
    Call<JSONObject> getPositions();

    @GET("/api/futures/v3/{instrument_id}/position")
    Call<JSONObject> getInstrumentPosition(@Path("instrument_id") String instrumentId);

    @GET("/api/futures/v3/accounts")
    Call<JSONObject> getAccounts();

    @GET("/api/futures/v3/accounts/{currency}")
    Call<JSONObject> getAccountsByCurrency(@Path("currency") String currency);

    @GET("/api/futures/v3/accounts/{currency}/ledger")
    Call<JSONArray> getAccountsLedgerByCurrency(@Path("currency") String currency);

    @GET("/api/futures/v3/accounts/{instrument_id}/holds")
    Call<JSONObject> getAccountsHoldsByInstrumentId(@Path("instrument_id") String instrumentId);

    @POST("/api/futures/v3/order")
    Call<OrderResult> order(@Body JSONObject order);

    @POST("/api/futures/v3/orders")
    Call<JSONObject> orders(@Body JSONObject orders);

    @POST("/api/futures/v3/cancel_order/{instrument_id}/{order_id}")
    Call<JSONObject> cancelOrder(@Path("instrument_id") String instrumentId, @Path("order_id") String orderId);

    @POST("/api/futures/v3/cancel_batch_orders/{instrument_id}")
    Call<JSONObject> cancelOrders(@Path("instrument_id") String instrumentId, @Body JSONObject order_ids);

    @GET("/api/futures/v3/orders/{instrument_id}")
    Call<JSONObject> getOrders(@Path("instrument_id") String instrumentId, @Query("status") int status,
                               @Query("from") int from, @Query("to") int to, @Query("limit") int limit);

    @GET("/api/futures/v3/orders/{instrument_id}/{order_id}")
    Call<JSONObject> getOrder(@Path("instrument_id") String instrumentId, @Path("order_id") String orderId);

    @GET("/api/futures/v3/fills")
    Call<JSONArray> getFills(@Query("instrument_id") String instrumentId, @Query("order_id") String orderId,
                             @Query("from") int before, @Query("to") int after, @Query("limit") int limit);

    @GET("/api/futures/v3/accounts/{currency}/leverage")
    Call<JSONObject> getLeverRate(@Path("currency") String currency);

    @POST("/api/futures/v3/accounts/{currency}/leverage")
    Call<JSONObject> changeLevelRate(@Path("currency") String currency,
                                     @Query("instrument_id") String instrumentId,
                                     @Query("direction") String direction,
                                     @Query("leverage") int leverage);

    @POST("/api/futures/v3/accounts/{currency}/leverage")
    Call<JSONObject> changequancanLevelRate(@Path("currency") String currency,
                                            @Query("leverage") int leverage);
}
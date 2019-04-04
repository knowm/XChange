package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

public interface SwapUserAPI {


    @GET("/api/swap/v3/{instrument_id}/position")
    Call<String> getPosition(@Path("instrument_id") String instrumentId);

    @GET("/api/swap/v3/accounts")
    Call<String> getAccounts();

    @GET("/api/swap/v3/{instrument_id}/accounts")
    Call<String> selectAccount(@Path("instrument_id") String instrumentId);

    @GET("/api/swap/v3/accounts/{instrument_id}/settings")
    Call<String> selectContractSettings(@Path("instrument_id") String instrumentId);

    @POST("/api/swap/v3/accounts/{instrument_id}/leverage")
    Call<String> updateLevelRate(@Path("instrument_id") String instrumentId, @Body JSONObject levelRateParam);

    @GET("/api/swap/v3/orders/{instrument_id}")
    Call<String> selectOrders(@Path("instrument_id") String instrumentId, @Query("status") String status, @Query("from") String from, @Query("to") String to, @Query("limit") String limit);

    @GET("/api/swap/v3/orders/{instrument_id}/{order_id}")
    Call<String> selectOrder(@Path("instrument_id") String instrumentId, @Path("order_id") String orderId);

    @GET("/api/swap/v3/fills")
    Call<String> selectDealDetail(@Query("instrument_id") String instrumentId, @Query("order_id") String orderId, @Query("from") String from, @Query("to") String to, @Query("limit") String limit);

    @GET("/api/swap/v3/accounts/{instrument_id}/ledger")
    Call<String> getLedger(@Path("instrument_id") String instrumentId, @Query("from") String from, @Query("to") String to, @Query("limit") String limit);

    @GET("/api/swap/v3/accounts/{instrument_id}/holds")
    Call<String> getHolds(@Path("instrument_id") String instrumentId);

}

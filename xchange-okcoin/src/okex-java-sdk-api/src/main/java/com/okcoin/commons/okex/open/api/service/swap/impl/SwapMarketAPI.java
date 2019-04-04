package com.okcoin.commons.okex.open.api.service.swap.impl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SwapMarketAPI {

    @GET("/api/swap/v3/instruments")
    Call<String> getContractsApi();

    @GET("/api/swap/v3/instruments/{instrument_id}/depth")
    Call<String> getDepthApi(@Path("instrument_id") String instrumentId, @Query("size") String size);

    @GET("/api/swap/v3/instruments/ticker")
    Call<String> getTickersApi();

    @GET("/api/swap/v3/instruments/{instrument_id}/ticker")
    Call<String> getTickerApi(@Path("instrument_id") String instrumentId);

    @GET("/api/swap/v3/instruments/{instrument_id}/trades")
    Call<String> getTradesApi(@Path("instrument_id") String instrumentId, @Query("from") String from, @Query("to") String to, @Query("limit") String limit);

    @GET("/api/swap/v3/instruments/{instrument_id}/candles")
    Call<String> getCandlesApi(@Path("instrument_id") String instrumentId, @Query("start") String start, @Query("end") String end, @Query("granularity") String granularity);

    @GET("/api/swap/v3/instruments/{instrument_id}/index")
    Call<String> getIndexApi(@Path("instrument_id") String instrumentId);

    @GET("/api/swap/v3/rate")
    Call<String> getRateApi();

    @GET("/api/swap/v3/instruments/{instrument_id}/open_interest")
    Call<String> getOpenInterestApi(@Path("instrument_id") String instrumentId);

    @GET("/api/swap/v3/instruments/{instrument_id}/price_limit")
    Call<String> getPriceLimitApi(@Path("instrument_id") String instrumentId);

    @GET("/api/swap/v3/instruments/{instrument_id}/liquidation")
    Call<String> getLiquidationApi(@Path("instrument_id") String instrumentId, @Query("status") String status, @Query("from") String from, @Query("to") String to, @Query("limit") String limit);

    @GET("/api/swap/v3/instruments/{instrument_id}/funding_time")
    Call<String> getFundingTimeApi(@Path("instrument_id") String instrumentId);

    @GET("/api/swap/v3/instruments/{instrument_id}/historical_funding_rate")
    Call<String> getHistoricalFundingRateApi(@Path("instrument_id") String instrumentId, @Query("from") String from, @Query("to") String to, @Query("limit") String limit);

    @GET("/api/swap/v3/instruments/{instrument_id}/mark_price")
    Call<String> getMarkPriceApi(@Path("instrument_id") String instrumentId);

}

package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Futures market api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/8 20:51
 */
interface FuturesMarketAPI {

    @GET("/api/futures/v3/time")
    Call<ServerTime> getServerTime();

    @GET("/api/futures/v3/rate")
    Call<ExchangeRate> getExchangeRate();

    @GET("/api/futures/v3/instruments")
    Call<List<Instruments>> getInstruments();

    @GET("/api/futures/v3/instruments/currencies")
    Call<List<Currencies>> getCurrencies();

    @GET("/api/futures/v3/instruments/{instrument_id}/book")
    Call<Book> getInstrumentBook(@Path("instrument_id") String instrumentId, @Query("size") Integer size);

    @GET("/api/futures/v3/instruments/{instrument_id}/ticker")
    Call<Ticker> getInstrumentTicker(@Path("instrument_id") String instrumentId);

    @GET("/api/futures/v3/instruments/ticker")
    Call<List<Ticker>> getAllInstrumentTicker();

    @GET("/api/futures/v3/instruments/{instrument_id}/trades")
    Call<List<Trades>> getInstrumentTrades(@Path("instrument_id") String instrumentId, @Query("from") int from, @Query("to") int to, @Query("limit") int limit);

    @GET("/api/futures/v3/instruments/{instrument_id}/candles")
    Call<JSONArray> getInstrumentCandles(@Path("instrument_id") String instrumentId, @Query("start") String start, @Query("end") String end, @Query("granularity") String granularity);

    @GET("/api/futures/v3/instruments/{instrument_id}/index")
    Call<Index> getInstrumentIndex(@Path("instrument_id") String instrumentId);

    @GET("/api/futures/v3/instruments/{instrument_id}/estimated_price")
    Call<EstimatedPrice> getInstrumentEstimatedPrice(@Path("instrument_id") String instrumentId);

    @GET("/api/futures/v3/instruments/{instrument_id}/open_interest")
    Call<Holds> getInstrumentHolds(@Path("instrument_id") String instrumentId);

    @GET("/api/futures/v3/instruments/{instrument_id}/price_limit")
    Call<PriceLimit> getInstrumentPriceLimit(@Path("instrument_id") String instrumentId);

    @GET("/api/futures/v3/instruments/{instrument_id}/liquidation")
    Call<List<Liquidation>> getInstrumentLiquidation(@Path("instrument_id") String instrumentId, @Query("status") int status
            , @Query("from") int from, @Query("to") int to, @Query("limit") int limit);

    @GET("/api/futures/v3/instruments/{instrument_id}/mark_price")
    Call<JSONObject> getMarkPrice(@Path("instrument_id") String instrumentId);
}

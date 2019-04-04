package com.okcoin.commons.okex.open.api.service.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;

import java.util.List;

/**
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 16:06
 */
public interface FuturesMarketAPIService {

    /**
     * Get all of futures contract list
     */
    List<Instruments> getInstruments();

    /**
     * Get the futures contract currencies
     */
    List<Currencies> getCurrencies();

    /**
     * Get the futures contract product book
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     * @param size     value：1-200
     * @return
     */
    Book getInstrumentBook(String instrumentId, Integer size);

    /**
     * Get the futures contract product ticker
     *
     * @param productId The id of the futures contract eg: BTC-USD-0331"
     */
    Ticker getInstrumentTicker(String productId);

    /**
     * Get all futures contract product ticker
     *
     */
    List<Ticker> getAllInstrumentTicker();

    /**
     * Get the futures contract product trades
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    List<Trades> getInstrumentTrades(String instrumentId , int from, int to, int limit);

    /**
     * Get the futures contract product candles
     *
     * @param instrumentId   The id of the futures contract eg: BTC-USD-0331"
     * @param start       start timestamp of candles, (eg:1530676775258)
     * @param end         start timestamp of candles, (eg:1530676841895)
     * @param granularity Time granularity measured in seconds. data after the timestamp will be returned
     *                    60     ->  1min
     *                    180    ->  3min
     *                    300    ->  5min
     *                    900    ->  15min
     *                    1800   ->  30min
     *                    3600   ->  1hour
     *                    7200   ->  2hour
     *                    14400  ->  4hour
     *                    21600  ->  6hour
     *                    43200  ->  12hour
     *                    86400  ->  1day
     *                    604800 ->  1week
     */
    JSONArray getInstrumentCandles(String instrumentId, String start, String end, long granularity);

    /**
     * Get the futures contract product index
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    Index getInstrumentIndex(String instrumentId);

    /**
     * Get the futures contract product estimated price
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    EstimatedPrice getInstrumentEstimatedPrice(String instrumentId);

    /**
     * Get the futures contract product holds
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    Holds getInstrumentHolds(String instrumentId);

    /**
     * Get the futures contract product limit price
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    PriceLimit getInstrumentPriceLimit(String instrumentId);

    /**
     * Get the futures contract liquidation
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     * @param status    0:Last 7 days: Open 1:Last 7 days: Filled
     * @param from    Paging content after requesting this id .
     * @param to     Paging content prior to requesting this id.
     * @param limit     Number of results per request. Maximum 100. (default 100)
     */
    List<Liquidation> getInstrumentLiquidation(String instrumentId, int status, int from, int to, int limit);

    /**
     * Get MarkPrice
     *
     * @param instrumentId The id of the futures contract eg: BTC-USD-0331"
     */
    JSONObject getMarkPrice(String instrumentId);


}

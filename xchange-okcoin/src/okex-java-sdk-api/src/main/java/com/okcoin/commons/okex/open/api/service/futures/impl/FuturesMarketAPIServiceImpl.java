package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;

import java.util.List;

/**
 * Futures market api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 16:09
 */
public class FuturesMarketAPIServiceImpl implements FuturesMarketAPIService {

    private APIClient client;
    private FuturesMarketAPI api;

    public FuturesMarketAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(FuturesMarketAPI.class);
    }

    @Override
    public List<Instruments> getInstruments() {
        return this.client.executeSync(this.api.getInstruments());
    }

    @Override
    public List<Currencies> getCurrencies() {
        return this.client.executeSync(this.api.getCurrencies());
    }

    @Override
    public Book getInstrumentBook(String instrumentId, Integer size) {
        return this.client.executeSync(this.api.getInstrumentBook(instrumentId, size));
    }

    @Override
    public Ticker getInstrumentTicker(String instrumentId) {
        return this.client.executeSync(this.api.getInstrumentTicker(instrumentId));
    }

    @Override
    public List<Ticker> getAllInstrumentTicker() {
        return this.client.executeSync(this.api.getAllInstrumentTicker());
    }

    @Override
    public List<Trades> getInstrumentTrades(String instrumentId, int from, int to, int limit) {
        return this.client.executeSync(this.api.getInstrumentTrades(instrumentId,  from,  to,  limit));
    }

    @Override
    public JSONArray getInstrumentCandles(String instrumentId, String start, String end, long granularity) {
        return this.client.executeSync(this.api.getInstrumentCandles(instrumentId, String.valueOf(start), String.valueOf(end), String.valueOf(granularity)));
    }

    @Override
    public Index getInstrumentIndex(String instrumentId) {
        return this.client.executeSync(this.api.getInstrumentIndex(instrumentId));
    }

    @Override
    public EstimatedPrice getInstrumentEstimatedPrice(String instrumentId) {
        return this.client.executeSync(this.api.getInstrumentEstimatedPrice(instrumentId));
    }

    @Override
    public Holds getInstrumentHolds(String instrumentId) {
        return this.client.executeSync(this.api.getInstrumentHolds(instrumentId));
    }

    @Override
    public PriceLimit getInstrumentPriceLimit(String instrumentId) {
        return this.client.executeSync(this.api.getInstrumentPriceLimit(instrumentId));
    }

    @Override
    public List<Liquidation> getInstrumentLiquidation(String instrumentId, int status, int from, int to, int limit) {
        return this.client.executeSync(this.api.getInstrumentLiquidation(instrumentId, status,  from,  to,  limit));
    }

    @Override
    public JSONObject getMarkPrice(String instrumentId){
        return this.client.executeSync(this.api.getMarkPrice(instrumentId));
    }

}

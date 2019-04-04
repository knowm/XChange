package com.okcoin.commons.okex.open.api.test.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Futures market api tests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/12 14:54
 */
public class FuturesMarketAPITests extends FuturesAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(FuturesMarketAPITests.class);

    private FuturesMarketAPIService marketAPIService;

    @Before
    public void before() {
        config = config();
        marketAPIService = new FuturesMarketAPIServiceImpl(config);
    }

    @Test
    public void testGetInstruments() {
        List<Instruments> instruments = marketAPIService.getInstruments();
        toResultString(LOG, "Instruments", instruments);
    }

    @Test
    public void testGetCurrencies() {
        List<Currencies> currencies = marketAPIService.getCurrencies();
        toResultString(LOG, "Currencies", currencies);
    }

    @Test
    public void testGetInstrumentBook() {
        Book book = marketAPIService.getInstrumentBook(instrument_id, 2);
        toResultString(LOG, "Instrument-Book", book);
    }

    @Test
    public void testGetInstrumentTicker() {
        Ticker ticker = marketAPIService.getInstrumentTicker(instrument_id);
        toResultString(LOG, "Instrument-Ticker", ticker);
    }

    @Test
    public void testGetAllInstrumentTicker() {
        List<Ticker> tickers = marketAPIService.getAllInstrumentTicker();
        toResultString(LOG, "Instrument-Ticker", tickers);
    }

    @Test
    public void testGetInstrumentTrades() {
        List<Trades> trades = marketAPIService.getInstrumentTrades(instrument_id, from, to, limit);
        toResultString(LOG, "Instrument-Trades", trades);
    }

    @Test
    public void testGetInstrumentCandles() {
        String start = "2018-10-24T07:10:00.000Z";
        String end = "2018-10-24T07:20:00.000Z";
        JSONArray array = marketAPIService.getInstrumentCandles(instrument_id, start, end, 180L);
        toResultString(LOG, "Instrument-Candles", array);
    }

    @Test
    public void testGetInstrumentIndex() {
        Index index = marketAPIService.getInstrumentIndex(instrument_id);
        toResultString(LOG, "Instrument-Book", index);
    }

    @Test
    public void testGetInstrumentEstimatedPrice() {
        EstimatedPrice estimatedPrice = marketAPIService.getInstrumentEstimatedPrice(instrument_id);
        toResultString(LOG, "Instrument-Estimated-Price", estimatedPrice);
    }

    @Test
    public void testGetInstrumentHolds() {
        Holds holds = marketAPIService.getInstrumentHolds(instrument_id);
        toResultString(LOG, "Instrument-Holds", holds);
    }

    @Test
    public void testGetInstrumentPriceLimit() {
        PriceLimit priceLimit = marketAPIService.getInstrumentPriceLimit(instrument_id);
        toResultString(LOG, "Instrument-Price-Limit", priceLimit);
    }

    @Test
    public void testGetInstrumentLiquidation() {
        List<Liquidation> liquidations = marketAPIService.getInstrumentLiquidation(instrument_id, 1, 0, 0, 2);
        toResultString(LOG, "Instrument-Liquidation", liquidations);
    }

    @Test
    public void testGetMarkPrice() {
        JSONObject jsonObject = marketAPIService.getMarkPrice(instrument_id);
        toResultString(LOG, "MarkPrice", jsonObject);
    }
}

package org.knowm.xchange.blockchain.service.marketdata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.service.BlockchainBaseTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.*;

public class MarketDataServiceTest extends BlockchainBaseTest {
    private MarketDataService service;

    @Before
    public void init() {
        BlockchainExchange exchange = createExchange();
        service = exchange.getMarketDataService();
    }

    @Test(timeout = 2000)
    public void getOrderBookSuccess() throws Exception {
        stubGet(ORDERBOOK_JSON, 200, URL_ORDERBOOOK_L3);
        OrderBook response = service.getOrderBook(CurrencyPair.BTC_USD);
        assertThat(response).isNotNull();
        assertThat(response.getAsks().get(0).getLimitPrice()).isNotNull().isPositive();
        assertThat(response.getBids().get(0).getLimitPrice()).isNotNull().isPositive();
    }

    @Test(timeout = 2000)
    public void getOrderBookFailure() {
        stubGet(ORDERBOOK_FAILURE_JSON, 500, URL_ORDERBOOOK_L3);
        Throwable exception = catchThrowable(() -> service.getOrderBook(CurrencyPair.BTC_USD));
        assertThat(exception)
                .isInstanceOf(InternalServerException.class)
                .hasMessage(STATUS_CODE_500);
    }

    @Test(timeout = 2000)
    public void getOrderBookByInstrumentSuccess() throws Exception {
        stubGet(ORDERBOOK_JSON, 200, URL_ORDERBOOOK_L3);
        Instrument instrument = CurrencyPair.BTC_USD;
        OrderBook response = service.getOrderBook(instrument);
        assertThat(response).isNotNull();
        assertThat(response.getAsks().get(0).getLimitPrice()).isNotNull().isPositive();
        assertThat(response.getBids().get(0).getLimitPrice()).isNotNull().isPositive();
    }

    @Test(timeout = 2000)
    public void getTradesSuccess() throws Exception {
        stubGet(ORDERS_JSON, 200, URL_TRADES);
        Trades response = service.getTrades(CurrencyPair.BTC_USD, new Object[] {START_TIME, END_TIME});
        List<Trade> trades = response.getTrades();
        assertThat(trades).isNotNull();
        assertThat(trades).isNotEmpty();

        trades.forEach(
                record -> Assert.assertTrue(record.getOriginalAmount().compareTo(BigDecimal.ZERO) > 0));
    }

    @Test(timeout = 2000)
    public void getTradesFailure() {
        stubGet(TRADES_FAILURE_JSON, 400, URL_TRADES);
        Throwable exception = catchThrowable(() -> service.getTrades(CurrencyPair.BTC_USD, new Object[] {START_TIME, END_TIME}));
        assertThat(exception)
                .isInstanceOf(ExchangeException.class)
                .hasMessage(STATUS_CODE_400);
    }
}

package org.knowm.xchange.acx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.known.xchange.acx.AcxApi;
import org.known.xchange.acx.AcxMapper;
import org.known.xchange.acx.dto.AcxTrade;
import org.known.xchange.acx.dto.marketdata.AcxMarket;
import org.known.xchange.acx.dto.marketdata.AcxOrderBook;
import org.known.xchange.acx.service.marketdata.AcxMarketDataService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class AcxMarketDataServiceTest {

    private AcxApi api;
    private ObjectMapper objectMapper;
    private MarketDataService service;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        AcxMapper mapper = new AcxMapper();
        api = mock(AcxApi.class);
        service = new AcxMarketDataService(api, mapper);
    }

    @Test
    public void testTickers() throws IOException {
        when(api.getTicker("ethaud"))
                .thenReturn(read("/marketdata/tickers.json", AcxMarket.class));

        Ticker ticker = service.getTicker(CurrencyPair.ETH_AUD);

        assertEquals(ticker.getVolume(), new BigDecimal("576.3302"));
        assertEquals(ticker.getBid(), new BigDecimal("1119.33"));
    }

    @Test
    public void testOrderBooks() throws IOException {
        when(api.getOrderBook("ethaud"))
                .thenReturn(read("/marketdata/order_book.json", AcxOrderBook.class));

        OrderBook orderBook = service.getOrderBook(CurrencyPair.ETH_AUD);

        assertFalse(orderBook.getAsks().isEmpty());
        assertFalse(orderBook.getBids().isEmpty());
        assertEquals(new BigDecimal("1144.94"), orderBook.getAsks().get(0).getLimitPrice());
        assertEquals(new BigDecimal("1128.88"), orderBook.getBids().get(0).getLimitPrice());
    }

    @Test
    public void testTrades() throws IOException {
        when(api.getTrades("ethaud"))
                .thenReturn(read("/marketdata/trades.json", new TypeReference<List<AcxTrade>>(){}));

        Trades trades = service.getTrades(CurrencyPair.ETH_AUD);

        assertFalse(trades.getTrades().isEmpty());
        assertEquals(new BigDecimal("0.0085"), trades.getTrades().get(0).getOriginalAmount());
    }

    private <T> T read(String path, Class<T> clz) throws IOException {
        return objectMapper.readValue(this.getClass().getResourceAsStream(path), clz);
    }

    private <T> T read(String path, TypeReference<T> type) throws IOException {
        return objectMapper.readValue(this.getClass().getResourceAsStream(path), type);
    }
}

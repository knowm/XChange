package org.knowm.xchange.acx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    when(api.getTicker("ethaud")).thenReturn(read("/marketdata/tickers.json", AcxMarket.class));

    Ticker ticker = service.getTicker(CurrencyPair.ETH_AUD);

    assertEquals(new BigDecimal("576.3302"), ticker.getVolume());
    assertEquals(new BigDecimal("1119.33"), ticker.getBid());
    assertEquals(new Date(1513687641000L), ticker.getTimestamp());
  }

  @Test
  public void testOrderBooks() throws IOException {
    when(api.getOrderBook("ethaud", AcxMarketDataService.MAX_LIMIT, AcxMarketDataService.MAX_LIMIT))
        .thenReturn(read("/marketdata/order_book.json", AcxOrderBook.class));

    OrderBook orderBook = service.getOrderBook(CurrencyPair.ETH_AUD);

    assertFalse(orderBook.getAsks().isEmpty());
    assertFalse(orderBook.getBids().isEmpty());
    assertEquals(new BigDecimal("1144.94"), orderBook.getAsks().get(0).getLimitPrice());
    assertEquals(ASK, orderBook.getAsks().get(0).getType());
    assertEquals(new BigDecimal("1128.88"), orderBook.getBids().get(0).getLimitPrice());
    assertEquals(BID, orderBook.getBids().get(0).getType());
  }

  @Test
  public void testOrderBooksArguments() throws IOException {
    when(api.getOrderBook("ethaud", 5, 6))
        .thenReturn(read("/marketdata/order_book.json", AcxOrderBook.class));

    OrderBook orderBook = service.getOrderBook(CurrencyPair.ETH_AUD, 5, 6);

    assertFalse(orderBook.getAsks().isEmpty());
    assertFalse(orderBook.getBids().isEmpty());
  }

  @Test
  public void testTrades() throws IOException {
    when(api.getTrades("ethaud"))
        .thenReturn(read("/marketdata/trades.json", new TypeReference<List<AcxTrade>>() {}));

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

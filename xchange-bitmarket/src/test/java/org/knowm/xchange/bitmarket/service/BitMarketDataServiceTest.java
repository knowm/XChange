package org.knowm.xchange.bitmarket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarket;
import org.knowm.xchange.bitmarket.BitMarketAssert;
import org.knowm.xchange.bitmarket.BitMarketTestSupport;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;

@RunWith(MockitoJUnitRunner.class)
public class BitMarketDataServiceTest extends BitMarketTestSupport {

  private BitMarketDataService dataService;

  @Mock private Exchange exchange;

  @Mock private IRestProxyFactory restProxyFactory;

  @Mock private BitMarket bitMarket;

  @Before
  public void setUp() {
    when(exchange.getExchangeSpecification()).thenReturn(createExchangeSpecification());

    when(restProxyFactory.createProxy(
            eq(BitMarket.class), any(String.class), any(ClientConfig.class)))
        .thenReturn(bitMarket);

    dataService = new BitMarketDataService(exchange, restProxyFactory);
  }

  @Test
  public void constructor() {
    assertEquals(SPECIFICATION_API_KEY, dataService.apiKey);
  }

  @Test
  public void shouldGetTicker() throws IOException {
    // given
    BitMarketTicker response =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-ticker-data",
            BitMarketTicker.class);

    when(bitMarket.getTicker("BTCAUD")).thenReturn(response);

    // when
    Ticker ticker = dataService.getTicker(CurrencyPair.BTC_AUD);

    // then
    BitMarketAssert.assertEquals(ticker, TICKER);
  }

  @Test
  public void shouldGetTrades() throws IOException {
    // given
    final Trade[] expectedTrades = expectedTrades();
    BitMarketTrade[] response =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-trades-data",
            BitMarketTrade[].class);

    when(bitMarket.getTrades("BTCAUD")).thenReturn(response);

    // when
    Trades trades = dataService.getTrades(CurrencyPair.BTC_AUD);
    List<Trade> tradeList = trades.getTrades();

    // then
    assertThat(tradeList).hasSize(3);
    for (int i = 0; i < tradeList.size(); i++) {
      BitMarketAssert.assertEquals(tradeList.get(i), expectedTrades[i]);
    }
  }

  @Test
  public void shouldGetOrderBook() throws IOException {
    // given
    BitMarketOrderBook response =
        parse(
            "org/knowm/xchange/bitmarket/dto/marketdata/example-order-book-data",
            BitMarketOrderBook.class);

    when(bitMarket.getOrderBook("BTCAUD")).thenReturn(response);

    // when
    OrderBook orderBook = dataService.getOrderBook(CurrencyPair.BTC_AUD);

    // then
    BitMarketAssert.assertEquals(orderBook, ORDER_BOOK);
  }
}

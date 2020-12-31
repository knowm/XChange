package org.knowm.xchange.btcmarkets.service;

// Note:
//    I tried my best to get powermock to work after adding a logback.xml file to suppress verbose
// logging but there are some Java 9* issues
//    arising with powermock that I just cannot resolve. Therefore I decided to comment out all the
// powermock code in this module since it's the
//    only module using powermock. The dependencies have also been removed from the project.
// Hopefully someone will upgrade all these test to use
//    Mokito.
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.fail;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.BDDMockito.given;
// import static org.powermock.api.mockito.PowerMockito.mock;
//
// import java.io.IOException;
// import java.util.List;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.knowm.xchange.ExchangeFactory;
// import org.knowm.xchange.ExchangeSpecification;
// import org.knowm.xchange.btcmarkets.BTCMarkets;
// import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
// import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticatedV3;
// import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
// import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
// import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
// import org.knowm.xchange.client.ExchangeRestProxyBuilder;
// import org.knowm.xchange.currency.CurrencyPair;
// import org.knowm.xchange.dto.marketdata.OrderBook;
// import org.knowm.xchange.dto.marketdata.Ticker;
// import org.knowm.xchange.dto.trade.LimitOrder;
// import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;
//
// @RunWith(PowerMockRunner.class)
// @PrepareForTest(ExchangeRestProxyBuilder.class)
public class BTCMarketsMarketDataServiceTest extends BTCMarketsTestSupport {

  //  private BTCMarkets btcmarkets;
  //  private BTCMarketsMarketDataService marketDataService;
  //
  //  @Before
  //  public void setUp() {
  //    btcmarkets = mock(BTCMarkets.class);
  //    BTCMarketsAuthenticated btcMarketsAuthenticated = mock(BTCMarketsAuthenticated.class);
  //    BTCMarketsAuthenticatedV3 btcMarketsAuthenticatedV3 = mock(BTCMarketsAuthenticatedV3.class);
  //    PowerMockito.mockStatic(ExchangeRestProxyBuilder.class);
  //
  //    ExchangeRestProxyBuilder<BTCMarkets> exchangeRestProxyBuilderBtcMarketsMock =
  //        mock(ExchangeRestProxyBuilder.class);
  //    given(ExchangeRestProxyBuilder.forInterface(eq(BTCMarkets.class), any()))
  //        .willReturn(exchangeRestProxyBuilderBtcMarketsMock);
  //    given(exchangeRestProxyBuilderBtcMarketsMock.build()).willReturn(btcmarkets);
  //
  //    ExchangeRestProxyBuilder<BTCMarketsAuthenticated>
  //        exchangeRestProxyBuilderBTCMarketsAuthenticatedMock =
  // mock(ExchangeRestProxyBuilder.class);
  //    given(ExchangeRestProxyBuilder.forInterface(eq(BTCMarketsAuthenticated.class), any()))
  //        .willReturn(exchangeRestProxyBuilderBTCMarketsAuthenticatedMock);
  //    given(exchangeRestProxyBuilderBTCMarketsAuthenticatedMock.build())
  //        .willReturn(btcMarketsAuthenticated);
  //
  //    ExchangeRestProxyBuilder<BTCMarketsAuthenticatedV3>
  //        exchangeRestProxyBuilderBTCMarketsAuthenticatedV3Mock =
  //            mock(ExchangeRestProxyBuilder.class);
  //    given(ExchangeRestProxyBuilder.forInterface(eq(BTCMarketsAuthenticatedV3.class), any()))
  //        .willReturn(exchangeRestProxyBuilderBTCMarketsAuthenticatedV3Mock);
  //    given(exchangeRestProxyBuilderBTCMarketsAuthenticatedV3Mock.build())
  //        .willReturn(btcMarketsAuthenticatedV3);
  //
  //    BTCMarketsExchange exchange =
  //        (BTCMarketsExchange)
  //
  // ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
  //    ExchangeSpecification specification = exchange.getExchangeSpecification();
  //    specification.setUserName(SPECIFICATION_USERNAME);
  //    specification.setApiKey(SPECIFICATION_API_KEY);
  //    specification.setSecretKey(SPECIFICATION_SECRET_KEY);
  //
  //    marketDataService = new BTCMarketsMarketDataService(exchange);
  //  }
  //
  //  @Test
  //  public void shouldGetTicker() throws IOException {
  //    // given
  //    PowerMockito.when(btcmarkets.getTicker("BTC",
  // "AUD")).thenReturn(EXPECTED_BTC_MARKETS_TICKER);
  //
  //    // when
  //    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_AUD);
  //
  //    // then
  //    BtcMarketsAssert.assertEquals(ticker, EXPECTED_TICKER);
  //  }
  //
  //  @Test
  //  public void shouldGetOrderBook() throws IOException {
  //    // given
  //    final LimitOrder[] expectedAsks = expectedAsks();
  //    final LimitOrder[] expectedBids = expectedBids();
  //
  //    BTCMarketsOrderBook orderBookMock =
  //        parse("org/knowm/xchange/btcmarkets/dto/" + "ShortOrderBook",
  // BTCMarketsOrderBook.class);
  //
  //    PowerMockito.when(btcmarkets.getOrderBook("BTC", "AUD")).thenReturn(orderBookMock);
  //
  //    // when
  //    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD);
  //
  //    // then
  //    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1442997827000L);
  //
  //    List<LimitOrder> asks = orderBook.getAsks();
  //    assertThat(asks).hasSize(3);
  //    for (int i = 0; i < asks.size(); i++) {
  //      BtcMarketsAssert.assertEquals(asks.get(i), expectedAsks[i]);
  //    }
  //
  //    List<LimitOrder> bids = orderBook.getBids();
  //    assertThat(bids).hasSize(2);
  //    for (int i = 0; i < bids.size(); i++) {
  //      BtcMarketsAssert.assertEquals(bids.get(i), expectedBids[i]);
  //    }
  //  }
  //
  //  @Test(expected = NotYetImplementedForExchangeException.class)
  //  public void shouldFailWhenGetTrades() throws IOException {
  //    // when
  //    marketDataService.getTrades(CurrencyPair.BTC_AUD);
  //
  //    // then
  //    fail(
  //        "BTCMarketsMarketDataService should throw NotYetImplementedForExchangeException when
  // call getTrades");
  //  }
}

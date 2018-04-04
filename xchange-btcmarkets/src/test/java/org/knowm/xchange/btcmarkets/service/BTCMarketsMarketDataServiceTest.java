package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.BTCMarkets;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BTCMarkets.class)
public class BTCMarketsMarketDataServiceTest extends BTCMarketsTestSupport {

  private BTCMarketsMarketDataService marketDataService;

  @Before
  public void setUp() {
    BTCMarketsExchange exchange =
        (BTCMarketsExchange)
            ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    marketDataService = new BTCMarketsMarketDataService(exchange);
  }

  @Test
  public void shouldGetTicker() throws IOException {
    // given
    BTCMarkets btcmarkets = mock(BTCMarkets.class);
    PowerMockito.when(btcmarkets.getTicker("BTC", "AUD")).thenReturn(EXPECTED_BTC_MARKETS_TICKER);
    Whitebox.setInternalState(marketDataService, "btcmarkets", btcmarkets);

    // when
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_AUD);

    // then
    BtcMarketsAssert.assertEquals(ticker, EXPECTED_TICKER);
  }

  @Test
  public void shouldGetOrderBook() throws IOException {
    // given
    final LimitOrder[] expectedAsks = expectedAsks();
    final LimitOrder[] expectedBids = expectedBids();

    BTCMarketsOrderBook orderBookMock =
        parse("org/knowm/xchange/btcmarkets/dto/" + "ShortOrderBook", BTCMarketsOrderBook.class);

    BTCMarkets btcmarkets = mock(BTCMarkets.class);
    PowerMockito.when(btcmarkets.getOrderBook("BTC", "AUD")).thenReturn(orderBookMock);
    Whitebox.setInternalState(marketDataService, "btcmarkets", btcmarkets);

    // when
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD);

    // then
    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1442997827000L);

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(3);
    for (int i = 0; i < asks.size(); i++) {
      BtcMarketsAssert.assertEquals(asks.get(i), expectedAsks[i]);
    }

    List<LimitOrder> bids = orderBook.getBids();
    assertThat(bids).hasSize(2);
    for (int i = 0; i < bids.size(); i++) {
      BtcMarketsAssert.assertEquals(bids.get(i), expectedBids[i]);
    }
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenGetTrades() throws IOException {
    // when
    marketDataService.getTrades(CurrencyPair.BTC_AUD);

    // then
    fail(
        "BTCMarketsMarketDataService should throw NotYetImplementedForExchangeException when call getTrades");
  }
}

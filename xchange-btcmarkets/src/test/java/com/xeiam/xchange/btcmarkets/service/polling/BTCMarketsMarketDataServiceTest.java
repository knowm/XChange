package com.xeiam.xchange.btcmarkets.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.BTCMarkets;
import com.xeiam.xchange.btcmarkets.BTCMarketsExchange;
import com.xeiam.xchange.btcmarkets.BtcMarketsAssert;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BTCMarkets.class)
public class BTCMarketsMarketDataServiceTest extends BTCMarketsTestSupport {

  private BTCMarketsMarketDataService marketDataService;

  @Before
  public void setUp() {
    BTCMarketsExchange exchange = (BTCMarketsExchange) ExchangeFactory.INSTANCE.createExchange(
      BTCMarketsExchange.class.getCanonicalName());
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
    PowerMockito.when(btcmarkets.getTicker("BTC", "AUD")).thenReturn(BTC_MARKETS_TICKER);
    Whitebox.setInternalState(marketDataService, "btcmarkets", btcmarkets);

    // when
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_AUD);

    // then
    BtcMarketsAssert.assertEquals(ticker, TICKER);
  }

  @Test
  public void shouldGetOrderBook() throws IOException {
    // given
    BTCMarketsOrderBook orderBookMock = parse("ShortOrderBook", BTCMarketsOrderBook.class);

    BTCMarkets btcmarkets = mock(BTCMarkets.class);
    PowerMockito.when(btcmarkets.getOrderBook("BTC", "AUD")).thenReturn(orderBookMock);
    Whitebox.setInternalState(marketDataService, "btcmarkets", btcmarkets);

    // when
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_AUD);

    // then
    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1442997827000L);

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(3);
    for (int i=0; i<asks.size(); i++) {
      BtcMarketsAssert.assertEquals(asks.get(i), ASKS[i]);
    }

    List<LimitOrder> bids = orderBook.getBids();
    assertThat(bids).hasSize(2);
    for (int i=0; i<bids.size(); i++) {
      BtcMarketsAssert.assertEquals(bids.get(i), BIDS[i]);
    }
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenGetTrades() throws IOException {
    // when
    marketDataService.getTrades(CurrencyPair.BTC_AUD);

    // then
    fail("BTCMarketsMarketDataService should throw NotYetImplementedForExchangeException when call getTrades");
  }

}

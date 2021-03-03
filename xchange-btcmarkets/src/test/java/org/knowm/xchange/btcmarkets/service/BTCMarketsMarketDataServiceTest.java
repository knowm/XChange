package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class BTCMarketsMarketDataServiceTest extends BTCMarketsServiceTest {

  @Test
  public void shouldGetTicker() throws IOException {
    // given
    when(btcMarkets.getTicker("BTC", "AUD")).thenReturn(EXPECTED_BTC_MARKETS_TICKER);

    // when
    Ticker ticker = btcMarketsMarketDataService.getTicker(CurrencyPair.BTC_AUD);

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

    when(btcMarkets.getOrderBook("BTC", "AUD")).thenReturn(orderBookMock);

    // when
    OrderBook orderBook = btcMarketsMarketDataService.getOrderBook(CurrencyPair.BTC_AUD);

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
    btcMarketsMarketDataService.getTrades(CurrencyPair.BTC_AUD);

    // then
    fail(
        "BTCMarketsMarketDataService should throw NotYetImplementedForExchangeException when call getTrades");
  }
}

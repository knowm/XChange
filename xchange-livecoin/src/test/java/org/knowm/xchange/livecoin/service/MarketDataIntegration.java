package org.knowm.xchange.livecoin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;

public class MarketDataIntegration {

  private static LivecoinMarketDataService marketDataService;

  @BeforeClass
  public static void setUp() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(LivecoinExchange.class.getName());
    marketDataService = (LivecoinMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void tickerFetchTest() throws Exception {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getLast()).isNotNull().isPositive();
    assertThat(ticker.getHigh()).isNotNull().isPositive();
  }

  @Test
  public void invalidCurrencyPairForTickerFetchTest() throws Exception {
    Throwable excepton =
        catchThrowable(
            () -> marketDataService.getTicker(new CurrencyPair("NOT_EXISTING_CODE", "USD")));
    assertThat(excepton).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }

  @Test
  public void allOrderBooksFetchTest() throws Exception {
    Map<CurrencyPair, LivecoinOrderBook> orderBooksByPair =
        marketDataService.getAllOrderBooksRaw(10, true);
    assertThat(orderBooksByPair)
        .isNotNull()
        .containsKeys(CurrencyPair.BTC_USD, CurrencyPair.ETH_BTC);
    LivecoinOrderBook btcUsdOrderBook = orderBooksByPair.get(CurrencyPair.BTC_USD);
    LivecoinAsksBidsData[] btcUsdAsks = btcUsdOrderBook.getAsks();
    assertThat(btcUsdAsks).isNotEmpty();
    LivecoinAsksBidsData firstBtcUsdAsk = btcUsdAsks[0];
    assertThat(firstBtcUsdAsk.getQuantity()).isNotNull().isPositive();
  }

  @Test
  public void invalidCurrencyPairForTradesFetchTest() throws Exception {
    Throwable excepton =
        catchThrowable(
            () -> marketDataService.getTrades(new CurrencyPair("NOT_EXISTING_CODE", "USD")));
    assertThat(excepton).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }
}

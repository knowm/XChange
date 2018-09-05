package org.knowm.xchange.bittrex.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;

/** @author walec51 */
public class MarketDataIntegration {

  private static BittrexMarketDataService marketDataService;

  @BeforeClass
  public static void setUp() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    marketDataService = (BittrexMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void tickerTest() throws Exception {
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("LTC", "BTC"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
    assertThat(ticker.getLast()).isNotNull().isPositive();
    assertThat(ticker.getHigh()).isNotNull().isPositive();
  }

  @Test
  public void invalidCurrencyPairForTickerTest() throws Exception {
    Throwable excepton =
        catchThrowable(
            () -> marketDataService.getTicker(new CurrencyPair("NOT_EXISTING_CODE", "USD")));
    assertThat(excepton).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }

  @Test
  public void invalidCurrencyPairForTradesTest() throws Exception {
    Throwable excepton =
        catchThrowable(
            () -> marketDataService.getTrades(new CurrencyPair("NOT_EXISTING_CODE", "USD")));
    assertThat(excepton).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }

  @Test
  public void orderBooksTest() throws Exception {
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);
    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).isNotEmpty();
    LimitOrder firstAsk = asks.get(0);
    assertThat(firstAsk.getLimitPrice()).isNotNull().isPositive();
    assertThat(firstAsk.getRemainingAmount()).isNotNull().isPositive();
  }
}

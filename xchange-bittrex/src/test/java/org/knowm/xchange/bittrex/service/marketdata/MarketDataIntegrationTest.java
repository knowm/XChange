package org.knowm.xchange.bittrex.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.service.BittrexMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;

/** @author walec51 */
public class MarketDataIntegrationTest {

  private static BittrexMarketDataService marketDataService;

  @BeforeClass
  public static void setUp() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    marketDataService = (BittrexMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void tickerFetchTest() throws Exception {
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("LTC", "BTC"));
    System.out.println(ticker.toString());
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
  public void invalidCurrencyPairForTradesFetchTest() throws Exception {
    Throwable excepton =
        catchThrowable(
            () -> marketDataService.getTrades(new CurrencyPair("NOT_EXISTING_CODE", "USD")));
    assertThat(excepton).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }
}

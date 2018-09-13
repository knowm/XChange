package org.knowm.xchange.coingi.service.marketdata;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coingi.CoingiExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoingiExceptionIntegration {
  @Test
  public void invalidCurrencyPairForTradesFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoingiExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    Throwable exception =
        catchThrowable(
            () -> marketDataService.getTrades(new CurrencyPair("NOT_EXISTING_CODE", "USD")));
    assertThat(exception).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }
}

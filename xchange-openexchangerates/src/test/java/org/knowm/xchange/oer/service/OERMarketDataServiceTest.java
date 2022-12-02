package org.knowm.xchange.oer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.oer.OERExchange;
import org.knowm.xchange.oer.dto.marketdata.OERRates;

public class OERMarketDataServiceTest {

  @Test
  public void testTakesCorrectValueFromOERRates() throws IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(OERExchange.class);

    OERMarketDataService marketDataService =
        new OERMarketDataService(exchange) {
          @Override
          public OERRates getOERTicker(CurrencyPair pair) {
            assertThat(pair.base).isEqualTo(Currency.USD);
            OERRates r = new OERRates();
            r.setAUD(1.23d);
            return r;
          }
        };

    Ticker t = marketDataService.getTicker(CurrencyPair.USD_AUD);
    assertThat(t.getCurrencyPair()).isEqualTo(CurrencyPair.USD_AUD);
    assertThat(t.getLast()).isEqualTo(BigDecimal.valueOf(1.23d));
  }
}

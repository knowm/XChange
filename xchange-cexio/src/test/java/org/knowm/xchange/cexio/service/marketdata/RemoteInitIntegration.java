package org.knowm.xchange.cexio.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.cexio.dto.marketdata.CexIOCurrencyLimits;
import org.knowm.xchange.cexio.service.CexIOMarketDataService;

/** @author ujjwal on 14/02/18. */
public class RemoteInitIntegration {
  @Test
  public void integrationTest() throws IOException {
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class);

    assertThat(exchange).isNotNull();
    assertThat(exchange.getExchangeMetaData().getCurrencyPairs()).isNotEmpty();

    final CexIOCurrencyLimits currencyLimits =
        ((CexIOMarketDataService) exchange.getMarketDataService()).getCurrencyLimits();
    assertThat(currencyLimits.getE()).isEqualToIgnoringCase("currency_limits");
    assertThat(currencyLimits.getError()).isNullOrEmpty();
    assertThat(currencyLimits.getOk()).isEqualToIgnoringCase("ok");
  }
}

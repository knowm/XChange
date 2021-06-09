package org.knowm.xchange.okex.v5;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

import static org.assertj.core.api.Assertions.assertThat;

public class OkexExchangeIntegrationTest {
  @Test
  public void testCreateExchangeShouldApplyDefaultSpecification() throws Exception {
    ExchangeSpecification spec =
        ExchangeFactory.INSTANCE
            .createExchange(OkexExchange.class)
            .getDefaultExchangeSpecification();
    //        spec.setApiKey("");
    //        spec.setSecretKey("");
    //        spec.setExchangeSpecificParametersItem("passphrase", "");
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);

    assertThat(exchange.getExchangeSpecification().getSslUri()).isEqualTo("https://www.okex.com");
    assertThat(exchange.getExchangeSpecification().getHost()).isEqualTo("okex.com");
  }
}

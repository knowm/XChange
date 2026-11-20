package org.knowm.xchange.hitbtc.v2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

class HitbtcExchangeTest {

  @Test
  void createExchangeWithoutRemoteMetadata() {
    ExchangeSpecification spec = new ExchangeSpecification(HitbtcExchange.class);
    spec.setShouldLoadRemoteMetaData(false);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);

    assertThat(exchange).isInstanceOf(HitbtcExchange.class);
    assertThat(exchange.getMarketDataService()).isNotNull();
    assertThat(exchange.getTradeService()).isNotNull();
    assertThat(exchange.getAccountService()).isNotNull();

    ExchangeSpecification defaults = exchange.getDefaultExchangeSpecification();
    assertThat(defaults.getExchangeName()).isEqualTo("Hitbtc");
    assertThat(defaults.getHost()).isEqualTo("hitbtc.com");
    assertThat(defaults.getSslUri()).isEqualTo("https://api.hitbtc.com");
  }
}

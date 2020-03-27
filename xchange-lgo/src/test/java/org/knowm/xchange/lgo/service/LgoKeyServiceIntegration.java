package org.knowm.xchange.lgo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoEnv.SignatureService;
import org.knowm.xchange.lgo.dto.key.LgoKey;

public class LgoKeyServiceIntegration {

  private LgoKeyService lgoKeyService;

  @Before
  public void setUp() {
    ExchangeSpecification spec = LgoEnv.sandbox();
    spec.setShouldLoadRemoteMetaData(false);
    spec.getExchangeSpecificParameters()
        .put(LgoEnv.SIGNATURE_SERVICE, SignatureService.PASSTHROUGHS);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);

    lgoKeyService = new LgoKeyService(exchange.getExchangeSpecification());
  }

  @Test
  public void fetchAValidKey() {
    LgoKey key = lgoKeyService.selectKey();

    assertThat(key).isNotNull();
    assertThat(key.getDisabledAt()).isAfter(Instant.now());
  }

  @Test
  public void returnsTheSameKeyIfValid() {
    LgoKey firstKey = lgoKeyService.selectKey();
    LgoKey secondKey = lgoKeyService.selectKey();

    assertThat(firstKey).isEqualTo(secondKey);
  }
}

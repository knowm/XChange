package org.knowm.xchange.bitrue;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitrue.service.BitrueAccountService;

import java.io.IOException;

public class BitrueExchangeIntegration {
  protected static BitrueExchange exchange;
  @Rule public WireMockRule wireMockRule = new WireMockRule();

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
  }



  protected static void createExchange() throws Exception {
    exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BitrueExchange.class);
    ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
    spec.setShouldLoadRemoteMetaData(true);
//    spec.setApiKey("vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A");
//    spec.setSecretKey("NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j");
    exchange.applySpecification(spec);
  }

  protected void assumeProduction() {
    Assume.assumeFalse("Using sandbox", false);
  }

  protected BitrueExchange createExchangeMocked() {
    BitrueExchange exchangeMocked =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BitrueExchange.class);
    ExchangeSpecification specification = exchangeMocked.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.port() + "/");
    specification.setPort(wireMockRule.port());
    specification.setShouldLoadRemoteMetaData(false);
    exchangeMocked.applySpecification(specification);
    return exchangeMocked;
  }
}

package org.knowm.xchange.bitrue.service.account;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitrue.BitrueExchange;

public class AccountServiceTest {

  @Rule public final WireMockRule wireMockRule = new WireMockRule();



  @Test
  public void createExchange() {
    BitrueExchange exchange =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BitrueExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
//    specification.setHost("localhost");
//    specification.setSslUri("http://localhost:" + wireMockRule.port() + "/");
//    specification.setPort(wireMockRule.port());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setHttpReadTimeout(1000);
    specification.setExchangeSpecificParametersItem("recvWindow", "20000");
    specification.setSecretKey("166d1f1903f15adcb16a862f884c56f42e0386aba53138c5b599c344f60e6284");
    specification.setApiKey("e05def3fe6d924ff06872ee57af0b2688411af0ec75097a22746e17853f45e5c");
    exchange.applySpecification(specification);
    exchange.remoteInit();


  }
}

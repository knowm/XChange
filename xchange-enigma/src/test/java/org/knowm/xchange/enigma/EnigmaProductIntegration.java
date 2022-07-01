package org.knowm.xchange.enigma;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.service.EnigmaAccountService;
import org.knowm.xchange.enigma.service.EnigmaMarketDataService;

public class EnigmaProductIntegration {

  private static final String TEST_SSL_URI = "https://api-test.enigma-securities.io/";
  private static final String TEST_HOST = "api-test.enigma-securities.io";

  private String username = "vlad_test";
  private String password = "nw4dZ:gz";
  private String infra = "dev";
  private EnigmaExchange enigmaExchange;
  private EnigmaAccountService accountService;
  private EnigmaMarketDataService marketDataService;

  @Before
  public void init() throws IOException {
    enigmaExchange = new EnigmaExchange();
    ExchangeSpecification exchangeSpec = enigmaExchange.getDefaultExchangeSpecification();
    exchangeSpec.setExchangeSpecificParametersItem("infra", infra);
    exchangeSpec.setSslUri(TEST_SSL_URI);
    exchangeSpec.setHost(TEST_HOST);
    exchangeSpec.setUserName(username);
    exchangeSpec.setPassword(password);
    enigmaExchange.applySpecification(exchangeSpec);
    accountService = new EnigmaAccountService(enigmaExchange);
    marketDataService = new EnigmaMarketDataService(enigmaExchange);
    accountService.login();
  }

  @Test()
  public void getProducts() throws IOException {
    List<EnigmaProduct> enigmaProducts = marketDataService.getProducts();
    assertThat(enigmaProducts).isNotEmpty();
    assertThat(enigmaProducts.get(0)).isNotNull();
    assertThat(enigmaProducts.get(0).getProductName()).isEqualTo("BTC-EUR");
  }
}

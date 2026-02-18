package org.knowm.xchange.deribit.v2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.dto.account.AccountInfo;

public class DeribitAccountServiceIntegration {

  static Exchange exchange;

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    String apiKey = System.getProperty("apiKey");
    String secretKey = System.getProperty("secretKey");
    assumeThat(apiKey).isNotEmpty();
    assumeThat(secretKey).isNotEmpty();

    ExchangeSpecification exSpec = new ExchangeSpecification(DeribitExchange.class);
    exSpec.setApiKey(apiKey);
    exSpec.setSecretKey(secretKey);
    exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo.getWallet("main").getBalances()).isNotEmpty();
  }
}

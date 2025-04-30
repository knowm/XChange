package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.dto.account.AccountInfo;

class BitgetAccountServiceIntegration {

  static BitgetExchange exchange;

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    String apiKey = System.getProperty("apiKey");
    String secretKey = System.getProperty("secretKey");
    String passphrase = System.getProperty("passphrase");
    assumeThat(apiKey).isNotEmpty();
    assumeThat(secretKey).isNotEmpty();
    assumeThat(passphrase).isNotEmpty();

    ExchangeSpecification exSpec = new ExchangeSpecification(BitgetExchange.class);
    exSpec.setApiKey(apiKey);
    exSpec.setSecretKey(secretKey);
    exSpec.setPassword(passphrase);
    exchange = (BitgetExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo.getWallet("spot").getBalances()).isNotEmpty();
  }
}

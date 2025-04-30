package org.knowm.xchange.bitmex.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;

class BitmexAccountServiceIntegration {

  static BitmexExchange exchange;

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    String apiKey = System.getProperty("apiKey");
    String secretKey = System.getProperty("secretKey");
    assumeThat(apiKey).isNotEmpty();
    assumeThat(secretKey).isNotEmpty();

    ExchangeSpecification exSpec = new ExchangeSpecification(BitmexExchange.class);
    exSpec.setApiKey(apiKey);
    exSpec.setSecretKey(secretKey);
    exchange = (BitmexExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo.getWallet(WalletFeature.TRADING).getBalances()).isNotEmpty();
  }
}

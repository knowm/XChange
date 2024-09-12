package org.knowm.xchange.coinex.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.dto.account.AccountInfo;

class CoinexAccountServiceIntegration {

  static CoinexExchange exchange;

  @BeforeAll
  public static void credentialsPresent() {
    // skip if there are no credentials
    String apiKey = System.getProperty("apiKey");
    String secretKey = System.getProperty("secretKey");
    assumeThat(apiKey).isNotEmpty();
    assumeThat(secretKey).isNotEmpty();

    exchange = ExchangeFactory.INSTANCE.createExchange(CoinexExchange.class, apiKey, secretKey);
  }

  @Test
  void valid_balances() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();
    assertThat(accountInfo.getWallet("spot").getBalances()).isNotEmpty();
  }


}
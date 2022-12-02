package org.knowm.xchange.btcturk.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcturk.BTCTurkExchange;
import org.knowm.xchange.btcturk.dto.account.BTCTurkAccountBalance;
import org.knowm.xchange.btcturk.service.BTCTurkAccountService;
import org.knowm.xchange.btcturk.service.BTCTurkDemoUtilsTest;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/** @author mertguner */
public class AccountDataFetchIntegration {

  private Exchange btcTurk;
  private BTCTurkAccountService btcTurkAccountService;
  private AccountService accountService;

  @Before
  public void InitExchange() throws IOException {
    if (BTCTurkDemoUtilsTest.BTCTURK_APIKEY.isEmpty())
      btcTurk = ExchangeFactory.INSTANCE.createExchange(BTCTurkExchange.class);
    else {
      ExchangeSpecification exSpec = new BTCTurkExchange().getDefaultExchangeSpecification();
      exSpec.setApiKey(BTCTurkDemoUtilsTest.BTCTURK_APIKEY);
      exSpec.setSecretKey(BTCTurkDemoUtilsTest.BTCTURK_SECRETKEY);
      btcTurk = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    accountService = btcTurk.getAccountService();
    btcTurkAccountService = (BTCTurkAccountService) accountService;
  }

  @Test
  public void testBalance() throws IOException, InterruptedException {

    if (!BTCTurkDemoUtilsTest.BTCTURK_APIKEY.isEmpty()) {
      // BTCTurkAccountBalance Test
      BTCTurkAccountBalance accountBalance = btcTurkAccountService.getBTCTurkBalance();
      assertThat(accountBalance).isNotEqualTo(null);
      assertThat(accountBalance.getBtctry_maker_fee_percentage())
          .isEqualTo(new BigDecimal("0.0012711860000000"));

      // AccountInfo Test
      Thread.sleep(1000);
      AccountInfo accountInfo = btcTurkAccountService.getAccountInfo();
      assertThat(accountInfo).isNotEqualTo(null);
    } else assertThat(accountService).isNotEqualTo(null);
  }
}

package org.knowm.xchange.gatehub;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.gatehub.service.polling.GatehubAccountService;
import org.knowm.xchange.service.polling.account.PollingAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatehubTest {

  private static final Logger log = LoggerFactory.getLogger(GatehubTest.class);

  private PollingAccountService account;
  private GatehubAccountService gatehubAccount;

  @Before
  public void setUp() throws Exception {
    Exchange gatehubExchange = GatehubTestUtils.createExchange();
    account = gatehubExchange.getPollingAccountService();
    gatehubAccount = (GatehubAccountService) gatehubExchange.getPollingAccountService();
  }

  @Test
  @Ignore
  public void shouldAccessBalance() throws Exception {
    Wallet wallet = account.getAccountInfo().getWallet();
    Currency currency = new Currency("ETC");
    assertThat(wallet.getBalances().keySet()).contains(currency);
    Balance ethBalance = wallet.getBalance(currency);
    assertThat(ethBalance).isNotNull();
    assertThat(ethBalance.getTotal()).isPositive();
    assertThat(ethBalance.getAvailable()).isPositive();
    assertThat(ethBalance.getAvailableForWithdrawal()).isPositive();
  }

  @Test
  @Ignore
  public void shouldWithdrawToEthereum() throws Exception {
    String uid = account.withdrawFunds(Currency.ETH, new BigDecimal("0.0107"), "0xe1ac22057c3e7f3563c2d1ce3cd4f55b0f976223");
    log.debug("uid = {}", uid);
  }

  @Test
  @Ignore
  public void shouldWithdrawToRipple() throws Exception {
    String uid = gatehubAccount.withdrawFundsToRipple(Currency.ETH, new BigDecimal("0.0106"), "rBpBWWgnnXgdsyxDatzTGkRYE2PEv2Txcd");
    log.debug("uid = {}", uid);
  }
}
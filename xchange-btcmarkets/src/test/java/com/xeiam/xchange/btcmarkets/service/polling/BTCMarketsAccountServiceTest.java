package com.xeiam.xchange.btcmarkets.service.polling;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.BTCMarketsAuthenticated;
import com.xeiam.xchange.btcmarkets.BTCMarketsExchange;
import com.xeiam.xchange.btcmarkets.BtcMarketsAssert;
import com.xeiam.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import com.xeiam.xchange.btcmarkets.service.BTCMarketsDigest;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;

import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BTCMarketsAuthenticated.class)
public class BTCMarketsAccountServiceTest extends BTCMarketsTestSupport {

  private BTCMarketsAccountService accountService;

  @Before
  public void setUp() {
    BTCMarketsExchange exchange = (BTCMarketsExchange) ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    accountService = new BTCMarketsAccountService(exchange);
  }

  @Test
  public void shouldCreateAccountInfo() throws IOException {
    // given
    BTCMarketsBalance balance = parse(BTCMarketsBalance.class);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito
        .when(btcm.getBalance(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(SynchronizedValueFactory.class), Mockito.any(BTCMarketsDigest.class)))
        .thenReturn(Arrays.asList(balance));
    Whitebox.setInternalState(accountService, "btcm", btcm);

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getUsername()).isEqualTo(SPECIFICATION_USERNAME);
    assertThat(accountInfo.getTradingFee()).isNull();
    assertThat(accountInfo.getWallets()).hasSize(1);

    BtcMarketsAssert.assertEquals(accountInfo.getWallet().getBalance(Currency.BTC), EXPECTED_BALANCE);
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenWithdrawFunds() throws IOException {
    // when
    accountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "any address");

    // then
    fail("BTCMarketsAccountService should throw NotYetImplementedForExchangeException when call withdrawFunds");
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenRequestDepositAddress() throws IOException {
    // when
    accountService.requestDepositAddress(Currency.BTC);

    // then
    fail("BTCMarketsAccountService should throw NotYetImplementedForExchangeException when call requestDepositAddress");
  }
}

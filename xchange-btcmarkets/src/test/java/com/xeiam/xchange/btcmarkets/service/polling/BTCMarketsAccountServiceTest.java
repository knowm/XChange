package com.xeiam.xchange.btcmarkets.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.BTCMarketsAuthenticated;
import com.xeiam.xchange.btcmarkets.BTCMarketsExchange;
import com.xeiam.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import com.xeiam.xchange.btcmarkets.service.BTCMarketsDigest;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BTCMarketsAuthenticated.class)
public class BTCMarketsAccountServiceTest {

  private BTCMarketsAccountService accountService;

  @Before
  public void setUp() {
    BTCMarketsExchange exchange =
        (BTCMarketsExchange) ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName("admin");
    specification.setApiKey("publicKey");
    specification.setSecretKey("secretKey");

    accountService = new BTCMarketsAccountService(exchange);
  }

  @Test
  public void shouldCreateAccountInfo() throws IOException {
    // given
    BTCMarketsBalance balance = new BTCMarketsBalance();
    Whitebox.setInternalState(balance, "pendingFunds", new BigDecimal("10.00000000"));
    Whitebox.setInternalState(balance, "balance", new BigDecimal("30.00000000"));
    Whitebox.setInternalState(balance, "currency", "BTC");

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(btcm.getBalance(Mockito.eq("publicKey"),
        Mockito.any(SynchronizedValueFactory.class),
        Mockito.any(BTCMarketsDigest.class))).thenReturn(Arrays.asList(balance));
    Whitebox.setInternalState(accountService, "btcm", btcm);

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getUsername()).isEqualTo("admin");
    assertThat(accountInfo.getTradingFee()).isNull();
    assertThat(accountInfo.getWallets()).hasSize(1);

    Balance btcBalance = accountInfo.getWallet().getBalance(Currency.BTC);
    assertThat(btcBalance).isNotNull();
    assertThat(btcBalance.getTotal()).isEqualTo(new BigDecimal("30.00000000"));
    assertThat(btcBalance.getAvailable()).isEqualTo(new BigDecimal("20.00000000"));
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenWithdrawFunds() throws IOException {
    // when
    accountService.withdrawFunds(Currency.BTC, new BigDecimal("1000"), "any address");

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

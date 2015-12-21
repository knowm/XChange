package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeCompareUtils;
import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalance;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BleutradeAccountServiceTest extends BleutradeServiceTestSupport {

  private BleutradeAccountService accountService;

  @Before
  public void setUp() {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(
      BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName(SPECIFICATION_USERNAME);
    exchange.getExchangeSpecification().setApiKey(SPECIFICATION_API_KEY);
    exchange.getExchangeSpecification().setSecretKey(SPECIFICATION_SECRET_KEY);

    accountService = new BleutradeAccountService(exchange);
  }

  @Test
  public void constructor() throws Exception {
    assertThat(Whitebox.getInternalState(accountService, "apiKey")).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test
  public void shouldGetBalance() throws IOException {
    // given
    BleutradeBalanceReturn balanceReturn = new BleutradeBalanceReturn();
    balanceReturn.setSuccess(true);
    balanceReturn.setMessage("test message");
    balanceReturn.setResult(BLEUTRADE_ACCOUNT_INFO.get(0));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalance(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(balanceReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    BleutradeBalance balance = accountService.getBleutradeBalance("AUD");

    // then
    BleutradeCompareUtils.compareBleutradeBalances(balance, BLEUTRADE_ACCOUNT_INFO.get(0));
    assertThat(balance.toString()).isEqualTo(BLEUTRADE_BALANCE_STR);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetBalance() throws IOException {
    // given
    BleutradeBalanceReturn balanceReturn = new BleutradeBalanceReturn();
    balanceReturn.setSuccess(false);
    balanceReturn.setMessage("test message");
    balanceReturn.setResult(BLEUTRADE_ACCOUNT_INFO.get(0));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalance(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(balanceReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getBleutradeBalance("AUD");

    // then
    fail("BleutradeAccountService should throw ExchangeException when balance request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetBalanceThrowError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalance(Mockito.eq(SPECIFICATION_API_KEY),
        Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD")))
      .thenThrow(BleutradeException.class);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getBleutradeBalance("AUD");

    // then
    fail("BleutradeAccountService should throw ExchangeException when balance request throw error");
  }

  @Test
  public void shouldGetAccountInfo() throws IOException {
    // given
    BleutradeBalancesReturn balancesReturn = new BleutradeBalancesReturn();
    balancesReturn.setSuccess(true);
    balancesReturn.setMessage("test message");
    balancesReturn.setResult(BLEUTRADE_ACCOUNT_INFO);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalances(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(balancesReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getWallets()).hasSize(1);

    Map<Currency,Balance> balances = accountInfo.getWallet(null).getBalances();
    assertThat(balances).hasSize(3);

    BleutradeCompareUtils.compareBalances(balances.get(Currency.AUD), ACCOUNT_BALANCES[0]);
    BleutradeCompareUtils.compareBalances(balances.get(Currency.BTC), ACCOUNT_BALANCES[1]);
    BleutradeCompareUtils.compareBalances(balances.get(new Currency("BLEU")),ACCOUNT_BALANCES[2]);
  }

  @Test(expected = ExchangeException.class) public void shouldFailOnUnsuccessfulGetAccountInfo() throws IOException {
    // given
    BleutradeBalancesReturn balancesReturn = new BleutradeBalancesReturn();
    balancesReturn.setSuccess(false);
    balancesReturn.setMessage("test message");
    balancesReturn.setResult(BLEUTRADE_ACCOUNT_INFO);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalances(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(balancesReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getAccountInfo();

    // then
    fail("BleutradeAccountService should throw ExchangeException when balances request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetAccountInfoThrowError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalances(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getAccountInfo();

    // then
    fail("BleutradeAccountService should throw ExchangeException when balances request throw error");
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenWithdrawFunds() throws IOException {
    // when
    accountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "any address");

    // then
    fail("BleutradeAccountService should throw NotYetImplementedForExchangeException when call withdrawFunds");
  }

  @Test
  public void shouldRequestDepositAddress() throws IOException {
    // given
    BleutradeDepositAddressReturn addressReturn = new BleutradeDepositAddressReturn();
    addressReturn.setSuccess(true);
    addressReturn.setMessage("test message");
    addressReturn.setResult(BLEUTRADE_DEPOSIT_ADDRESS);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getDepositAddress(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(addressReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    String depositAddress = accountService.requestDepositAddress(Currency.AUD);

    // then
    assertThat(depositAddress).isEqualTo("Deposit Address Details");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulRequestDepositAddress() throws IOException {
    // given
    BleutradeDepositAddressReturn addressReturn = new BleutradeDepositAddressReturn();
    addressReturn.setSuccess(false);
    addressReturn.setMessage("test message");
    addressReturn.setResult(BLEUTRADE_DEPOSIT_ADDRESS);

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getDepositAddress(Mockito.eq(SPECIFICATION_API_KEY), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(addressReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.requestDepositAddress(Currency.AUD);

    // then
    fail("BleutradeAccountService should throw ExchangeException when deposit address request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailRequestDepositAddressError() throws IOException {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getDepositAddress(Mockito.eq(SPECIFICATION_API_KEY),
        Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD")))
        .thenThrow(BleutradeException.class);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.requestDepositAddress(Currency.AUD);

    // then
    fail("BleutradeAccountService should throw ExchangeException when deposit address request throw exception");
  }
}

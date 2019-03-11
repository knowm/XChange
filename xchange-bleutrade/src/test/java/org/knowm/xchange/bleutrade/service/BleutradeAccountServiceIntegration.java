package org.knowm.xchange.bleutrade.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bleutrade.BleutradeAssert;
import org.knowm.xchange.bleutrade.BleutradeAuthenticated;
import org.knowm.xchange.bleutrade.BleutradeException;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalance;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeWithdrawReturn;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.ExchangeException;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(MockitoJUnitRunner.class)
public class BleutradeAccountServiceIntegration extends BleutradeServiceTestSupport {

  private BleutradeAccountService accountService;

  @Mock private BleutradeAuthenticated bleutrade;

  @Before
  public void setUp() {
    BleutradeExchange exchange =
        (BleutradeExchange)
            ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName(SPECIFICATION_USERNAME);
    exchange.getExchangeSpecification().setApiKey(SPECIFICATION_API_KEY);
    exchange.getExchangeSpecification().setSecretKey(SPECIFICATION_SECRET_KEY);

    IRestProxyFactory restProxyFactory = mock(IRestProxyFactory.class);
    when(restProxyFactory.createProxy(
            eq(BleutradeAuthenticated.class), any(String.class), any(ClientConfig.class)))
        .thenReturn(bleutrade);

    accountService = new BleutradeAccountService(exchange, restProxyFactory);
  }

  @Test
  public void constructor() {
    assertThat(accountService.apiKey).isEqualTo(SPECIFICATION_API_KEY);
  }

  @Test
  public void shouldGetBalance() throws IOException {
    // given
    final List<BleutradeBalance> expectedBleutradeAccountInfo = expectedBleutradeAccountInfo();

    BleutradeBalanceReturn balanceReturn = new BleutradeBalanceReturn();
    balanceReturn.setSuccess(true);
    balanceReturn.setMessage("test message");
    balanceReturn.setResult(expectedBleutradeAccountInfo.get(0));

    when(bleutrade.getBalance(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("AUD")))
        .thenReturn(balanceReturn);

    // when
    BleutradeBalance balance = accountService.getBleutradeBalance("AUD");

    // then
    BleutradeAssert.assertEquals(balance, expectedBleutradeAccountInfo.get(0));
    assertThat(balance.toString()).isEqualTo(BLEUTRADE_BALANCE_STR);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetBalance() throws IOException {
    // given
    BleutradeBalanceReturn balanceReturn = new BleutradeBalanceReturn();
    balanceReturn.setSuccess(false);
    balanceReturn.setMessage("test message");
    balanceReturn.setResult(expectedBleutradeAccountInfo().get(0));

    when(bleutrade.getBalance(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("AUD")))
        .thenReturn(balanceReturn);

    // when
    accountService.getBleutradeBalance("AUD");

    // then
    fail(
        "BleutradeAccountService should throw ExchangeException when balance request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetBalanceThrowError() throws IOException {
    // given
    when(bleutrade.getBalance(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("AUD")))
        .thenThrow(BleutradeException.class);

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
    balancesReturn.setResult(expectedBleutradeAccountInfo());

    when(bleutrade.getBalances(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class)))
        .thenReturn(balancesReturn);

    final Balance[] expectedAccountBalances = expectedAccountBalances();

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getWallets()).hasSize(1);

    Map<Currency, Balance> balances = accountInfo.getWallet(null).getBalances();
    assertThat(balances).hasSize(3);

    BleutradeAssert.assertEquals(balances.get(Currency.AUD), expectedAccountBalances[0]);
    BleutradeAssert.assertEquals(balances.get(Currency.BTC), expectedAccountBalances[1]);
    BleutradeAssert.assertEquals(
        balances.get(Currency.getInstance("BLEU")), expectedAccountBalances[2]);
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetAccountInfo() throws IOException {
    // given
    BleutradeBalancesReturn balancesReturn = new BleutradeBalancesReturn();
    balancesReturn.setSuccess(false);
    balancesReturn.setMessage("test message");
    balancesReturn.setResult(expectedBleutradeAccountInfo());

    when(bleutrade.getBalances(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class)))
        .thenReturn(balancesReturn);

    // when
    accountService.getAccountInfo();

    // then
    fail(
        "BleutradeAccountService should throw ExchangeException when balances request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetAccountInfoThrowError() throws IOException {
    // given
    when(bleutrade.getBalances(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class)))
        .thenThrow(BleutradeException.class);

    // when
    accountService.getAccountInfo();

    // then
    fail(
        "BleutradeAccountService should throw ExchangeException when balances request throw error");
  }

  @Test()
  public void withdrawFunds() throws IOException {
    when(bleutrade.withdraw(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("BTC"),
            eq(BigDecimal.TEN),
            eq("any address")))
        .thenReturn(new BleutradeWithdrawReturn(true, "message", new String[0]));

    // when
    String message = accountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "any address");

    assertThat(message).isEqualTo("message");
  }

  @Test
  public void shouldRequestDepositAddress() throws IOException {
    // given
    BleutradeDepositAddressReturn addressReturn = new BleutradeDepositAddressReturn();
    addressReturn.setSuccess(true);
    addressReturn.setMessage("test message");
    addressReturn.setResult(BLEUTRADE_DEPOSIT_ADDRESS);

    when(bleutrade.getDepositAddress(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("AUD")))
        .thenReturn(addressReturn);

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

    when(bleutrade.getDepositAddress(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("AUD")))
        .thenReturn(addressReturn);

    // when
    accountService.requestDepositAddress(Currency.AUD);

    // then
    fail(
        "BleutradeAccountService should throw ExchangeException when deposit address request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailRequestDepositAddressError() throws IOException {
    // given
    when(bleutrade.getDepositAddress(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq("AUD")))
        .thenThrow(BleutradeException.class);

    // when
    accountService.requestDepositAddress(Currency.AUD);

    // then
    fail(
        "BleutradeAccountService should throw ExchangeException when deposit address request throw exception");
  }
}

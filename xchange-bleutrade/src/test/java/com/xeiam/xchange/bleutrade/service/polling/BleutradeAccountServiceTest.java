package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalance;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
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

import java.math.BigDecimal;
import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BleutradeAccountServiceTest extends BleutradeServiceTestSupport {

  private BleutradeAccountService accountService;

  @Before
  public void setUp() throws Exception {
    BleutradeExchange exchange = (BleutradeExchange) ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class.getCanonicalName());
    exchange.getExchangeSpecification().setUserName("admin");
    exchange.getExchangeSpecification().setApiKey("publicKey");
    exchange.getExchangeSpecification().setSecretKey("secretKey");

    accountService = new BleutradeAccountService(exchange);
  }

  @Test
  public void constructor() throws Exception {
    assertThat(Whitebox.getInternalState(accountService, "apiKey")).isEqualTo("publicKey");
  }

  @Test
  public void shouldGetBalance() throws Exception {
    // given
    BleutradeBalanceReturn balanceReturn = new BleutradeBalanceReturn();
    balanceReturn.setSuccess(true);
    balanceReturn.setMessage("test message");
    balanceReturn.setResult(createBalance(new BigDecimal("10.00000000"), "AUD", new BigDecimal("40.00000000"), new BigDecimal("30.00000000"), true));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalance(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(balanceReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    BleutradeBalance balance = accountService.getBleutradeBalance("AUD");

    // then
    assertThat(balance.toString()).isEqualTo("BleutradeBalance [Currency=AUD, Balance=40.00000000, Available=10.00000000, Pending=30.00000000, CryptoAddress=null, IsActive=true, additionalProperties={}]");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetBalance() throws Exception {
    // given
    BleutradeBalanceReturn balanceReturn = new BleutradeBalanceReturn();
    balanceReturn.setSuccess(false);
    balanceReturn.setMessage("test message");
    balanceReturn.setResult(createBalance(new BigDecimal("10.00000000"), "AUD", new BigDecimal("40.00000000"), new BigDecimal("30.00000000"), true));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalance(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(balanceReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getBleutradeBalance("AUD");

    // then
    fail("BleutradeAccountService should throw ExchangeException when balance request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetBalanceThrowError() throws Exception {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalance(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getBleutradeBalance("AUD");

    // then
    fail("BleutradeAccountService should throw ExchangeException when balance request throw error");
  }

  @Test
  public void shouldGetAccountInfo() throws Exception {
    // given
    BleutradeBalancesReturn balancesReturn = new BleutradeBalancesReturn();
    balancesReturn.setSuccess(true);
    balancesReturn.setMessage("test message");
    balancesReturn.setResult(Arrays.asList(
        createBalance(new BigDecimal("10.00000000"), "AUD", new BigDecimal("40.00000000"), new BigDecimal("30.00000000"), true),
        createBalance(new BigDecimal("40.00000000"), "BTC", new BigDecimal("100.00000000"), new BigDecimal("60.00000000"), false),
        createBalance(new BigDecimal("70.00000000"), "BLEU", new BigDecimal("160.00000000"), new BigDecimal("90.00000000"), true)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalances(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class))).thenReturn(balancesReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getWallets()).hasSize(1);
    assertThat(accountInfo.getWallet(null).getBalances()).hasSize(3);
    assertThat(accountInfo.getWallet(null).getBalances().get(Currency.AUD).toString())
        .isEqualTo("Balance [currency=AUD, total=40.00000000, available=10.00000000, frozen=30.00000000, borrowed=0, loaned=0, withdrawing=0, depositing=0]");
    assertThat(accountInfo.getWallet(null).getBalances().get(Currency.BTC).toString())
        .isEqualTo("Balance [currency=BTC, total=100.00000000, available=40.00000000, frozen=60.00000000, borrowed=0, loaned=0, withdrawing=0, depositing=0]");
    assertThat(accountInfo.getWallet(null).getBalances().get(new Currency("BLEU")).toString())
        .isEqualTo("Balance [currency=BLEU, total=160.00000000, available=70.00000000, frozen=90.00000000, borrowed=0, loaned=0, withdrawing=0, depositing=0]");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetAccountInfo() throws Exception {
    // given
    BleutradeBalancesReturn balancesReturn = new BleutradeBalancesReturn();
    balancesReturn.setSuccess(false);
    balancesReturn.setMessage("test message");
    balancesReturn.setResult(Arrays.asList(
        createBalance(new BigDecimal("10.00000000"), "AUD", new BigDecimal("40.00000000"), new BigDecimal("30.00000000"), true),
        createBalance(new BigDecimal("40.00000000"), "BTC", new BigDecimal("100.00000000"), new BigDecimal("60.00000000"), false)
    ));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalances(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class))).thenReturn(balancesReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getAccountInfo();

    // then
    fail("BleutradeAccountService should throw ExchangeException when balances request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnGetAccountInfoThrowError() throws Exception {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getBalances(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class))).thenThrow(BleutradeException.class);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.getAccountInfo();

    // then
    fail("BleutradeAccountService should throw ExchangeException when balances request throw error");
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenWithdrawFunds() throws Exception {
    // when
    accountService.withdrawFunds(Currency.BTC, new BigDecimal("1000"), "any address");

    // then
    fail("BleutradeAccountService should throw NotYetImplementedForExchangeException when call withdrawFunds");
  }

  @Test
  public void shouldRequestDepositAddress() throws Exception {
    // given
    BleutradeDepositAddressReturn addressReturn = new BleutradeDepositAddressReturn();
    addressReturn.setSuccess(true);
    addressReturn.setMessage("test message");
    addressReturn.setResult(createDepositAddress("AUD", "Deposit Address Details"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getDepositAddress(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(addressReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    String depositAddress = accountService.requestDepositAddress(Currency.AUD);

    // then
    assertThat(depositAddress).isEqualTo("Deposit Address Details");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulRequestDepositAddress() throws Exception {
    // given
    BleutradeDepositAddressReturn addressReturn = new BleutradeDepositAddressReturn();
    addressReturn.setSuccess(false);
    addressReturn.setMessage("test message");
    addressReturn.setResult(createDepositAddress("AUD", "Deposit Address Details"));

    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getDepositAddress(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD"))).thenReturn(addressReturn);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.requestDepositAddress(Currency.AUD);

    // then
    fail("BleutradeAccountService should throw ExchangeException when deposit address request was unsuccessful");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailRequestDepositAddressError() throws Exception {
    // given
    BleutradeAuthenticated bleutrade = mock(BleutradeAuthenticated.class);
    PowerMockito.when(bleutrade.getDepositAddress(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class), Mockito.any(SynchronizedValueFactory.class), Mockito.eq("AUD")))
        .thenThrow(BleutradeException.class);
    Whitebox.setInternalState(accountService, "bleutrade", bleutrade);

    // when
    accountService.requestDepositAddress(Currency.AUD);

    // then
    fail("BleutradeAccountService should throw ExchangeException when deposit address request throw exception");
  }


}

package org.knowm.xchange.bitmarket.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.knowm.xchange.utils.nonce.LongConstNonceFactory.NONCE_FACTORY;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.BitMarketAssert;
import org.knowm.xchange.bitmarket.BitMarketAuthenticated;
import org.knowm.xchange.bitmarket.BitMarketTestSupport;
import org.knowm.xchange.bitmarket.dto.BitMarketAPILimit;
import org.knowm.xchange.bitmarket.dto.account.BitMarketAccountInfo;
import org.knowm.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import org.knowm.xchange.bitmarket.dto.account.BitMarketBalance;
import org.knowm.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import org.knowm.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(MockitoJUnitRunner.class)
public class BitMarketAccountTest extends BitMarketTestSupport {

  private BitMarketAccountService accountService;

  @Mock private BitMarketAuthenticated bitMarketAuthenticated;

  @Mock private IRestProxyFactory restProxyFactory;

  @Mock private Exchange exchange;

  @Before
  public void setUp() {
    when(exchange.getExchangeSpecification()).thenReturn(createExchangeSpecification());
    when(exchange.getNonceFactory()).thenReturn(NONCE_FACTORY);

    when(restProxyFactory.createProxy(
            eq(BitMarketAuthenticated.class), any(String.class), any(ClientConfig.class)))
        .thenReturn(bitMarketAuthenticated);

    accountService = new BitMarketAccountService(exchange, restProxyFactory);
  }

  @Test
  public void constructor() {
    assertEquals(SPECIFICATION_API_KEY, accountService.apiKey);
  }

  @Test
  public void shouldGetAccountInfo() throws IOException {
    // given
    final Balance[] expectedBalances = expectedBalances();

    BitMarketAccountInfoResponse response =
        new BitMarketAccountInfoResponse(
            true,
            new BitMarketAccountInfo(new BitMarketBalance(createAvailable(), createBlocked())),
            new BitMarketAPILimit(3, 100, 12345000L),
            0,
            null);

    when(bitMarketAuthenticated.info(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class)))
        .thenReturn(response);

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getUsername()).isEqualTo(SPECIFICATION_USERNAME);

    Wallet wallet = accountInfo.getWallet();
    Map<Currency, Balance> balances = wallet.getBalances();

    assertThat(balances).hasSize(3);
    for (int i = 0; i < balances.size(); i++) {
      BitMarketAssert.assertEquals(
          balances.get(expectedBalances[i].getCurrency()), expectedBalances[i]);
    }
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetAccountInfo() throws IOException {
    // given
    BitMarketAccountInfoResponse response =
        new BitMarketAccountInfoResponse(false, null, null, 502, "Invalid message hash");

    when(bitMarketAuthenticated.info(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class)))
        .thenReturn(response);

    // when
    accountService.getAccountInfo();

    // then
    fail(
        "BitMarketAccountService should throw ExchangeException when account info request was unsuccessful");
  }

  @Test
  public void shouldWithdrawFunds() throws IOException {
    // given
    BitMarketWithdrawResponse response =
        new BitMarketWithdrawResponse(
            true, "12345", new BitMarketAPILimit(3, 100, 12345000L), 0, null);

    when(bitMarketAuthenticated.withdraw(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString()),
            eq(BigDecimal.TEN),
            eq("address mock")))
        .thenReturn(response);

    // when
    String withdraw = accountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "address mock");

    // then
    assertThat(withdraw).isEqualTo("12345");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulWithdrawFunds() throws IOException {
    // given
    BitMarketWithdrawResponse response =
        new BitMarketWithdrawResponse(false, null, null, 502, "Invalid message hash");

    when(bitMarketAuthenticated.withdraw(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString()),
            eq(BigDecimal.TEN),
            eq("address mock")))
        .thenReturn(response);

    // when
    accountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "address mock");

    // then
    fail(
        "BitMarketAccountService should throw ExchangeException when withdraw funds request was unsuccessful");
  }

  @Test
  public void shouldRequestDepositAddress() throws IOException {
    // given
    BitMarketDepositResponse response =
        new BitMarketDepositResponse(
            true, "BITMarket", new BitMarketAPILimit(3, 100, 12345000L), 0, null);

    when(bitMarketAuthenticated.deposit(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString())))
        .thenReturn(response);

    // when
    String withdraw = accountService.requestDepositAddress(Currency.BTC);

    // then
    assertThat(withdraw).isEqualTo("BITMarket");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulRequestDepositAddress() throws IOException {
    // given
    BitMarketDepositResponse response =
        new BitMarketDepositResponse(false, null, null, 502, "Invalid message hash");

    when(bitMarketAuthenticated.deposit(
            eq(SPECIFICATION_API_KEY),
            any(ParamsDigest.class),
            any(SynchronizedValueFactory.class),
            eq(Currency.BTC.toString())))
        .thenReturn(response);

    // when
    accountService.requestDepositAddress(Currency.BTC);

    // then
    fail(
        "BitMarketAccountService should throw ExchangeException when deposit funds request was unsuccessful");
  }
}

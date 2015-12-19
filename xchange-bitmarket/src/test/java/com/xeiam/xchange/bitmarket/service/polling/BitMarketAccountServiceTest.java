package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitmarket.BitMarketAuthenticated;
import com.xeiam.xchange.bitmarket.BitMarketExchange;
import com.xeiam.xchange.bitmarket.dto.BitMarketAPILimit;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfo;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketBalance;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.exceptions.ExchangeException;
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

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class BitMarketAccountServiceTest extends BitMarketServiceTestSupport {

  private BitMarketAccountService accountService;

  @Before
  public void setUp() {
    BitMarketExchange exchange = (BitMarketExchange) ExchangeFactory.INSTANCE.createExchange(BitMarketExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName("admin");
    specification.setApiKey("publicKey");
    specification.setSecretKey("secretKey");

    accountService = new BitMarketAccountService(exchange);
  }

  @Test
  public void constructor() {
    assertThat(Whitebox.getInternalState(accountService, "apiKey")).isEqualTo("publicKey");
  }

  @Test
  public void shouldGetAccountInfo() throws IOException {
    // given
    BitMarketAccountInfoResponse response = new BitMarketAccountInfoResponse(
        true,
        new BitMarketAccountInfo(new BitMarketBalance(createAvailable(), createBlocked())),
        new BitMarketAPILimit(3, 100, 12345000L),
        0,
        null
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.info(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(response);
    Whitebox.setInternalState(accountService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getUsername()).isEqualTo("admin");

    Wallet wallet = accountInfo.getWallet();
    assertThat(wallet.getBalances()).hasSize(3);
    assertThat(wallet.getBalance(Currency.BTC).getTotal()).isEqualTo(new BigDecimal("20.00000000"));
    assertThat(wallet.getBalance(Currency.BTC).getFrozen()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(wallet.getBalance(Currency.BTC).getAvailable()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(wallet.getBalance(Currency.AUD).getTotal()).isEqualTo(new BigDecimal("40.00000000"));
    assertThat(wallet.getBalance(Currency.AUD).getFrozen()).isEqualTo(new BigDecimal("20.00000000"));
    assertThat(wallet.getBalance(Currency.AUD).getAvailable()).isEqualTo(new BigDecimal("20.00000000"));
    assertThat(wallet.getBalance(Currency.PLN).getTotal()).isEqualTo(new BigDecimal("60.00000000"));
    assertThat(wallet.getBalance(Currency.PLN).getFrozen()).isEqualTo(new BigDecimal("30.00000000"));
    assertThat(wallet.getBalance(Currency.PLN).getAvailable()).isEqualTo(new BigDecimal("30.00000000"));
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulGetAccountInfo() throws IOException {
    // given
    BitMarketAccountInfoResponse response = new BitMarketAccountInfoResponse(
        false,
        null,
        null,
        502,
        "Invalid message hash"
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.info(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class))).thenReturn(response);
    Whitebox.setInternalState(accountService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    accountService.getAccountInfo();

    // then
    fail("BitMarketAccountService should throw ExchangeException when account info request was unsuccessful");
  }

  @Test
  public void shouldWithdrawFunds() throws IOException {
    // given
    BitMarketWithdrawResponse response = new BitMarketWithdrawResponse(
        true,
        "12345",
        new BitMarketAPILimit(3, 100, 12345000L),
        0,
        null
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.withdraw(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC"),
        Mockito.eq(new BigDecimal("30.00000000")), Mockito.eq("address mock")))
        .thenReturn(response);
    Whitebox.setInternalState(accountService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    String withdraw = accountService.withdrawFunds(Currency.BTC, new BigDecimal("30.00000000"), "address mock");

    // then
    assertThat(withdraw).isEqualTo("12345");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulWithdrawFunds() throws IOException {
    // given
    BitMarketWithdrawResponse response = new BitMarketWithdrawResponse(
        false,
        null,
        null,
        502,
        "Invalid message hash"
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.withdraw(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC"),
        Mockito.eq(new BigDecimal("30.00000000")), Mockito.eq("address mock")))
        .thenReturn(response);
    Whitebox.setInternalState(accountService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    accountService.withdrawFunds(Currency.BTC, new BigDecimal("30.00000000"), "address mock");

    // then
    fail("BitMarketAccountService should throw ExchangeException when withdraw funds request was unsuccessful");
  }

  @Test
  public void shouldRequestDepositAddress() throws IOException {
    // given
    BitMarketDepositResponse response = new BitMarketDepositResponse(
        true,
        "BITMarket",
        new BitMarketAPILimit(3, 100, 12345000L),
        0,
        null
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.deposit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC")))
        .thenReturn(response);
    Whitebox.setInternalState(accountService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    String withdraw = accountService.requestDepositAddress(Currency.BTC);

    // then
    assertThat(withdraw).isEqualTo("BITMarket");
  }

  @Test(expected = ExchangeException.class)
  public void shouldFailOnUnsuccessfulRequestDepositAddress() throws IOException {
    // given
    BitMarketDepositResponse response = new BitMarketDepositResponse(
        false,
        null,
        null,
        502,
        "Invalid message hash"
    );

    BitMarketAuthenticated bitMarketAuthenticated = mock(BitMarketAuthenticated.class);
    PowerMockito.when(bitMarketAuthenticated.deposit(Mockito.eq("publicKey"), Mockito.any(ParamsDigest.class),
        Mockito.any(SynchronizedValueFactory.class), Mockito.eq("BTC")))
        .thenReturn(response);
    Whitebox.setInternalState(accountService, "bitMarketAuthenticated", bitMarketAuthenticated);

    // when
    accountService.requestDepositAddress(Currency.BTC);

    // then
    fail("BitMarketAccountService should throw ExchangeException when deposit funds request was unsuccessful");
  }
}

package org.knowm.xchange.coinbase.v2.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseException;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseAccountData.CoinbaseAccount;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbasePaymentMethodsData.CoinbasePaymentMethod;
import org.knowm.xchange.coinbase.v2.service.CoinbaseAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.utils.AuthUtils;

class AccountServiceIntegration {

  static Exchange exchange;
  static AccountService accountService;

  @BeforeAll
  static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class);
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification());
    accountService = exchange.getAccountService();
  }

  @Test
  void listAccounts() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    CoinbaseAccountService coinbaseService = (CoinbaseAccountService) accountService;
    List<CoinbaseAccount> accounts = coinbaseService.getCoinbaseAccounts();
    assertThat(accounts.size() > 0).isTrue();

    CoinbaseAccount btcAccount =
        accounts.stream()
            .filter(t -> t.getName().equals("BTC Wallet"))
            .collect(Collectors.toList())
            .get(0);
    assertThat(btcAccount.getBalance().getCurrency()).isEqualTo("BTC");
    assertThat(btcAccount.getName()).isEqualTo("BTC Wallet");
  }

  @Test
  void getAccountByCurrency() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    CoinbaseAccountService coinbaseService = (CoinbaseAccountService) accountService;
    CoinbaseAccount btcAccount = coinbaseService.getCoinbaseAccount(Currency.BTC);
    assertThat(btcAccount.getBalance().getCurrency()).isEqualTo("BTC");
    assertThat(btcAccount.getName()).isEqualTo("BTC Wallet");
  }

  @Test
  void createAccount() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    CoinbaseAccountService coinbaseService = (CoinbaseAccountService) accountService;
    try {
      coinbaseService.createCoinbaseAccount("BTC Test");
    } catch (CoinbaseException ex) {
      assertThat(ex.getHttpStatusCode()).isEqualTo(400);
      assertThat(ex.getMessage())
          .isEqualTo("Creation of multiple BTC accounts is not supported (HTTP status code: 400)");
    }
  }

  @Test
  void listPaymentMethods() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    CoinbaseAccountService coinbaseService = (CoinbaseAccountService) accountService;
    List<CoinbasePaymentMethod> methods = coinbaseService.getCoinbasePaymentMethods();
    assertThat(methods.size() > 0).isTrue();
  }
}

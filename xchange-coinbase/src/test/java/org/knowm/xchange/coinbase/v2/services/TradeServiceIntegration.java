package org.knowm.xchange.coinbase.v2.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbase.v2.CoinbaseExchange;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;
import org.knowm.xchange.coinbase.v2.dto.CoinbasePrice;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseBuyData.CoinbaseBuy;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseSellData.CoinbaseSell;
import org.knowm.xchange.coinbase.v2.service.CoinbaseAccountService;
import org.knowm.xchange.coinbase.v2.service.CoinbaseTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.utils.AuthUtils;

class TradeServiceIntegration {

  static Exchange exchange;
  static TradeService tradeService;

  @BeforeAll
  static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class);
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification());
    tradeService = exchange.getTradeService();
  }

  @Test
  void buy() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    Currency currency = Currency.EUR;
    BigDecimal amount = new BigDecimal("10.00");
    BigDecimal total = new BigDecimal("10.00");

    CoinbaseTradeService coinbaseService = (CoinbaseTradeService) tradeService;
    CoinbaseBuy res = coinbaseService.buy(accountId(currency), total, currency, false);
    assertThat(res.getId()).isNotNull();
    assertThat(res.getStatus()).isEqualTo("created");
    assertThat(res.getFee()).isEqualTo(new CoinbasePrice(new BigDecimal("1.00"), Currency.EUR));
    assertThat(res.getAmount()).isEqualTo(new CoinbaseAmount("BTC", new BigDecimal("0.0001")));
    assertThat(res.getSubtotal().getCurrency()).isEqualTo(Currency.EUR);
    assertThat(res.getTotal().getCurrency()).isEqualTo(Currency.EUR);
    assertThat(res.isCommitted()).isFalse();
  }

  @Test
  void sell() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    Currency currency = Currency.BTC;
    BigDecimal amount = new BigDecimal("0.0001");
    BigDecimal total = null;

    CoinbaseTradeService coinbaseService = (CoinbaseTradeService) tradeService;
    CoinbaseSell res = coinbaseService.sell(accountId(currency), total, currency, false);
    assertThat(res.getId()).isNotNull();
    assertThat(res.getStatus()).isEqualTo("created");
    assertThat(res.getFee()).isEqualTo(new CoinbasePrice(new BigDecimal("1.00"), Currency.EUR));
    assertThat(res.getAmount()).isEqualTo(new CoinbaseAmount("BTC", new BigDecimal("0.0001")));
    assertThat(res.getSubtotal().getCurrency()).isEqualTo(Currency.EUR);
    assertThat(res.getTotal().getCurrency()).isEqualTo(Currency.EUR);
    assertThat(res.isCommitted()).isFalse();
  }

  @Test
  void quote() throws Exception {

    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);

    Currency currency = Currency.BTC;
    BigDecimal amount = new BigDecimal("0.0001");
    BigDecimal total = null;

    CoinbaseTradeService coinbaseService = (CoinbaseTradeService) tradeService;
    CoinbaseSell res = coinbaseService.quote(accountId(currency), total, currency);
    assertThat(res.getId()).isNull();
    assertThat(res.getStatus()).isEqualTo("quote");
    assertThat(res.getFee()).isEqualTo(new CoinbasePrice(new BigDecimal("1.00"), Currency.EUR));
    assertThat(res.getAmount()).isEqualTo(new CoinbaseAmount("BTC", new BigDecimal("0.0001")));
    assertThat(res.getSubtotal().getCurrency()).isEqualTo(Currency.EUR);
    assertThat(res.getTotal().getCurrency()).isEqualTo(Currency.EUR);
    assertThat(res.isCommitted()).isFalse();
  }

  private String accountId(Currency currency) throws IOException {
    CoinbaseAccountService accountService = (CoinbaseAccountService) exchange.getAccountService();
    return accountService.getCoinbaseAccount(currency).getId();
  }
}

package org.knowm.xchange.coinbase.v2.services;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
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

public class TradeServiceIntegration {

  static Exchange exchange;
  static TradeService tradeService;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName());
    AuthUtils.setApiAndSecretKey(exchange.getExchangeSpecification());
    tradeService = exchange.getTradeService();
  }

  @Test
  public void buy() throws Exception {

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    Currency currency = Currency.EUR;
    BigDecimal amount = new BigDecimal("10.00");
    BigDecimal total = new BigDecimal("10.00");

    CoinbaseTradeService coinbaseService = (CoinbaseTradeService) tradeService;
    CoinbaseBuy res = coinbaseService.buy(accountId(currency), total, currency, false);
    Assert.assertNotNull(res.getId());
    Assert.assertEquals("created", res.getStatus());
    Assert.assertEquals(new CoinbasePrice(new BigDecimal("1.00"), Currency.EUR), res.getFee());
    Assert.assertEquals(new CoinbaseAmount("BTC", new BigDecimal("0.0001")), res.getAmount());
    Assert.assertEquals(Currency.EUR, res.getSubtotal().getCurrency());
    Assert.assertEquals(Currency.EUR, res.getTotal().getCurrency());
    Assert.assertEquals(false, res.isCommitted());
  }

  @Test
  public void sell() throws Exception {

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    Currency currency = Currency.BTC;
    BigDecimal amount = new BigDecimal("0.0001");
    BigDecimal total = null;

    CoinbaseTradeService coinbaseService = (CoinbaseTradeService) tradeService;
    CoinbaseSell res = coinbaseService.sell(accountId(currency), total, currency, false);
    Assert.assertNotNull(res.getId());
    Assert.assertEquals("created", res.getStatus());
    Assert.assertEquals(new CoinbasePrice(new BigDecimal("1.00"), Currency.EUR), res.getFee());
    Assert.assertEquals(new CoinbaseAmount("BTC", new BigDecimal("0.0001")), res.getAmount());
    Assert.assertEquals(Currency.EUR, res.getSubtotal().getCurrency());
    Assert.assertEquals(Currency.EUR, res.getTotal().getCurrency());
    Assert.assertEquals(false, res.isCommitted());
  }

  @Test
  public void quote() throws Exception {

    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());

    Currency currency = Currency.BTC;
    BigDecimal amount = new BigDecimal("0.0001");
    BigDecimal total = null;

    CoinbaseTradeService coinbaseService = (CoinbaseTradeService) tradeService;
    CoinbaseSell res = coinbaseService.quote(accountId(currency), total, currency);
    Assert.assertNull(res.getId());
    Assert.assertEquals("quote", res.getStatus());
    Assert.assertEquals(new CoinbasePrice(new BigDecimal("1.00"), Currency.EUR), res.getFee());
    Assert.assertEquals(new CoinbaseAmount("BTC", new BigDecimal("0.0001")), res.getAmount());
    Assert.assertEquals(Currency.EUR, res.getSubtotal().getCurrency());
    Assert.assertEquals(Currency.EUR, res.getTotal().getCurrency());
    Assert.assertEquals(false, res.isCommitted());
  }

  private String accountId(Currency currency) throws IOException {
    CoinbaseAccountService accountService = (CoinbaseAccountService) exchange.getAccountService();
    return accountService.getCoinbaseAccount(currency).getId();
  }
}

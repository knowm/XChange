package org.knowm.xchange.test.binance;

import java.util.Map;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.utils.StreamUtils;

public class AccountServiceIntegration {

  static Exchange exchange;
  static AccountService accountService;
  
  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
    accountService = exchange.getAccountService();
  }
  
  @Before
  public void before() {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void testMetaData() throws Exception {
    
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchange.getExchangeMetaData().getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchange.getExchangeMetaData().getCurrencies();

    CurrencyPair currPair = currencyPairs.keySet().stream()
    .filter(cp -> "ETH/BTC".equals(cp.toString())).collect(StreamUtils.singletonCollector());
    Assert.assertNotNull(currPair);
    
    Currency cur = currencies.keySet().stream()
    .filter(c -> Currency.BTC == c).collect(StreamUtils.singletonCollector());
    Assert.assertNotNull(cur);
  }
}

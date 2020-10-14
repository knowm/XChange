package org.knowm.xchange.binance.service.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.utils.StreamUtils;

public class AccountServiceIntegration {

  static Exchange exchange;
  static AccountService accountService;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
    accountService = exchange.getAccountService();
  }

  @Before
  public void before() {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void testMetaData() throws Exception {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs =
        exchange.getExchangeMetaData().getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchange.getExchangeMetaData().getCurrencies();
    CurrencyPair currPair;
    Currency curr;

    currPair =
        currencyPairs.keySet().stream()
            .filter(cp -> "ETH/BTC".equals(cp.toString()))
            .collect(StreamUtils.singletonCollector());
    Assert.assertNotNull(currPair);

    currPair =
        currencyPairs.keySet().stream()
            .filter(cp -> "IOTX/ETH".equals(cp.toString()))
            .collect(StreamUtils.singletonCollector());
    Assert.assertNotNull(currPair);

    curr =
        currencies.keySet().stream()
            .filter(c -> Currency.BTC.equals(c))
            .collect(StreamUtils.singletonCollector());
    Assert.assertNotNull(curr);

    curr =
        currencies.keySet().stream()
            .filter(c -> c.equals(new Currency("IOTX")))
            .collect(StreamUtils.singletonCollector());
    Assert.assertNotNull(curr);
  }

  @Test
  public void testBalances() throws Exception {

    Wallet wallet = accountService.getAccountInfo().getWallet();
    Assert.assertNotNull(wallet);

    Map<Currency, Balance> balances = wallet.getBalances();
    for (Entry<Currency, Balance> entry : balances.entrySet()) {
      Currency curr = entry.getKey();
      Balance bal = entry.getValue();
      if (0 < bal.getAvailable().doubleValue()) {
        Assert.assertSame(curr, bal.getCurrency());
        Assert.assertSame(Currency.getInstance(curr.getCurrencyCode()), bal.getCurrency());
      }
    }
  }

  @Test
  public void testWithdrawalHistory() throws Exception {

    TradeHistoryParams params = accountService.createFundingHistoryParams();
    List<FundingRecord> fundingHistory = accountService.getFundingHistory(params);
    Assert.assertNotNull(fundingHistory);

    for (FundingRecord record : fundingHistory) {
      Assert.assertTrue(record.getAmount().compareTo(BigDecimal.ZERO) > 0);
    }
  }
}

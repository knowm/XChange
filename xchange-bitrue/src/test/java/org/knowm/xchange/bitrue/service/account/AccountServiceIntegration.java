package org.knowm.xchange.bitrue.service.account;

import org.junit.*;
import org.knowm.xchange.bitrue.BitrueExchangeIntegration;
import org.knowm.xchange.bitrue.dto.account.AssetDetail;
import org.knowm.xchange.bitrue.dto.account.TransferHistory;
import org.knowm.xchange.bitrue.service.BitrueAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.utils.StreamUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AccountServiceIntegration extends BitrueExchangeIntegration {

  static BitrueAccountService accountService;

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
    accountService = (BitrueAccountService) exchange.getAccountService();
  }

  @Before
  public void before() {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void testAssetDetail() throws Exception {
    assumeProduction();
    Map<String, AssetDetail> assetDetails =
        ((BitrueAccountService) accountService).getAssetDetails();
    Assert.assertNotNull(assetDetails);
    Assert.assertFalse(assetDetails.isEmpty());
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

    curr =
        currencies.keySet().stream()
            .filter(c -> Currency.BTC.equals(c))
            .collect(StreamUtils.singletonCollector());
    Assert.assertNotNull(curr);

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
  public void testWithdrawal() throws Exception {
    assumeProduction();
    accountService.withdrawFunds(
        Currency.BTC, BigDecimal.ONE, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
  }

  @Test
  public void testWithdrawalHistory() throws Exception {
    assumeProduction();
    TradeHistoryParams params = accountService.createFundingHistoryParams();
    List<FundingRecord> fundingHistory = accountService.getFundingHistory(params);
    Assert.assertNotNull(fundingHistory);

    fundingHistory.forEach(
        record -> {
          Assert.assertTrue(record.getAmount().compareTo(BigDecimal.ZERO) > 0);
        });
  }

  @Test
  public void testDepositAddress() throws Exception {
    assumeProduction();
    String address = accountService.requestDepositAddress(Currency.BTC, (String) null);
    Assert.assertNotNull(address);
  }


}

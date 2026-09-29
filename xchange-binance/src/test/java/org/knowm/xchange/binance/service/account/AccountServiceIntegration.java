package org.knowm.xchange.binance.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.binance.BinanceExchangeIntegration;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.BinanceCurrencyInfo;
import org.knowm.xchange.binance.dto.account.BinanceDeposit;
import org.knowm.xchange.binance.dto.account.TransferHistory;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.utils.StreamUtils;

class AccountServiceIntegration extends BinanceExchangeIntegration {

  static BinanceAccountService accountService;

  @BeforeAll
  static void beforeClass() throws Exception {
    createExchange();
    accountService = (BinanceAccountService) exchange.getAccountService();
  }

  @BeforeEach
  void before() {
    Assumptions.assumeFalse(exchange.getExchangeSpecification().getApiKey() == null);
  }

  @Test
  void assetDetail() throws Exception {
    assumeProduction();
    Map<String, AssetDetail> assetDetails =
        ((BinanceAccountService) accountService).getAssetDetails();
    assertThat(assetDetails).isNotNull();
    assertThat(assetDetails.isEmpty()).isFalse();
  }

  @Test
  void currencyInfos() throws Exception {
    assumeProduction();
    List<BinanceCurrencyInfo> currencyInfos = accountService.currencyInfos();
    assertThat(currencyInfos).isNotEmpty();
  }

  @Test
  void metaData() {

    Map<Instrument, InstrumentMetaData> currencyPairs =
        exchange.getExchangeMetaData().getInstruments();
    Map<Currency, CurrencyMetaData> currencies = exchange.getExchangeMetaData().getCurrencies();
    Instrument currPair;
    Currency curr;

    currPair =
        currencyPairs.keySet().stream()
            .filter(cp -> "ETH/BTC".equals(cp.toString()))
            .collect(StreamUtils.singletonCollector());
    assertThat(currPair).isNotNull();

    curr =
        currencies.keySet().stream()
            .filter(Currency.BTC::equals)
            .collect(StreamUtils.singletonCollector());
    assertThat(curr).isNotNull();

    assertThat(curr).isNotNull();
  }

  @Test
  void balances() throws Exception {

    Wallet wallet = accountService.getAccountInfo().getWallet();
    assertThat(wallet).isNotNull();

    Map<Currency, Balance> balances = wallet.getBalances();
    for (Entry<Currency, Balance> entry : balances.entrySet()) {
      Currency curr = entry.getKey();
      Balance bal = entry.getValue();
      if (0 < bal.getAvailable().doubleValue()) {
        assertThat(bal.getCurrency()).isSameAs(curr);
        assertThat(bal.getCurrency()).isSameAs(Currency.getInstance(curr.getCurrencyCode()));
      }
    }
  }

  @Test
  void withdrawal() throws Exception {
    assumeProduction();
    accountService.withdrawFunds(
        Currency.BTC, BigDecimal.ONE, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
  }

  @Test
  void withdrawalHistory() throws Exception {
    assumeProduction();
    TradeHistoryParams params = accountService.createFundingHistoryParams();
    List<FundingRecord> fundingHistory = accountService.getFundingHistory(params);
    assertThat(fundingHistory).isNotNull();

    fundingHistory.forEach(
        record -> assertThat(record.getAmount().compareTo(BigDecimal.ZERO) > 0).isTrue());
  }

  @Test
  void depositAddress() throws Exception {
    assumeProduction();
    String address = accountService.requestDepositAddress(Currency.BTC, (String) null);
    assertThat(address).isNotNull();
  }

  @Test
  void depositHistory() throws Exception {
    assumeProduction();
    List<BinanceDeposit> depositHistory = accountService.depositHistory("BTC", null, null);
    assertThat(depositHistory).isNotNull();
  }

  @Test
  void transferHistory() throws Exception {
    assumeProduction();
    List<TransferHistory> transferHistory =
        accountService.getTransferHistory("no@email.com", null, null, 1, 10);
    assertThat(transferHistory).isNotNull();
  }
}

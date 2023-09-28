package org.knowm.xchange.bybit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.params.FundingRecordParamAll;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

public class BybitPrivateEndpointsTest {

  static Exchange exchange;

  Instrument instrument = new CurrencyPair("BTC/USDT");

  @BeforeClass
  public static void setUp(){
    Properties properties = new Properties();

    try {
      properties.load(BybitPrivateEndpointsTest.class.getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ExchangeSpecification spec = new BybitExchange().getDefaultExchangeSpecification();

    spec.setApiKey(properties.getProperty("apikey"));
    spec.setSecretKey(properties.getProperty("secret"));
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);

    exchange = ExchangeFactory.INSTANCE.createExchange(BybitExchange.class);
    exchange.applySpecification(spec);

  }

  @Test
  public void testTradeHistory() throws IOException {

    TradeHistoryParamsAll params = new TradeHistoryParamsAll();

    params.setInstrument(instrument);

    exchange
        .getTradeService()
        .getTradeHistory(params)
        .getUserTrades()
        .forEach(
            userTrade -> {
              assertThat(userTrade.getId()).isNotNull();
              assertThat(userTrade.getOrderId()).isNotNull();
              assertThat(userTrade.getOrderUserReference()).isNotNull();
              assertThat(userTrade.getOriginalAmount()).isGreaterThan(BigDecimal.ZERO);
              assertThat(userTrade.getPrice()).isGreaterThan(BigDecimal.ZERO);
              assertThat(userTrade.getFeeAmount()).isGreaterThan(BigDecimal.ZERO);
              assertThat(userTrade.getType()).isNotNull();
              assertThat(userTrade.getFeeCurrency()).isNotNull();
              assertThat(userTrade.getTimestamp()).isNotNull();
            });
  }

  @Test
  public void testAccountInfo() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();

    assertThat(accountInfo.getWallets().size()).isGreaterThan(1);
    accountInfo
        .getWallets()
        .forEach(
            (s, wallet) -> {
              assertThat(BybitAccountType.valueOf(s)).isInstanceOf(BybitAccountType.class);
              assertThat(BybitAccountType.valueOf(wallet.getId())).isInstanceOf(BybitAccountType.class);
              assertThat(wallet.getFeatures()).isNotNull();

              wallet.getBalances().forEach((currency, balance) -> {
                assertThat(currency).isNotNull();
                assertThat(balance.getAvailable()).isNotNull();
                assertThat(balance.getTotal()).isNotNull();
                assertThat(balance.getFrozen()).isNotNull();
                assertThat(balance.getBorrowed()).isNotNull();
            });
        });
    }

  @Test
  public void testInternalTransfers() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .startTime(Date.from(Instant.now().minus(2, ChronoUnit.DAYS)))
        .endTime(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
        .status(FundingRecord.Status.COMPLETE)
        .build();
    List<FundingRecord> records = exchange.getAccountService().getInternalWalletsTransferHistory(paramAll);

    records.forEach(
        internalFundingRecord -> {
          assertThat(internalFundingRecord.getInternalId()).isNotNull();
          assertThat(internalFundingRecord.getDate()).isNotNull();
          assertThat(internalFundingRecord.getAmount()).isNotNull();
          assertThat(internalFundingRecord.getFromWallet()).isNotNull();
          assertThat(internalFundingRecord.getToWallet()).isNotNull();
          assertThat(internalFundingRecord.getStatus()).isNotNull();
          assertThat(internalFundingRecord.getDescription()).isNotNull();
        });
  }

  @Test
  public void testSubAccountTransfers() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .build();
    List<FundingRecord> records = exchange.getAccountService().getSubAccountsTransferHistory(paramAll);

    records.forEach(
        fundingRecord -> {
          assertThat(fundingRecord.getInternalId()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getStatus()).isNotNull();
          assertThat(fundingRecord.getDescription()).isNotNull();
        });
  }

  @Test
  public void testLedger() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .usePagination(true)
        .build();
    List<FundingRecord> records = exchange.getAccountService().getLedger(paramAll);

    records.forEach(
        fundingRecord -> {
          assertThat(fundingRecord.getInternalId()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getStatus()).isNotNull();
          assertThat(fundingRecord.getCurrency()).isNotNull();
        });
  }
}

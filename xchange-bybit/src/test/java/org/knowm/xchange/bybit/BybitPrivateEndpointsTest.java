package org.knowm.xchange.bybit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.params.FundingRecordParamAll;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

@Ignore
public class BybitPrivateEndpointsTest {

  static Exchange exchange;

  Instrument instrument = new CurrencyPair("BTC/USDT");
  static String subAccountId;

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

    subAccountId = properties.getProperty("subAccountId");
  }

  @Test
  public void testTradeHistory() throws IOException {

    TradeHistoryParamsAll params = new TradeHistoryParamsAll();

    params.setInstrument(instrument);

    List<UserTrade> userTrades = exchange
        .getTradeService()
        .getTradeHistory(params)
        .getUserTrades();

    assertThat(userTrades.isEmpty()).isFalse();

    userTrades.forEach(
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
  public void testSubAccountInfo() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getSubAccountInfo(subAccountId);

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
  public void testWalletTransfers() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .status(FundingRecord.Status.COMPLETE)
        .build();
    List<FundingRecord> records = exchange.getAccountService().getWalletTransferHistory(paramAll);

    assertThat(records.isEmpty()).isFalse();

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
    List<FundingRecord> records = exchange.getAccountService().getInternalTransferHistory(paramAll);

    assertThat(records.isEmpty()).isFalse();

    records.forEach(
        fundingRecord -> {
          assertThat(fundingRecord.getInternalId()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getStatus()).isNotNull();
          assertThat(fundingRecord.getToSubAccount()).isNotNull();
          assertThat(fundingRecord.getFromSubAccount()).isNotNull();
          assertThat(fundingRecord.getToWallet()).isNotNull();
          assertThat(fundingRecord.getFromWallet()).isNotNull();
          assertThat(fundingRecord.getDescription()).isNotNull();
        });
  }

  @Test
  public void testAccountDepositHistory() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .build();
    List<FundingRecord> records = exchange.getAccountService().getDepositHistory(paramAll);

    assertThat(records.isEmpty()).isFalse();

    records.forEach(
        fundingRecord -> {
          assertThat(fundingRecord.getInternalId()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getStatus()).isNotNull();
          assertThat(fundingRecord.getAddress()).isNotNull();
          assertThat(fundingRecord.getAddressTag()).isNotNull();
          assertThat(fundingRecord.getFee()).isNotNull();
          assertThat(fundingRecord.getType()).isNotNull();
          assertThat(fundingRecord.getBlockchainTransactionHash()).isNotNull();
        });
  }

  @Test
  public void testAccountWithdrawHistory() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .build();
    List<FundingRecord> records = exchange.getAccountService().getWithdrawHistory(paramAll);

    assertThat(records.isEmpty()).isFalse();

    records.forEach(
        fundingRecord -> {
          assertThat(fundingRecord.getInternalId()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getStatus()).isNotNull();
          assertThat(fundingRecord.getAddress()).isNotNull();
          assertThat(fundingRecord.getAddressTag()).isNotNull();
          assertThat(fundingRecord.getFee()).isNotNull();
          assertThat(fundingRecord.getType()).isNotNull();
          assertThat(fundingRecord.getBlockchainTransactionHash()).isNotNull();
        });
  }
  @Test
  public void testSubAccountDepositHistory() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .subAccountId(subAccountId)
        .build();
    List<FundingRecord> records = exchange.getAccountService().getSubAccountDepositHistory(paramAll);

    records.forEach(
        fundingRecord -> {
          assertThat(fundingRecord.getInternalId()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getStatus()).isNotNull();
          assertThat(fundingRecord.getAddress()).isNotNull();
          assertThat(fundingRecord.getAddressTag()).isNotNull();
          assertThat(fundingRecord.getFee()).isNotNull();
          assertThat(fundingRecord.getType()).isNotNull();
          assertThat(fundingRecord.getBlockchainTransactionHash()).isNotNull();
          assertThat(fundingRecord.getDescription()).isNotNull();
        });
  }

  @Test
  public void testLedger() throws IOException {
    FundingRecordParamAll paramAll = FundingRecordParamAll.builder()
        .usePagination(true)
        .build();
    List<FundingRecord> records = exchange.getAccountService().getLedger(paramAll);

    assertThat(records.isEmpty()).isFalse();

    records.forEach(
        fundingRecord -> {
          assertThat(fundingRecord.getInternalId()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getBalance()).isNotNull();
          assertThat(fundingRecord.getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
          assertThat(fundingRecord.getType()).isNotNull();
          assertThat(fundingRecord.getFee()).isNotNull();
          assertThat(fundingRecord.getCurrency()).isNotNull();
        });
  }
}

package org.knowm.xchange.coinbasepro;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProFundingHistoryParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamInstrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinbaseProPrivateIntegration {

  private final Exchange exchange = CoinbaseProPrivateInit.getCoinbasePrivateInstance();
  private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProPrivateIntegration.class);
  Instrument instrument = new CurrencyPair("BTC/USD");

  /**
   * AccountService tests
   */
  @Test
  public void testAccountInfo() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();

    LOG.info(accountInfo.toString());
    assertThat(accountInfo.getWallet()).isNotNull();
    assertThat(accountInfo.getWallet(WalletFeature.FUNDING)).isNotNull();
    assertThat(accountInfo.getWallets().size()).isEqualTo(1);
  }

  @Test
  public void testFundingHistory() throws IOException {

    CoinbaseProFundingHistoryParams coinbaseFundingHistoryParams = (CoinbaseProFundingHistoryParams) exchange.getAccountService().createFundingHistoryParams();

    coinbaseFundingHistoryParams.setLimit(50);
    coinbaseFundingHistoryParams.setType(Type.WITHDRAW);

    exchange
        .getAccountService()
        .getFundingHistory(coinbaseFundingHistoryParams)
        .forEach(
            fundingRecord -> {
              LOG.info(fundingRecord.toString());
              assertThat(fundingRecord).isNotNull();
              assertThat(fundingRecord.getDate()).isNotNull();
              assertThat(fundingRecord.getAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
              assertThat(fundingRecord.getType()).isInstanceOf(FundingRecord.Type.class);
              assertThat(fundingRecord.getStatus()).isInstanceOf(FundingRecord.Status.class);
              assertThat(fundingRecord.getCurrency()).isNotNull();
            });

    exchange
        .getAccountService()
        .getFundingHistory(new DefaultTradeHistoryParamInstrument())
        .forEach(
            fundingRecord -> {
              LOG.info(fundingRecord.toString());
              assertThat(fundingRecord).isNotNull();
              assertThat(fundingRecord.getDate()).isNotNull();
              assertThat(fundingRecord.getAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
              assertThat(fundingRecord.getType()).isInstanceOf(FundingRecord.Type.class);
              assertThat(fundingRecord.getStatus()).isInstanceOf(FundingRecord.Status.class);
              assertThat(fundingRecord.getCurrency()).isNotNull();
            });
  }

  /**
   * TradeService tests
   */
  @Test
  public void testTradeHistory() throws IOException {
    UserTrades userTrades = exchange
        .getTradeService()
        .getTradeHistory(new DefaultTradeHistoryParamInstrument(instrument));

    userTrades.getUserTrades().forEach(userTrade -> {
      assertThat(userTrade).isNotNull();
      assertThat(userTrade.getId()).isNotNull();
      assertThat(userTrade.getInstrument()).isEqualTo(instrument);
      assertThat(userTrade.getPrice()).isGreaterThan(BigDecimal.ZERO);
      assertThat(userTrade.getOriginalAmount()).isGreaterThan(BigDecimal.ZERO);
      assertThat(userTrade.getFeeAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
      assertThat(userTrade.getTimestamp()).isNotNull();
      assertThat(userTrade.getType()).isNotNull();
      assertThat(userTrade.getOrderId()).isNotNull();
      assertThat(userTrade.getFeeCurrency()).isNotNull();
    });
  }
}

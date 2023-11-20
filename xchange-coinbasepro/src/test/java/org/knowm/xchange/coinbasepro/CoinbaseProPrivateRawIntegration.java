package org.knowm.xchange.coinbasepro;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbasepro.dto.CoinbasePagedResponse;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTransfers;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProFundingHistoryParams;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProLedgerDto.CoinbaseProLedgerTxType;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill;
import org.knowm.xchange.coinbasepro.service.CoinbaseProAccountService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProAccountServiceRaw;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinbaseProPrivateRawIntegration {

  private final Exchange exchange = CoinbaseProPrivateInit.getCoinbasePrivateInstance();
  private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProPrivateIntegration.class);
  Instrument instrument = new CurrencyPair("BTC/USD");

  /**
   * AccountServiceRaw tests
   */
  @Test
  public void testCoinbaseAccountById() throws IOException {
    AccountInfo accountInfo = exchange.getAccountService().getAccountInfo();

    LOG.info(accountInfo.toString());
    assertThat(accountInfo.getWallet()).isNotNull();

    CoinbaseProAccountServiceRaw raw = (CoinbaseProAccountServiceRaw) exchange.getAccountService();

    CoinbaseProAccount[] accounts = raw.getCoinbaseProAccountInfo();
    assertThat(accounts).isNotEmpty();

    CoinbaseProAccount account = raw.getCoinbaseProAccountById(accounts[0].getId());

    LOG.info(account.toString());
    assertThat(account).isNotNull();
    assertThat(account.getId()).isNotNull();
    assertThat(account.getCurrency()).isNotNull();
    assertThat(account.getProfileId()).isNotNull();
    assertThat(account.getBalance()).isInstanceOf(BigDecimal.class);
    assertThat(account.getHold()).isInstanceOf(BigDecimal.class);
    assertThat(account.getAvailable()).isInstanceOf(BigDecimal.class);
    assertThat(account.isTradingEnabled()).isTrue();
  }

  /**
   * TradeServiceRaw tests
   */
  @Test
  public void testTradeHistoryRawData() throws IOException {

    CoinbaseProTradeServiceRaw raw = (CoinbaseProTradeServiceRaw) exchange.getTradeService();
    CoinbasePagedResponse<CoinbaseProFill> rawData = raw.getCoinbaseProFills(null, CoinbaseProAdapters.adaptProductID(instrument), null, null, null, null, null, null);

    rawData.forEach(coinbaseProFill -> {
      assertThat(coinbaseProFill).isNotNull();
      assertThat(coinbaseProFill.getTradeId()).isNotNull();
      assertThat(coinbaseProFill.getProductId()).isNotNull();
      assertThat(coinbaseProFill.getOrderId()).isNotNull();
      assertThat(coinbaseProFill.getUserId()).isNotNull();
      assertThat(coinbaseProFill.getProfileId()).isNotNull();
      assertThat(coinbaseProFill.getLiquidity()).isNotNull();
      assertThat(coinbaseProFill.getPrice()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProFill.getSize()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProFill.getFee()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
      assertThat(coinbaseProFill.getCreatedAt()).isNotNull();
      assertThat(coinbaseProFill.getSide()).isNotNull();
      assertThat(coinbaseProFill.isSettled()).isTrue();
      assertThat(coinbaseProFill.getUsdVolume()).isNotNull();
      assertThat(coinbaseProFill.getMarketType()).isNotNull();
      LOG.info(coinbaseProFill.toString());
    });
  }
}

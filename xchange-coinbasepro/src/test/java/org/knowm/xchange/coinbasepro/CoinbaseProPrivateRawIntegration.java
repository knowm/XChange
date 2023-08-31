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

  @Test
  public void testLedger() throws IOException {
    CoinbaseProAccountService service = (CoinbaseProAccountService) exchange.getAccountService();
    CoinbaseProAccount account = service.getCoinbaseProAccountInfo()[1];

    CoinbaseProFundingHistoryParams params = new CoinbaseProFundingHistoryParams();

    params.setAccountId(account.getId());


    service.getLedgerWithPagination(params).forEach(coinbaseProLedgerDto -> {
      LOG.info(coinbaseProLedgerDto.toString());
      assertThat(coinbaseProLedgerDto).isNotNull();
      assertThat(coinbaseProLedgerDto.getId()).isNotNull();
      assertThat(coinbaseProLedgerDto.getAmount()).isInstanceOf(BigDecimal.class);
      assertThat(coinbaseProLedgerDto.getCreatedAt()).isInstanceOf(Date.class);
      assertThat(coinbaseProLedgerDto.getBalance()).isInstanceOf(BigDecimal.class);
      assertThat(coinbaseProLedgerDto.getType()).isInstanceOf(CoinbaseProLedgerTxType.class);
      assertThat(coinbaseProLedgerDto.getDetails()).isNotNull();
      if(coinbaseProLedgerDto.getType().equals(CoinbaseProLedgerTxType.match)){
        assertThat(coinbaseProLedgerDto.getDetails().getOrderId()).isNotNull();
        assertThat(coinbaseProLedgerDto.getDetails().getProductId()).isInstanceOf(Instrument.class);
        assertThat(coinbaseProLedgerDto.getDetails().getTradeId()).isNotNull();
      }
    });

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

  @Test
  public void testCoinbaseTransfers() throws IOException {
    CoinbaseProAccountServiceRaw raw = (CoinbaseProAccountServiceRaw) exchange.getAccountService();

    String btcAccountId = Arrays.stream(raw.getCoinbaseProAccountInfo()).filter(coinbaseProAccount -> coinbaseProAccount.getCurrency().equals("BTC")).findFirst().get().getId();

    CoinbaseProTransfers transfers =
        raw.getTransfersByAccountId(
            btcAccountId,
            null,
            Date.from(Instant.now()),
            100,
            null);

    assertThat(transfers.getResponseHeaders().size()).isPositive();
    assertThat(transfers.getHeader("Cb-After")).isNotNull();

    transfers.forEach(coinbaseProTransfer -> {
      LOG.info(coinbaseProTransfer.toString());
      assertThat(coinbaseProTransfer).isNotNull();
      assertThat(coinbaseProTransfer.getId()).isNotNull();
      assertThat(coinbaseProTransfer.getType()).isInstanceOf(FundingRecord.Type.class);
      assertThat(coinbaseProTransfer.getCreatedAt()).isInstanceOf(Date.class);
      assertThat(coinbaseProTransfer.getCompletedAt()).isInstanceOf(Date.class);
      if(coinbaseProTransfer.getType().isInflowing()){
        assertThat(coinbaseProTransfer.getProcessedAt()).isInstanceOf(Date.class);
        assertThat(coinbaseProTransfer.getAmount()).isGreaterThan(BigDecimal.ZERO);
        assertThat(coinbaseProTransfer.getCurrency()).isNotNull();
        assertThat(coinbaseProTransfer.getDetails()).isNotNull();
        assertThat(coinbaseProTransfer.getDetails().getCoinbaseAccountId()).isNotNull();
        assertThat(coinbaseProTransfer.getDetails().getCoinbaseTransactionId()).isNotNull();
      }
    });

    CoinbaseProFundingHistoryParams fundingHistoryParams = (CoinbaseProFundingHistoryParams) exchange.getAccountService().createFundingHistoryParams();

    fundingHistoryParams.setLimit(50);
    fundingHistoryParams.setStartTime(Date.from(Instant.now()));
    fundingHistoryParams.setEndTime(Date.from(Instant.now()));
    fundingHistoryParams.setType(FundingRecord.Type.WITHDRAW);

    CoinbaseProAccountService accountService = (CoinbaseProAccountService) exchange.getAccountService();

    accountService.getTransfersWithPagination(fundingHistoryParams).getFundingRecords().forEach(fundingRecord -> {
      LOG.info(fundingRecord.toString());
      assertThat(fundingRecord).isNotNull();
      assertThat(fundingRecord.getAddress()).isNotNull();
      assertThat(fundingRecord.getAddressTag()).isNotNull();
      assertThat(fundingRecord.getDate()).isNotNull();
      assertThat(fundingRecord.getCurrency()).isInstanceOf(Currency.class);
      assertThat(fundingRecord.getAmount()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
      assertThat(fundingRecord.getInternalId()).isNotNull();
      assertThat(fundingRecord.getBlockchainTransactionHash()).isNotNull();
      assertThat(fundingRecord.getType()).isInstanceOf(FundingRecord.Type.class);
      assertThat(fundingRecord.getStatus()).isInstanceOf(FundingRecord.Status.class);
    });
  }
}

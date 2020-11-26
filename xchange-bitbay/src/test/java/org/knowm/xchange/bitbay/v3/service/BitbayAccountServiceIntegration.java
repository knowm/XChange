package org.knowm.xchange.bitbay.v3.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitbay.v3.BitbayExchange;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalanceHistoryEntry;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayBalancesHistoryQuery;
import org.knowm.xchange.dto.account.FundingRecord;

@Slf4j
public class BitbayAccountServiceIntegration {

  private String apiKey = null;
  private String apiSecret = null;
  private BitbayExchange exchange;

  @Before
  public void setUp() {
    assumeTrue(apiKey != null && apiSecret != null);
    exchange = ExchangeFactory.INSTANCE.createExchange(BitbayExchange.class, apiKey, apiSecret);
  }

  @Test
  public void testGetFundingHistoryShouldCorrectMapFields() throws IOException {
    final BitbayAccountService accountService = (BitbayAccountService) exchange.getAccountService();

    final BitbayTradeHistoryParams tradeHistoryParams =
        (BitbayTradeHistoryParams) exchange.getTradeService().createTradeHistoryParams();
    tradeHistoryParams.setLimit(1000);

    final List<FundingRecord> fundingHistory = accountService.getFundingHistory(tradeHistoryParams);
    assertThat(fundingHistory).isNotNull();

    fundingHistory.forEach(
        fundingRecord -> {
          assertThat(fundingRecord).isNotNull();
          assertThat(fundingRecord.getAmount()).isNotNull();
          assertThat(fundingRecord.getDate()).isNotNull();
          assertThat(fundingRecord.getInternalId()).isNotBlank();
          assertThat(fundingRecord.getType()).isNotNull();
          assertThat(fundingRecord.getStatus()).isNotNull();
          assertThat(fundingRecord.getBalance()).isNotNull();
        });
  }

  @Test
  public void testGetBalanceHistoryShouldCorrectMapFields() throws IOException {
    final BitbayAccountService accountService = (BitbayAccountService) exchange.getAccountService();

    final BitbayBalancesHistoryQuery bitbayBalancesHistoryQuery = new BitbayBalancesHistoryQuery();
    bitbayBalancesHistoryQuery.setLimit("1000");

    final List<BitbayBalanceHistoryEntry> bitbayBalanceHistoryEntries =
        accountService.balanceHistory(bitbayBalancesHistoryQuery).getItems();

    assertThat(bitbayBalanceHistoryEntries).isNotNull();

    bitbayBalanceHistoryEntries.forEach(
        balanceHistoryEntry -> {
          assertThat(balanceHistoryEntry).isNotNull();
          assertThat(balanceHistoryEntry.getHistoryId()).isNotNull();
          assertThat(balanceHistoryEntry.getBalance()).isNotNull();
          assertThat(balanceHistoryEntry.getBalance().getCurrency()).isNotBlank();
          assertThat(balanceHistoryEntry.getBalance().getName()).isNotBlank();
          assertThat(balanceHistoryEntry.getBalance().getType()).isNotNull();
          assertThat(balanceHistoryEntry.getBalance().getUserId()).isNotNull();
          assertThat(balanceHistoryEntry.getTime()).isPositive();
          assertThat(balanceHistoryEntry.getType()).isNotBlank();
          assertThat(balanceHistoryEntry.getValue()).isNotNull();
          assertThat(balanceHistoryEntry.getFundsBefore())
              .isNotNull(); // fields in fundsBefore are optional e.g. for type CREATE_BALANCE
          assertThat(balanceHistoryEntry.getFundsAfter()).isNotNull();
          assertThat(balanceHistoryEntry.getFundsAfter().getTotal()).isNotNull();
          assertThat(balanceHistoryEntry.getFundsAfter().getAvailable()).isNotNull();
          assertThat(balanceHistoryEntry.getFundsAfter().getLocked()).isNotNull();
          assertThat(balanceHistoryEntry.getChange()).isNotNull();
          assertThat(balanceHistoryEntry.getChange().getTotal()).isNotNull();
          assertThat(balanceHistoryEntry.getChange().getAvailable()).isNotNull();
          assertThat(balanceHistoryEntry.getChange().getLocked()).isNotNull();
          // detailId is optional field
        });
  }

  @Test
  public void testGetBalanceHistoryWithTimeFilterShouldContainsOnlyEntriesInGivenPeriod()
      throws IOException {
    final ZonedDateTime start =
        LocalDate.parse("2019-01-01").atStartOfDay(ZoneId.of("Europe/Warsaw"));
    final ZonedDateTime end = start.plusYears(1);
    final long startUnixTimestamp = start.toInstant().toEpochMilli();
    final long endUnixTimestamp = end.toInstant().toEpochMilli();

    final BitbayAccountService accountService = (BitbayAccountService) exchange.getAccountService();

    final BitbayBalancesHistoryQuery bitbayBalancesHistoryQuery = new BitbayBalancesHistoryQuery();
    bitbayBalancesHistoryQuery.setLimit("1000");
    bitbayBalancesHistoryQuery.setFromTime(startUnixTimestamp);
    bitbayBalancesHistoryQuery.setToTime(endUnixTimestamp);

    log.debug(
        "start: {}, end: {}, bitbayBalancesHistoryQuery: {}",
        start,
        end,
        bitbayBalancesHistoryQuery);

    final List<BitbayBalanceHistoryEntry> bitbayBalanceHistoryEntries =
        accountService.balanceHistory(bitbayBalancesHistoryQuery).getItems();
    log.debug("bitbayBalanceHistoryEntries size: {}", bitbayBalanceHistoryEntries.size());

    assertThat(bitbayBalanceHistoryEntries).isNotNull();

    bitbayBalanceHistoryEntries.forEach(
        bitbayBalanceHistoryEntry -> {
          assertThat(bitbayBalanceHistoryEntry).isNotNull();
          assertThat(bitbayBalanceHistoryEntry.getTime())
              .isBetween(startUnixTimestamp, endUnixTimestamp);
        });
  }
}

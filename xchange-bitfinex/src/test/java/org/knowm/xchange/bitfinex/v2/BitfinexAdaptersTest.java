package org.knowm.xchange.bitfinex.v2;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v2.dto.account.Movement;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.utils.DateUtils;

public class BitfinexAdaptersTest {

  @Test
  public void adaptCurrencyPairsToTickersParam() {
    List<CurrencyPair> currencyPairs =
        Stream.of(CurrencyPair.BTC_USD, CurrencyPair.ETH_USD, CurrencyPair.ETH_BTC)
            .collect(Collectors.toList());
    String formattedPairs = BitfinexAdapters.adaptCurrencyPairsToTickersParam(currencyPairs);
    Assert.assertEquals("tBTCUSD,tETHUSD,tETHBTC", formattedPairs);
  }

  @Test
  public void adaptCurrencyPair() {
    final List<String> currencyPairStrings =
        Arrays.asList("tBTCUSD", "tETHUSD", "tETHBTC", "tDUSK:USD", "tTKN:USD");
    final List<CurrencyPair> currencyPairs =
        currencyPairStrings.stream()
            .map(BitfinexAdapters::adaptCurrencyPair)
            .collect(Collectors.toList());
    Assert.assertEquals(
        Arrays.asList(
            CurrencyPair.BTC_USD,
            CurrencyPair.ETH_USD,
            CurrencyPair.ETH_BTC,
            new CurrencyPair("DUSK/USD"),
            new CurrencyPair("TKN/USD")),
        currencyPairs);
  }

  @Test
  public void adaptFundingHistory() {
    List<Movement> movements =
        Arrays.asList(
            new Movement(
                "13105603",
                "ETH",
                "",
                null,
                null,
                DateUtils.fromMillisUtc(1569348774000L),
                DateUtils.fromMillisUtc(1569348774000L),
                null,
                null,
                "COMPLETED",
                null,
                null,
                new BigDecimal("0.26300954"),
                new BigDecimal("-0.00135"),
                null,
                null,
                "DESTINATION_ADDRESS",
                null,
                null,
                null,
                "TRANSACTION_ID",
                null),
            new Movement(
                "13293039",
                "ETH",
                "ETHEREUM",
                null,
                null,
                DateUtils.fromMillisUtc(1574175052000L),
                DateUtils.fromMillisUtc(1574181326000L),
                null,
                null,
                "CANCELED",
                null,
                null,
                new BigDecimal("-0.24"),
                new BigDecimal("-0.00135"),
                null,
                null,
                "DESTINATION_ADDRESS",
                null,
                null,
                null,
                "TRANSACTION_ID",
                null));

    List<FundingRecord> fundingRecords = BitfinexAdapters.adaptFundingHistory(movements);
    assertThat(fundingRecords).hasSize(2);
    assertThat(fundingRecords.get(0).getAddress()).isEqualTo("DESTINATION_ADDRESS");
    assertThat(fundingRecords.get(0).getAddressTag()).isNull();
    assertThat(fundingRecords.get(0).getDate()).isEqualTo("2019-09-24T18:12:54Z");
    assertThat(fundingRecords.get(0).getCurrency()).isEqualTo(Currency.ETH);
    assertThat(fundingRecords.get(0).getAmount()).isEqualByComparingTo("0.26300954");
    assertThat(fundingRecords.get(0).getInternalId()).isEqualTo("13105603");
    assertThat(fundingRecords.get(0).getBlockchainTransactionHash()).isEqualTo("TRANSACTION_ID");
    assertThat(fundingRecords.get(0).getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(fundingRecords.get(0).getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
    assertThat(fundingRecords.get(0).getBalance()).isNull();
    assertThat(fundingRecords.get(0).getFee()).isEqualByComparingTo("0.00135");
    assertThat(fundingRecords.get(0).getDescription()).isNull();

    assertThat(fundingRecords.get(1).getAddress()).isEqualTo("DESTINATION_ADDRESS");
    assertThat(fundingRecords.get(1).getAddressTag()).isNull();
    assertThat(fundingRecords.get(1).getDate()).isEqualTo("2019-11-19T16:35:26Z");
    assertThat(fundingRecords.get(1).getCurrency()).isEqualTo(Currency.ETH);
    assertThat(fundingRecords.get(1).getAmount()).isEqualByComparingTo("0.24135");
    assertThat(fundingRecords.get(1).getInternalId()).isEqualTo("13293039");
    assertThat(fundingRecords.get(1).getBlockchainTransactionHash()).isEqualTo("TRANSACTION_ID");
    assertThat(fundingRecords.get(1).getType()).isEqualTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(fundingRecords.get(1).getStatus()).isEqualTo(FundingRecord.Status.CANCELLED);
    assertThat(fundingRecords.get(1).getBalance()).isNull();
    assertThat(fundingRecords.get(1).getFee()).isEqualByComparingTo("0.00135");
    assertThat(fundingRecords.get(1).getDescription()).isNull();
  }
}

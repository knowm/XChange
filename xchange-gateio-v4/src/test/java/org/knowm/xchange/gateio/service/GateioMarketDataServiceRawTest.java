package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyChain;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook.PriceSizeEntry;

public class GateioMarketDataServiceRawTest extends GateioExchangeWiremock {

  GateioMarketDataServiceRaw gateioMarketDataServiceRaw = (GateioMarketDataServiceRaw) exchange.getMarketDataService();


  @Test
  public void getCurrencies_valid() throws IOException {
    List<GateioCurrencyInfo> actual = gateioMarketDataServiceRaw.getGateioCurrencyInfos();

    assertThat(actual).hasSize(5);

    GateioCurrencyInfo actualBtc = actual.get(0);

    GateioCurrencyInfo expectedBtc = GateioCurrencyInfo.builder()
        .currencyWithChain("BTC")
        .delisted(false)
        .withdrawDisabled(false)
        .withdrawDelayed(false)
        .depositDisabled(false)
        .tradeDisabled(false)
        .chain("BTC")
        .build();

    assertThat(actualBtc).usingRecursiveComparison().isEqualTo(expectedBtc);
  }


  @Test
  public void getGateioOrderBook_valid() throws IOException {
    List<PriceSizeEntry> expectedAsks = new ArrayList<>();
    expectedAsks.add(PriceSizeEntry.builder()
        .price(new BigDecimal("200"))
        .size(BigDecimal.ONE)
        .build());
    expectedAsks.add(PriceSizeEntry.builder()
        .price(new BigDecimal("250"))
        .size(BigDecimal.TEN)
        .build());

    List<PriceSizeEntry> expectedBids = new ArrayList<>();
    expectedBids.add(PriceSizeEntry.builder()
        .price(new BigDecimal("150"))
        .size(BigDecimal.ONE)
        .build());
    expectedBids.add(PriceSizeEntry.builder()
        .price(new BigDecimal("100"))
        .size(BigDecimal.TEN)
        .build());

    GateioOrderBook expected = GateioOrderBook.builder()
        .generatedAt(Instant.parse("2023-05-14T22:10:10.493Z"))
        .updatedAt(Instant.parse("2023-05-14T22:10:10.263Z"))
        .asks(expectedAsks)
        .bids(expectedBids)
        .build();

    GateioOrderBook actual = gateioMarketDataServiceRaw.getGateioOrderBook(CurrencyPair.BTC_USDT);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  public void getCurrencyChains_valid_result() throws IOException {
    List<GateioCurrencyChain> expected = Arrays.asList(
        GateioCurrencyChain.builder()
            .chain("BTC")
            .chainNameCN("比特币 BRC20/Ordinals")
            .chainNameEN("Bitcoin BRC20/Ordinals")
            .disabled(false)
            .depositDisabled(false)
            .withdrawDisabled(false)
            .build(),
        GateioCurrencyChain.builder()
            .chain("HT")
            .chainNameCN("Heco")
            .chainNameEN("Heco")
            .disabled(true)
            .depositDisabled(true)
            .withdrawDisabled(true)
            .build()
    );

    List<GateioCurrencyChain> actual = gateioMarketDataServiceRaw.getCurrencyChains(Currency.BTC);

    assertThat(actual).isEqualTo(expected);
  }


  @Test
  void valid_currencypairs_details() throws IOException {
    List<GateioCurrencyPairDetails> details = gateioMarketDataServiceRaw.getCurrencyPairDetails();
    assertThat(details).hasSize(3);
    GateioCurrencyPairDetails expectedChz = GateioCurrencyPairDetails.builder()
        .id("CHZ_USDT")
        .asset("CHZ")
        .quote("USDT")
        .fee(new BigDecimal("0.2"))
        .minQuoteAmount(BigDecimal.ONE)
        .assetScale(2)
        .quoteScale(5)
        .tradeStatus("tradable")
        .startOfSells(Instant.parse("2020-12-24T04:00:00.000Z"))
        .startOfBuys(Instant.parse("2020-12-24T06:00:00.000Z"))
        .build();

    GateioCurrencyPairDetails actualChz = details.get(1);

    assertThat(actualChz).isEqualTo(expectedChz);
  }


  @Test
  void valid_single_currencypair_details() throws IOException {
    GateioCurrencyPairDetails actualChz = gateioMarketDataServiceRaw.getCurrencyPairDetails(new CurrencyPair("CHZ/USDT"));
    GateioCurrencyPairDetails expectedChz = GateioCurrencyPairDetails.builder()
        .id("CHZ_USDT")
        .asset("CHZ")
        .quote("USDT")
        .fee(new BigDecimal("0.2"))
        .minQuoteAmount(BigDecimal.ONE)
        .assetScale(2)
        .quoteScale(5)
        .tradeStatus("tradable")
        .startOfSells(Instant.parse("2020-12-24T04:00:00.000Z"))
        .startOfBuys(Instant.parse("2020-12-24T06:00:00.000Z"))
        .build();

    assertThat(actualChz).isEqualTo(expectedChz);
  }
}
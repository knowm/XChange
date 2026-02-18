package org.knowm.xchange.bitfinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.BitfinexExchangeWiremock;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexCurrencyChain;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexCurrencyPairInfo;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexCurrencyPairInfo.Info;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

class BitfinexMarketDataServiceRawTest extends BitfinexExchangeWiremock {

  BitfinexMarketDataServiceRaw bitfinexMarketDataServiceRaw =
      (BitfinexMarketDataServiceRaw) exchange.getMarketDataService();

  @Test
  void allChains() throws IOException {
    List<BitfinexCurrencyChain> expected =
        Arrays.asList(
            BitfinexCurrencyChain.builder()
                .currency(new Currency("1INCH"))
                .chainName("ETH")
                .build(),
            BitfinexCurrencyChain.builder()
                .currency(new Currency("ATLAS"))
                .chainName("SOL")
                .build());
    List<BitfinexCurrencyChain> actual = bitfinexMarketDataServiceRaw.allChains();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void allCurrencyPairInfos() throws IOException {
    List<BitfinexCurrencyPairInfo> expected =
        Arrays.asList(
            BitfinexCurrencyPairInfo.builder()
                .currencyPair(new CurrencyPair("1INCH/USD"))
                .info(
                    Info.builder()
                        .minAssetAmount(new BigDecimal("2.0"))
                        .maxAssetAmount(new BigDecimal("100000.0"))
                        .build())
                .build(),
            BitfinexCurrencyPairInfo.builder()
                .currencyPair(new CurrencyPair("ADA/USD"))
                .info(
                    Info.builder()
                        .minAssetAmount(new BigDecimal("4.0"))
                        .maxAssetAmount(new BigDecimal("250000.0"))
                        .initialMargin(new BigDecimal("0.3"))
                        .minMargin(new BigDecimal("0.15"))
                        .build())
                .build());
    List<BitfinexCurrencyPairInfo> actual = bitfinexMarketDataServiceRaw.allCurrencyPairInfos();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void allCurrencyPairs() throws IOException {
    List<CurrencyPair> expected =
        Arrays.asList(new CurrencyPair("1INCH/USD"), CurrencyPair.BTC_USD);
    List<CurrencyPair> actual = bitfinexMarketDataServiceRaw.allCurrencyPairs();

    assertThat(actual).isEqualTo(expected);
  }
}

package org.knowm.xchange.bitfinex.v2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.bitfinex.service.BitfinexAdapters;
import org.knowm.xchange.currency.CurrencyPair;

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
}

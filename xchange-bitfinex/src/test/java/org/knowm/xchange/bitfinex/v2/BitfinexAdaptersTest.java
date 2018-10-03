package org.knowm.xchange.bitfinex.v2;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
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
}

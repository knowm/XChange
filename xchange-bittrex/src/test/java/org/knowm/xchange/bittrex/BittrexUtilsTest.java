package org.knowm.xchange.bittrex;

import static org.knowm.xchange.bittrex.BittrexUtils.MARKET_NAME_SEPARATOR;
import static org.knowm.xchange.bittrex.BittrexUtils.toCurrencyPair;
import static org.knowm.xchange.bittrex.BittrexUtils.toPairString;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class BittrexUtilsTest extends TestCase {

  @Test
  public void testToPairString() {
    CurrencyPair market = CurrencyPair.ETH_BTC;
    String oldApiVersionPairString = market.counter + MARKET_NAME_SEPARATOR + market.base;
    String v3ApiVersionPairString = market.base + MARKET_NAME_SEPARATOR + market.counter;

    Assert.assertEquals(v3ApiVersionPairString, toPairString(market, true));
    Assert.assertEquals(oldApiVersionPairString, toPairString(market, false));
  }

  @Test
  public void testToCurrencyPair() {
    CurrencyPair market = CurrencyPair.ETH_BTC;
    String marketOldVersionString = "BTC-ETH";
    String marketV3ApiString = "ETH-BTC";

    Assert.assertEquals(market, toCurrencyPair(marketV3ApiString, true));
    Assert.assertEquals(market, toCurrencyPair(marketOldVersionString, false));
  }
}

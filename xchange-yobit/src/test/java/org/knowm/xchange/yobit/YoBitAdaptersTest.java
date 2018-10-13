package org.knowm.xchange.yobit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class YoBitAdaptersTest {

  @Test
  public void adaptCcyPairsToUrlFormat() {
    CurrencyPair pair0 = CurrencyPair.ADA_BNB;
    CurrencyPair pair1 = CurrencyPair.GNO_ETH;
    CurrencyPair pair2 = CurrencyPair.BTC_BRL;
    List<CurrencyPair> pairs = new ArrayList<>(3);
    pairs.add(pair0);
    pairs.add(pair1);
    pairs.add(pair2);
    Assert.assertEquals("ada_bnb-gno_eth-btc_brl", YoBitAdapters.adaptCcyPairsToUrlFormat(pairs));
  }
}
package org.knowm.xchange.yobit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;

class YoBitAdaptersTest {

  @Test
  void adaptCcyPairsToUrlFormat() {
    CurrencyPair pair0 = CurrencyPair.ADA_BNB;
    CurrencyPair pair1 = CurrencyPair.GNO_ETH;
    CurrencyPair pair2 = CurrencyPair.BTC_BRL;
    List<CurrencyPair> pairs = new ArrayList<>(3);
    pairs.add(pair0);
    pairs.add(pair1);
    pairs.add(pair2);
    assertThat(YoBitAdapters.adaptCcyPairsToUrlFormat(pairs)).isEqualTo("ada_bnb-gno_eth-btc_brl");
  }
}

package org.knowm.xchange.paribu.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by semihunaldi on 27/11/2017 */
public final class ParibuTicker {

  private final BTC_TL btcTL;

  public ParibuTicker(@JsonProperty("BTC_TL") BTC_TL btcTL) {
    this.btcTL = btcTL;
  }

  public BTC_TL getBtcTL() {
    return btcTL;
  }

  @Override
  public String toString() {
    return btcTL.toString();
  }
}

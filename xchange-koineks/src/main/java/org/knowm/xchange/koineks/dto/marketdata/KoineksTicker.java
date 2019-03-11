package org.knowm.xchange.koineks.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by semihunaldi on 27/11/2017 */
public final class KoineksTicker {

  private final KoineksBTCTicker koineksBTCTicker;

  private final KoineksETHTicker koineksETHTicker;

  private final KoineksLTCTicker koineksLTCTicker;

  private final KoineksDASHTicker koineksDASHTicker;

  private final KoineksDOGETicker koineksDOGETicker;

  public KoineksTicker(
      @JsonProperty("BTC") KoineksBTCTicker koineksBTCTicker,
      @JsonProperty("ETH") KoineksETHTicker koineksETHTicker,
      @JsonProperty("LTC") KoineksLTCTicker koineksLTCTicker,
      @JsonProperty("DASH") KoineksDASHTicker koineksDASHTicker,
      @JsonProperty("DOGE") KoineksDOGETicker koineksDOGETicker) {
    this.koineksBTCTicker = koineksBTCTicker;
    this.koineksETHTicker = koineksETHTicker;
    this.koineksLTCTicker = koineksLTCTicker;
    this.koineksDASHTicker = koineksDASHTicker;
    this.koineksDOGETicker = koineksDOGETicker;
  }

  @Override
  public String toString() {
    String builder =
        koineksBTCTicker.toString()
            + koineksETHTicker.toString()
            + koineksLTCTicker.toString()
            + koineksDASHTicker.toString()
            + koineksDOGETicker.toString();
    return builder;
  }

  public KoineksBTCTicker getKoineksBTCTicker() {
    return koineksBTCTicker;
  }

  public KoineksETHTicker getKoineksETHTicker() {
    return koineksETHTicker;
  }

  public KoineksLTCTicker getKoineksLTCTicker() {
    return koineksLTCTicker;
  }

  public KoineksDASHTicker getKoineksDASHTicker() {
    return koineksDASHTicker;
  }

  public KoineksDOGETicker getKoineksDOGETicker() {
    return koineksDOGETicker;
  }
}

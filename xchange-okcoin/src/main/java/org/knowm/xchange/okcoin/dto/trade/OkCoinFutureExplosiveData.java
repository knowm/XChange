package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinFutureExplosiveData {

  private final OkCoinFutureExplosiveDataInfo[] data;

  public OkCoinFutureExplosiveData(@JsonProperty("data") OkCoinFutureExplosiveDataInfo[] data) {
    this.data = data;
  }

  public OkCoinFutureExplosiveDataInfo[] getData() {
    return data;
  }
}

package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** Data object representing Volume from Cryptonit */
public final class CryptonitVolume {

  private final Map<String, BigDecimal> volumes = new HashMap<>();

  public Map<String, BigDecimal> getVolumes() {

    return volumes;
  }

  public BigDecimal getVolume(String currencyKey) {

    return volumes.get(currencyKey);
  }

  @JsonAnySetter
  public void setVolume(String name, BigDecimal volume) {

    this.volumes.put(name, volume);
  }
}

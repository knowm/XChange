package org.knowm.xchange.cointrader.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CointraderRequest {

  @JsonIgnore(true)
  private static long lastNonceTs = 0;

  @JsonProperty
  private String t;

  public CointraderRequest() {
    long nextNonceTs = getCurrentTimestamp();
    if (lastNonceTs == nextNonceTs) {
      nextNonceTs++;
    }
    lastNonceTs = nextNonceTs;
    t = String.format("%tc", new Date(lastNonceTs * 1000));
  }

  /** Current timestamp in seconds */
  private long getCurrentTimestamp() {
    return System.currentTimeMillis() / 1000;
  }
}

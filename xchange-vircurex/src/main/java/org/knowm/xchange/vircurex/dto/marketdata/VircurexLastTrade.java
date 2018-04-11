package org.knowm.xchange.vircurex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Data object representing depth from Vircurex */
public class VircurexLastTrade {

  private final String base;
  private final String alt;
  private final BigDecimal value;
  private final int status;

  /**
   * Constructor
   *
   * @param base
   * @param alt
   * @param value
   * @param status
   */
  public VircurexLastTrade(
      @JsonProperty("base") String base,
      @JsonProperty("alt") String alt,
      @JsonProperty("value") BigDecimal value,
      @JsonProperty("status") int status) {
    this.base = base;
    this.alt = alt;
    this.value = value;
    this.status = status;
  }

  public String getBase() {
    return base;
  }

  public String getAlt() {
    return alt;
  }

  public BigDecimal getValue() {
    return value;
  }

  public int getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "VircurexLastTrade [base="
        + base
        + ", alt="
        + alt
        + ", value="
        + value
        + ", status="
        + status
        + "]";
  }
}

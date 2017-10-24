package org.knowm.xchange.anx.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXLag {

  private final long lag;
  private final double lagDecimal;
  private final String lagText;
  private final int length;

  /**
   * Constructor
   *
   * @param lag
   * @param lagDecimal
   * @param lagText
   * @param length
   */
  public ANXLag(@JsonProperty("lag") long lag, @JsonProperty("lag_secs") double lagDecimal, @JsonProperty("lag_text") String lagText,
      @JsonProperty("length") int length) {

    this.lag = lag;
    this.lagDecimal = lagDecimal;
    this.lagText = lagText;
    this.length = length;
  }

  public long getLag() {

    return lag;
  }

  public double getLagDecimal() {

    return lagDecimal;
  }

  public String getLagText() {

    return lagText;
  }

  public int getLength() {

    return length;
  }

  @Override
  public String toString() {

    return "ANXLag [lagText=" + lagText + "]";
  }

}

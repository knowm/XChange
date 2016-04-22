package org.knowm.xchange.cryptsy.dto.trade;

import java.math.BigDecimal;
import java.text.ParseException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyCalculatedFees {

  private final BigDecimal fee;
  private final BigDecimal net;

  /**
   * Constructor
   * 
   * @param timestamp
   * @param isYourOrder
   * @param orderId
   * @param rate
   * @param amount
   * @param type
   * @param pair
   * @throws ParseException
   */
  public CryptsyCalculatedFees(@JsonProperty("fee") BigDecimal fee, @JsonProperty("net") BigDecimal net) throws ParseException {

    this.fee = fee;
    this.net = net;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getNet() {

    return net;
  }

  @Override
  public String toString() {

    return "CryptsyOpenOrder[" + "Fee='" + fee + "',Net Amount='" + net + "']";
  }
}

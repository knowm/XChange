package org.knowm.xchange.btce.v3.dto.account;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ondřej Novotný
 */
public class BTCEWithdrawInfo {

  private final Map<String, BigDecimal> funds;
  private final int tId;

  private final BigDecimal amountSent;

  /**
   * Constructor
   *
   * @param tId
   * @param amountSent
   * @param funds The funds
   */
  public BTCEWithdrawInfo(@JsonProperty("tId") int tId, @JsonProperty("amountSent") BigDecimal amountSent,
      @JsonProperty("funds") Map<String, BigDecimal> funds) {
    this.funds = funds;
    this.tId = tId;
    this.amountSent = amountSent;
  }

  public int gettId() {
    return tId;
  }

  public BigDecimal getAmountSent() {
    return amountSent;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BTCEAccountInfo[tId={0}, amountSent={1}, funds=''{2}''']", tId, amountSent, funds);
  }

}

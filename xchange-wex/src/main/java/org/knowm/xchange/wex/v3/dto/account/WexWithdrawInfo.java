package org.knowm.xchange.wex.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

/** @author Ondřej Novotný */
public class WexWithdrawInfo {

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
  public WexWithdrawInfo(
      @JsonProperty("tId") int tId,
      @JsonProperty("amountSent") BigDecimal amountSent,
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

    return MessageFormat.format(
        "WexAccountInfo[tId={0}, amountSent={1}, funds=''{2}''']", tId, amountSent, funds);
  }
}

package org.knowm.xchange.okcoin.v3.dto.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexDepositRecord {

  /** token */
  private String currency;
  /** deposit amount */
  private BigDecimal amount;
  /** deposit arrival date */
  private String timestamp;
  /** deposit address */
  private String to;
  /** TXID */
  private String txid;
  /**
   * The status of deposits (0: waiting for confirmation; 1: confirmation account; 2: recharge
   * success);
   */
  private String status;

  public Date getTimestamp() {
    return Date.from(Instant.parse(timestamp));
  }
}

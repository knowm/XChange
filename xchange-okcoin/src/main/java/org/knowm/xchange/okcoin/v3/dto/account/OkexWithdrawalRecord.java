package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexWithdrawalRecord {

  @JsonProperty("withdrawal_id")
  private String withdrawalId;
  /** token */
  private String currency;
  /** amount */
  private BigDecimal amount;
  /** withdrawal request creation date */
  private String timestamp;
  /** remitting address(OKEx account will be shown for OKEx address), ie 13454335123 */
  private String from;
  /** beneficiary address */
  private String to;
  /** tag is required for some tokens. Please leave the field blank if not required */
  private String tag;
  /** payment ID is required for some tokens. Please leave the field blank if it is not required */
  @JsonProperty("payment_id")
  private String paymentId;
  /** the txid for withdrawals (leave it blank for internal transfer) */
  private String txid;
  /** withdrawal fee, ie '0.01000000eth' or '0.00050000btc' */
  private String fee;
  /**
   * -3:pending cancel; -2: cancelled; -1: failed; 0 :pending; 1 :sending; 2:sent; 3 :email
   * confirmation; 4 :manual confirmation; 5:awaiting identity confirmation
   */
  private String status;

  public Date getTimestamp() {
    return Date.from(Instant.parse(timestamp));
  }
}

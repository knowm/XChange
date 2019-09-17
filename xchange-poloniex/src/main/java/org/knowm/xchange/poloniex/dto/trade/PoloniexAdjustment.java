package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class PoloniexAdjustment {

  private final String currency;
  private final BigDecimal amount;
  private final Date timestamp;
  private final String status;
  private final String category;
  private final String reason;
  private final String adjustmentTitle;
  private final String adjustmentShortTitle;
  private final String adjustmentDesc;
  private final String adjustmentShortDesc;
  private final String adjustmentHelp;

  public PoloniexAdjustment(
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("status") String status,
      @JsonProperty("category") String category,
      @JsonProperty("reason") String reason,
      @JsonProperty("adjustmentTitle") String adjustmentTitle,
      @JsonProperty("adjustmentShortTitle") String adjustmentShortTitle,
      @JsonProperty("adjustmentDesc") String adjustmentDesc,
      @JsonProperty("adjustmentShortDesc") String adjustmentShortDesc,
      @JsonProperty("adjustmentHelp") String adjustmentHelp) {
    super();
    this.currency = currency;
    this.amount = amount;
    this.timestamp = new Date(timestamp * 1000);
    this.status = status;
    this.category = category;
    this.reason = reason;
    this.adjustmentTitle = adjustmentTitle;
    this.adjustmentShortTitle = adjustmentShortTitle;
    this.adjustmentDesc = adjustmentDesc;
    this.adjustmentShortDesc = adjustmentShortDesc;
    this.adjustmentHelp = adjustmentHelp;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getStatus() {
    return status;
  }

  public String getCategory() {
    return category;
  }

  public String getReason() {
    return reason;
  }

  public String getAdjustmentTitle() {
    return adjustmentTitle;
  }

  public String getAdjustmentShortTitle() {
    return adjustmentShortTitle;
  }

  public String getAdjustmentDesc() {
    return adjustmentDesc;
  }

  public String getAdjustmentShortDesc() {
    return adjustmentShortDesc;
  }

  public String getAdjustmentHelp() {
    return adjustmentHelp;
  }

  @Override
  public String toString() {
    return "PoloniexAdjustment{"
        + "currency='"
        + currency
        + '\''
        + ", amount="
        + amount
        + ", timestamp="
        + timestamp
        + ", status='"
        + status
        + '\''
        + ", category='"
        + category
        + '\''
        + ", reason='"
        + reason
        + '\''
        + ", adjustmentTitle='"
        + adjustmentTitle
        + '\''
        + ", adjustmentShortTitle='"
        + adjustmentShortTitle
        + '\''
        + ", adjustmentDesc='"
        + adjustmentDesc
        + '\''
        + ", adjustmentShortDesc='"
        + adjustmentShortDesc
        + '\''
        + ", adjustmentHelp='"
        + adjustmentHelp
        + '\''
        + '}';
  }
}

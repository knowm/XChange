package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.bithumb.BithumbAdapters;

public class BithumbTransactionHistory {
  private final Date timestamp;
  private final BithumbAdapters.OrderType type;
  private final BigDecimal unitsTraded;
  private final BigDecimal price;
  private final BigDecimal total;

  public BithumbTransactionHistory(
      @JsonFormat(
              shape = JsonFormat.Shape.STRING,
              pattern = "yyyy-MM-dd HH:mm:ss",
              timezone = "UTC")
          @JsonProperty("transaction_date")
          Date transactionDate,
      @JsonProperty("type") BithumbAdapters.OrderType type,
      @JsonProperty("units_traded") BigDecimal unitsTraded,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("total") BigDecimal total) {
    this.timestamp = transactionDate; // field is renamed to maintain pre-existing API
    this.type = type;
    this.unitsTraded = unitsTraded;
    this.price = price;
    this.total = total;
  }

  public BithumbAdapters.OrderType getType() {
    return type;
  }

  public BigDecimal getUnitsTraded() {
    return unitsTraded;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "BithumbTransactionHistory{"
        + "timestamp='"
        + timestamp
        + '\''
        + ", type='"
        + type
        + '\''
        + ", unitsTraded="
        + unitsTraded
        + ", price="
        + price
        + ", total="
        + total
        + '}';
  }
}

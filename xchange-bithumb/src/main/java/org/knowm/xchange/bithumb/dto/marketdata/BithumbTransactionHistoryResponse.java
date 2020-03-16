package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.time.FastDateFormat;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.dto.BithumbResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class BithumbTransactionHistoryResponse
    extends BithumbResponse<List<BithumbTransactionHistoryResponse.BithumbTransactionHistory>> {
  public static final FastDateFormat TRANSACTION_DATE_FORMAT =
      FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

  public BithumbTransactionHistoryResponse(
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("data") List<BithumbTransactionHistory> data) {
    super(status, message, data);
  }

  public static class BithumbTransactionHistory {
    private final String transactionDate;
    private final BithumbAdapters.OrderType type;
    private final BigDecimal unitsTraded;
    private final BigDecimal price;
    private final BigDecimal total;

    public BithumbTransactionHistory(
        @JsonProperty("transaction_date") String transactionDate,
        @JsonProperty("type") BithumbAdapters.OrderType type,
        @JsonProperty("units_traded") BigDecimal unitsTraded,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("total") BigDecimal total) {
      this.transactionDate = transactionDate;
      this.type = type;
      this.unitsTraded = unitsTraded;
      this.price = price;
      this.total = total;
    }

    public String getTransactionDate() {
      return transactionDate;
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
      try {
        return TRANSACTION_DATE_FORMAT.parse(transactionDate);
      } catch (ParseException e) {
        return null;
      }
    }

    @Override
    public String toString() {
      return "BithumbTransactionHistory{"
          + "transactionDate='"
          + transactionDate
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
}

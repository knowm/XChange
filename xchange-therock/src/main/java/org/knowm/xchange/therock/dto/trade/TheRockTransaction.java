package org.knowm.xchange.therock.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TheRockTransaction {

  private final long id;
  private final Date date;
  private final String type;
  private final BigDecimal price;
  private final String currency;
  private final Long orderId;
  private final Long tradeId;
  private final TransferDetail transferDetail;

  public TheRockTransaction(@JsonProperty("id") long id, @JsonProperty("date") Date date, @JsonProperty("type") String type,
      @JsonProperty("price") BigDecimal price, @JsonProperty("currency") String currency, @JsonProperty("order_id") Long orderId,
      @JsonProperty("trade_id") Long tradeId, @JsonProperty("transfer_detail") TransferDetail transferDetail) {
    this.id = id;
    this.date = date;
    this.type = type;
    this.price = price;
    this.currency = currency;
    this.orderId = orderId;
    this.tradeId = tradeId;
    this.transferDetail = transferDetail;
  }

  public long getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getCurrency() {
    return currency;
  }

  public long getOrderId() {
    return orderId;
  }

  public Long getTradeId() {
    return tradeId;
  }

  public TransferDetail getTransferDetail() {
    return transferDetail;
  }

  @Override
  public String toString() {
    return "TheRockTransaction [id=" + id + ", date=" + date + ", type=" + type + ", price=" + price + ", currency=" + currency + ", orderId="
        + orderId + ", tradeId=" + tradeId + ", transferDetail=" + transferDetail + "]";
  }

  public static class TransferDetail {
    private final String method;
    private final String id;
    private final String recipient;
    private final int confirmations;

    public TransferDetail(@JsonProperty("method") String method, @JsonProperty("id") String id, @JsonProperty("recipient") String recipient,
        @JsonProperty("confirmations") int confirmations) {
      this.method = method;
      this.id = id;
      this.recipient = recipient;
      this.confirmations = confirmations;
    }

    @Override
    public String toString() {
      return "TransferDetail [method=" + method + ", id=" + id + ", recipient=" + recipient + ", confirmations=" + confirmations + "]";
    }

    public String getMethod() {
      return method;
    }

    public String getId() {
      return id;
    }

    public String getRecipient() {
      return recipient;
    }

    public int getConfirmations() {
      return confirmations;
    }
  }
}

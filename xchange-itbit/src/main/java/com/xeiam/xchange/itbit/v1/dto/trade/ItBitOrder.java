package com.xeiam.xchange.itbit.v1.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItBitOrder {

  private final String id;
  private final String walletId;
  private final String side;
  private final String instrument;
  private final String currency;
  private final String type;
  private final String status;
  private final Date createdTime;
  private final BigDecimal amountFilled;
  private final BigDecimal price;
  private final BigDecimal amount;

  public ItBitOrder(@JsonProperty("id") String id, @JsonProperty("walletId") String walletId, @JsonProperty("side") String side, @JsonProperty("instrument") String instrument,
      @JsonProperty("currency") String currency, @JsonProperty("type") String type, @JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amountFilled") BigDecimal amountFilled, @JsonProperty("createdTime") Date createdTime, @JsonProperty("status") String status) {

    this.id = id;
    this.walletId = walletId;
    this.side = side;
    this.instrument = instrument;
    this.currency = currency;
    this.type = type;
    this.amount = amount;
    this.price = price;
    this.amountFilled = amountFilled;
    this.createdTime = createdTime;
    this.status = status;
  }

  public String getId() {

    return id;
  }

  public String getWalletId() {

    return walletId;
  }

  public String getSide() {

    return side;
  }

  public String getInstrument() {

    return instrument;
  }

  public String getCurrency() {

    return currency;
  }

  public String getType() {

    return type;
  }

  public String getStatus() {

    return status;
  }

  public Date getCreatedTime() {

    return createdTime;
  }

  public BigDecimal getAmountFilled() {

    return amountFilled;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitAccountOrder [id=");
    builder.append(id);
    builder.append(", walletId=");
    builder.append(walletId);
    builder.append(", side=");
    builder.append(side);
    builder.append(", instrument=");
    builder.append(instrument);
    builder.append(", currency=");
    builder.append(currency);
    builder.append(", type=");
    builder.append(type);
    builder.append(", status=");
    builder.append(status);
    builder.append(", createdTime=");
    builder.append(createdTime);
    builder.append(", amountFilled=");
    builder.append(amountFilled);
    builder.append(", price=");
    builder.append(price);
    builder.append(", amount=");
    builder.append(amount);
    builder.append("]");
    return builder.toString();
  }

}

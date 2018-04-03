package org.knowm.xchange.itbit.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ItBitWithdrawalResponse {

  private final String id;
  private final String currency;
  private final BigDecimal amount;
  private final String address;
  private final String completionDate;

  public ItBitWithdrawalResponse(
      @JsonProperty("id") String id,
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("address") String address,
      @JsonProperty("completionDate") String completionDate) {

    this.id = id;
    this.currency = currency;
    this.amount = amount;
    this.address = address;
    this.completionDate = completionDate;
  }

  public String getId() {

    return id;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getAddress() {

    return address;
  }

  public String getCompletionDate() {

    return completionDate;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ItBitWithdrawalResponse [id=");
    builder.append(id);
    builder.append(", currency=");
    builder.append(currency);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", address=");
    builder.append(address);
    builder.append(", completionDate=");
    builder.append(completionDate);
    builder.append("]");
    return builder.toString();
  }
}

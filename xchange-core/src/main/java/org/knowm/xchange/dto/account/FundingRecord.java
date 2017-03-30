package org.knowm.xchange.dto.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * DTO representing funding information
 * </p>
 * <p>
 * Funding information contains the detail of deposit/withdrawal transaction for a specific currency
 * </p>
 */
public final class FundingRecord {
  private final String address;
  private final Date date;
  private final String currency;
  private final BigDecimal amount;
  private final String id;
  private final String description;
  private final Type type;
  private final String status;
  private final BigDecimal balance;
  private final BigDecimal fee;

  public FundingRecord(final String address, final Date date, final String currency, final BigDecimal amount, final String id,
                       final Type type, final String status, final BigDecimal balance, final BigDecimal fee,
                       final String description){
    this.address = address;
    this.date = date;
    this.currency = currency;
    this.amount = amount;
    this.id = id;
    this.type = type;
    this.status = status;
    this.balance = balance;
    this.fee = fee;
    this.description = description;
  }

  public String getAddress() {
    return address;
  }

  public Date getDate() {
    return date;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getId() {
    return id;
  }

  public Type getType() {
    return type;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "FundingRecord{" +
            "address='" + address + '\'' +
            ", date=" + date +
            ", currency='" + currency + '\'' +
            ", amount=" + amount +
            ", id='" + id + '\'' +
            ", description='" + description + '\'' +
            ", type='" + type + '\'' +
            ", status='" + status + '\'' +
            ", balance=" + balance +
            ", fee=" + fee +
            '}';
  }

  public enum Type {
    WITHDRAWAL, DEPOSIT;

    private static final Map<String, Type> fromString = new HashMap<String, Type>();

    static {
      for (Type type : values())
        fromString.put(type.toString(), type);
    }

    public static Type fromString(String ledgerTypeString) {
      return fromString.get(ledgerTypeString.toUpperCase());
    }
  }

}

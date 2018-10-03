package org.knowm.xchange.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.dto.Order.OrderType;

/** Data object representing an order for a loan */
public class LoanOrder implements Serializable {

  /** Order type i.e. bid or ask */
  private final OrderType type;

  /** The loan currency */
  private final String currency;

  /** Amount to be ordered / amount that was ordered */
  private final BigDecimal originalAmount;

  /** Duration of loan in days */
  private final int dayPeriod;

  /** An identifier that uniquely identifies the order */
  private final String id;

  /** The timestamp on the order according to the exchange's server, null if not provided */
  private final Date timestamp;

  /**
   * Constructor
   *
   * @param type Order type i.e. bid or ask
   * @param currency The loan currency
   * @param originalAmount Amount to be ordered / amount that was ordered
   * @param dayPeriod Duration of loan in days
   * @param id An identifier that uniquely identifies the order
   * @param timestamp The timestamp on the order according to the exchange's server, null if not
   *     provided
   */
  public LoanOrder(
      OrderType type,
      String currency,
      BigDecimal originalAmount,
      int dayPeriod,
      String id,
      Date timestamp) {

    this.type = type;
    this.currency = currency;
    this.originalAmount = originalAmount;
    this.dayPeriod = dayPeriod;
    this.id = id;
    this.timestamp = timestamp;
  }

  public OrderType getType() {

    return type;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getOriginalAmount() {

    return originalAmount;
  }

  public int getDayPeriod() {

    return dayPeriod;
  }

  public String getId() {

    return id;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "LoanOrder [type="
        + type
        + ", currency="
        + currency
        + ", originalAmount="
        + originalAmount
        + ", dayPeriod="
        + dayPeriod
        + ", id="
        + id
        + ", timestamp="
        + timestamp
        + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + dayPeriod;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    result = prime * result + ((originalAmount == null) ? 0 : originalAmount.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LoanOrder other = (LoanOrder) obj;
    if (currency == null) {
      if (other.currency != null) {
        return false;
      }
    } else if (!currency.equals(other.currency)) {
      return false;
    }
    if (dayPeriod != other.dayPeriod) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (timestamp == null) {
      if (other.timestamp != null) {
        return false;
      }
    } else if (!timestamp.equals(other.timestamp)) {
      return false;
    }
    if (originalAmount == null) {
      if (other.originalAmount != null) {
        return false;
      }
    } else if (!originalAmount.equals(other.originalAmount)) {
      return false;
    }
    return type == other.type;
  }
}

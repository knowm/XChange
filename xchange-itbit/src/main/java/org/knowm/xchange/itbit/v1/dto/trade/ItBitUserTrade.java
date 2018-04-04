package org.knowm.xchange.itbit.v1.dto.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.itbit.v1.ItBitDateDeserializer;

public class ItBitUserTrade {
  private String orderId;

  @JsonDeserialize(using = ItBitDateDeserializer.class)
  private Date timestamp;

  private String instrument;
  private Direction direction;
  private String currency1;
  private BigDecimal currency1Amount;
  private String currency2;
  private BigDecimal currency2Amount;
  private BigDecimal rate;
  private BigDecimal commissionPaid;
  private String commissionCurrency;
  private BigDecimal rebatesApplied;
  private String rebateCurrency;

  public String getOrderId() {
    return orderId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getInstrument() {
    return instrument;
  }

  public Direction getDirection() {
    return direction;
  }

  public String getCurrency1() {
    return currency1;
  }

  public BigDecimal getCurrency1Amount() {
    return currency1Amount;
  }

  public String getCurrency2() {
    return currency2;
  }

  public BigDecimal getCurrency2Amount() {
    return currency2Amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getCommissionPaid() {
    return commissionPaid;
  }

  public String getCommissionCurrency() {
    return commissionCurrency;
  }

  public BigDecimal getRebatesApplied() {
    return rebatesApplied;
  }

  public String getRebateCurrency() {
    return rebateCurrency;
  }

  public enum Direction {
    buy,
    sell
  }
}

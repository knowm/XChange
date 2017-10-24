package org.knowm.xchange.taurus.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.utils.jackson.SqlUtcTimeDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Matija Mazi
 */
public final class TaurusUserTransaction {

  private final Date datetime;
  private final long id;
  private final String orderId;
  private final TransactionType type;
  /**
   * CAD amount, negative -> BID, positive -> ASK
   */
  private final BigDecimal cad;
  private final BigDecimal btc;
  private final BigDecimal price;
  /**
   * fee seems to be in BTC for buys, CAD for sells
   */
  private final BigDecimal fee;

  public TaurusUserTransaction(@JsonProperty("datetime") @JsonDeserialize(using = SqlUtcTimeDeserializer.class) Date datetime,
      @JsonProperty("id") long id, @JsonProperty("order_id") String orderId, @JsonProperty("type") TransactionType type,
      @JsonProperty("cad") BigDecimal cad, @JsonProperty("btc") BigDecimal btc, @JsonProperty("rate") BigDecimal price,
      @JsonProperty("fee") BigDecimal fee) {
    this.datetime = datetime;
    this.id = id;
    this.orderId = orderId;
    this.type = type;
    this.cad = cad;
    this.btc = btc;
    this.price = price;
    this.fee = fee;
  }

  public Date getDatetime() {
    return datetime;
  }

  public long getId() {
    return id;
  }

  public String getOrderId() {
    return orderId;
  }

  public TransactionType getType() {
    return type;
  }

  public boolean isDeposit() {
    return type == TransactionType.deposit;
  }

  public boolean isWithdrawal() {
    return type == TransactionType.withdrawal;
  }

  public boolean isMarketTrade() {
    return type == TransactionType.trade;
  }

  public BigDecimal getCad() {
    return cad;
  }

  public BigDecimal getBtc() {
    return btc;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return String.format("TaurusUserTransaction{datetime=%s, id=%d, type=%s, cad=%s, btc=%s, fee=%s}", datetime, id, type, cad, btc, fee);
  }

  public enum TransactionType {
    /* reseved so parsing won 't break in case Taurus adds new types */
    deposit, withdrawal, trade, type3_reserved, type4_reserved, type5_reseverd, type6_reseved, type7_reserved
  }
}

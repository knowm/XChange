package org.knowm.xchange.clevercoin.dto.trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Karsten Nilsen
 */
public final class CleverCoinUserTransaction {

  private final long datetime;
  private final long transactionId;
  private final long orderId;
  private final String type;
  /** EUR amount, negative -> BID, positive -> ASK */
  private final BigDecimal eur;
  private final BigDecimal btc;
  /** price, has the reciprocal sign compared to 'eur' value */
  private final BigDecimal btc_eur;

  /**
   * Constructor
   * 
   * @param datetime
   * @param transactionId
   * @param orderId
   * @param type
   * @param eur
   * @param btc
   * @param btc_eur
   */
  public CleverCoinUserTransaction(@JsonProperty("time") long datetime, @JsonProperty("transactionId") long transactionId,
      @JsonProperty("order") long orderId, @JsonProperty("type") String type, @JsonProperty("volume") BigDecimal btc,
      @JsonProperty("price") BigDecimal btc_eur) {

    this.datetime = datetime;
    this.transactionId = transactionId;
    this.orderId = orderId;
    this.type = type;
    MathContext mc = new MathContext(10);
    this.eur = btc.multiply(btc_eur, mc);
    this.btc = btc;
    this.btc_eur = btc_eur;
  }

  public long getDatetime() {

    return datetime;
  }

  public long getId() {

    return transactionId;
  }

  public long getOrderId() {

    return orderId;
  }

  public String getType() {

    return type;
  }

  public boolean isDeposit() {

    return type == "deposit";
  }

  public boolean isWithdrawal() {

    return type == "withdrawal";
  }

  public boolean isMarketTrade() {

    return (type == "sell" || type == "buy");
  }

  public BigDecimal getEur() {

    return eur;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  @JsonIgnore
  public Date getTime() {

    return new Date((long) (this.getDatetime() * 1000L));
  }

  public BigDecimal getPrice() {

    return btc_eur;
  }

  @Override
  public String toString() {

    return String.format("UserTransaction{datetime=%s, transactionId=%d, type=%s, eur=%s, btc=%s}", datetime, transactionId, type, eur, btc);
  }
}

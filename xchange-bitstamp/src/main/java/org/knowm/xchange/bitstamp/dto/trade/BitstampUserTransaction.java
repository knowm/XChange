package org.knowm.xchange.bitstamp.dto.trade;

import java.math.BigDecimal;

import org.knowm.xchange.bitstamp.util.BitstampTransactionTypeDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Matija Mazi
 */
public final class BitstampUserTransaction {

  private final String datetime;
  private final long id;
  private final long order_id;
  private final TransactionType type;
  /** USD amount, negative -> BID, positive -> ASK */
  private final BigDecimal usd;
  /** EUR amount, negative -> BID, positive -> ASK */
  private final BigDecimal eur;
  private final BigDecimal btc;
  /** price, has the reciprocal sign compared to 'usd' value */
  private final BigDecimal btc_usd;
  /** price, has the reciprocal sign compared to 'eur' value */
  private final BigDecimal btc_eur;
  private final BigDecimal fee;

  /**
   * Constructor
   * 
   * @param datetime
   * @param id
   * @param order_id
   * @param type
   * @param usd
   * @param btc
   * @param btc_usd
   * @param fee
   */
  public BitstampUserTransaction(@JsonProperty("datetime") String datetime, @JsonProperty("id") long id, @JsonProperty("order_id") long order_id,
      @JsonProperty("type") @JsonDeserialize(using = BitstampTransactionTypeDeserializer.class) TransactionType type,
      @JsonProperty("usd") BigDecimal usd, @JsonProperty("eur") BigDecimal eur, @JsonProperty("btc") BigDecimal btc,
      @JsonProperty("btc_usd") BigDecimal btc_usd, @JsonProperty("btc_eur") BigDecimal btc_eur, @JsonProperty("fee") BigDecimal fee) {

    this.datetime = datetime;
    this.id = id;
    this.order_id = order_id;
    this.type = type;
    this.usd = usd;
    this.eur = eur;
    this.btc = btc;
    this.btc_usd = btc_usd;
    this.btc_eur = btc_eur;
    this.fee = fee;
  }

  public String getDatetime() {

    return datetime;
  }

  public long getId() {

    return id;
  }

  public long getOrderId() {

    return order_id;
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

  public BigDecimal getUsd() {

    return usd;
  }

  public BigDecimal getEur() {
    return eur;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getCounterAmount() {
    return isUsd() ? usd : eur;
  }

  public BigDecimal getPrice() {

    return isUsd() ? btc_usd : btc_eur;
  }

  public String getCounterCurrency() {
    return isUsd() ? "USD" : "EUR";
  }

  private boolean isUsd() {
    return usd != null && usd.compareTo(BigDecimal.ZERO) != 0;
  }

  public BigDecimal getFee() {

    return fee;
  }

  @Override
  public String toString() {

    return String.format("UserTransaction{datetime=%s, id=%d, type=%s, usd=%s, btc=%s, fee=%s}", datetime, id, type, usd, btc, fee);
  }

  public enum TransactionType {
    deposit, withdrawal, trade, rippleWithdrawal, rippleDeposit, type5_reseverd, type6_reseved, type7_reserved /*
                                                                                                                * reseved so parsing won 't break in
                                                                                                                * case Bitstamp adds new types
                                                                                                                */
  }
}

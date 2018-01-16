package org.knowm.xchange.bitstamp.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.bitstamp.BitstampUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitstampOrderTransaction {

  private final Date datetime;
  private final long tid;
  private final BitstampUserTransaction.TransactionType type;
  private final BigDecimal usd;
  private final BigDecimal btc;
  private final BigDecimal ltc;
  private final BigDecimal eth;
  private final BigDecimal eur;
  private final BigDecimal xrp;
  private final BigDecimal bch;
  private final BigDecimal price;
  private final BigDecimal fee;

  /**
   * Constructor
   *
   * @param datetime date and time of transaction
   * @param tid transaction id
   * @param type transaction type
   * @param usd settled amoun
   * @param btc traded amount
   * @param price transaction rate
   * @param fee transaction fee
   */
  public BitstampOrderTransaction(@JsonProperty("datetime") String datetime, @JsonProperty("tid") long tid,
      @JsonProperty("type") BitstampUserTransaction.TransactionType type,
      @JsonProperty("usd") BigDecimal usd, @JsonProperty("btc") BigDecimal btc,
      @JsonProperty("ltc") BigDecimal ltc, @JsonProperty("eth") BigDecimal eth,
      @JsonProperty("eur") BigDecimal eur, @JsonProperty("xrp") BigDecimal xrp,
      @JsonProperty("bch") BigDecimal bch,
      @JsonProperty("price") BigDecimal price, @JsonProperty("fee") BigDecimal fee) {

    this.datetime = BitstampUtils.parseDate(datetime);
    ;
    this.tid = tid;
    this.type = type;
    this.usd = usd;
    this.btc = btc;
    this.price = price;
    this.fee = fee;
    this.ltc = ltc;
    this.eth = eth;
    this.eur = eur;
    this.xrp = xrp;
    this.bch = bch;

  }

  public Date getDatetime() {

    return datetime;
  }

  public long getTid() {

    return tid;
  }

  public BitstampUserTransaction.TransactionType getType() {

    return type;
  }

  public BigDecimal getUsd() {

    return usd;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getLtc() {
    return ltc;
  }

  public BigDecimal getEth() {
    return eth;
  }

  public BigDecimal getEur() {
    return eur;
  }

  public BigDecimal getXrp() {
    return xrp;
  }

  public BigDecimal getBch() {
    return bch;
  }
}

package org.knowm.xchange.bitstamp.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitstamp.BitstampUtils;

public class BitstampOrderTransaction {

  private final Date datetime;
  private final long tid;
  private final BitstampUserTransaction.TransactionType type;
  private final Map<String, BigDecimal> amounts = new HashMap<>();
  private final BigDecimal price;
  private final BigDecimal fee;

  /**
   * Constructor
   *
   * @param datetime date and time of transaction
   * @param tid transaction id
   * @param type transaction type
   * @param price transaction rate
   * @param fee transaction fee
   */
  public BitstampOrderTransaction(
      @JsonProperty("datetime") String datetime,
      @JsonProperty("tid") long tid,
      @JsonProperty("type") BitstampUserTransaction.TransactionType type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("fee") BigDecimal fee) {

    this.datetime = BitstampUtils.parseDate(datetime);
    this.tid = tid;
    this.type = type;
    this.price = price;
    this.fee = fee;
  }

  @JsonAnySetter
  public void setDynamicProperty(String name, Object value) {
    if (value != null) {
      amounts.put(name, new BigDecimal(value.toString()));
    }
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

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getAmount(String token) {
    return amounts.get(token);
  }

  public Map<String, BigDecimal> getAmounts() {
    return amounts;
  }
}

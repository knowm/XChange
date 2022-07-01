package org.knowm.xchange.btcturk.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;

/** @author mertguner */
public class BTCTurkExchangeResult {

  private final String id;
  private final Date datetime;
  private final BTCTurkOrderTypes type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String pairsymbol;

  public BTCTurkExchangeResult(
      @JsonProperty("id") String id,
      @JsonProperty("datetime") Date datetime,
      @JsonProperty("type") BTCTurkOrderTypes type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("pairsymbol") String pairsymbol) {
    this.id = id;
    this.datetime = datetime;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.pairsymbol = pairsymbol;
  }

  public String getId() {
    return id;
  }

  public Date getDatetime() {
    return datetime;
  }

  public BTCTurkOrderTypes getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getPairsymbol() {
    return pairsymbol;
  }

  @Override
  public String toString() {
    return "BTCTurkExchangeResult [id="
        + id
        + ", datetime="
        + datetime
        + ", type="
        + type
        + ", price="
        + price
        + ", amount="
        + amount
        + ", pairsymbol="
        + pairsymbol
        + "]";
  }
}

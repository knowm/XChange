package org.knowm.xchange.huobi.dto.marketdata.futures;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcFuturesTrade {

  private final String id;
  private final String time;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String type;
  private final Date date;

  public BitVcFuturesTrade(@JsonProperty("tid") String id, @JsonProperty("time") final String time, @JsonProperty("price") final BigDecimal price,
      @JsonProperty("amount") final BigDecimal amount, @JsonProperty("type") final String type, @JsonProperty("date") Date date) {

    this.time = time;
    this.price = price;
    this.amount = amount;
    this.type = type;
    this.id = id;
    this.date = date;
  }

  public String getTime() {

    return time;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getType() {

    return type;
  }

  public Date getDate() {

    return date;
  }

  public String getId() {

    return id;
  }

}

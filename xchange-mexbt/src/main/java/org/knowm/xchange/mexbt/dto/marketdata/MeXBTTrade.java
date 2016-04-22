package org.knowm.xchange.mexbt.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import si.mazi.rescu.serialization.jackson.serializers.TimestampDeserializer;

public class MeXBTTrade {

  private final long tid;
  private final Date date;
  private final BigDecimal amount;
  private final BigDecimal price;

  public MeXBTTrade(@JsonProperty("tid") long tid, @JsonProperty("date") @JsonDeserialize(using = TimestampDeserializer.class) Date date,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price) {
    this.tid = tid;
    this.date = date;
    this.amount = amount;
    this.price = price;
  }

  public Date getDate() {
    return date;
  }

  public long getTid() {
    return tid;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

}

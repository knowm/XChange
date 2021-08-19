package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import org.knowm.xchange.utils.jackson.UnixTimestampDeserializer;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTrade {

  Date date;
  BigDecimal price;
  BigDecimal amount;
  Long tid;

  @JsonCreator
  public BitcoindeTrade(
      @JsonProperty("date") @JsonDeserialize(using = UnixTimestampDeserializer.class) Date date,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount_currency_to_trade") BigDecimal amount,
      @JsonProperty("tid") Long tid) {
    this.date = date;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
  }
}

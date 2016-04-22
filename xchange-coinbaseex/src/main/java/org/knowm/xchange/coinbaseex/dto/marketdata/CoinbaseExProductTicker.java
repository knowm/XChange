package org.knowm.xchange.coinbaseex.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 4/4/2015.
 */
public class CoinbaseExProductTicker {

  private final String tradeId;
  private final BigDecimal price;
  private final BigDecimal size;
  private Date time;

  public CoinbaseExProductTicker(@JsonProperty("trade_id") String tradeId, @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size, @JsonProperty("time") String time) {

    this.tradeId = tradeId;
    this.price = price;
    this.size = size;
    try {
      // Need to explicitly parse the date again as the timestamp from API contains microseconds
      time = time.substring(0, 23);
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      this.time = dateFormat.parse(time);
    } catch (ParseException e) {
      this.time = null;
    }
  }

  public Date getTime() {

    return time;
  }

  public String getTradeId() {

    return tradeId;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getSize() {

    return size;
  }

}

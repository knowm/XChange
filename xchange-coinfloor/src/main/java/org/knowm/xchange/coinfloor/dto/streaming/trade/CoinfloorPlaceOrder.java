package org.knowm.xchange.coinfloor.dto.streaming.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorPlaceOrder {

  private final int tag;
  private final int errorCode;
  private final int id;
  private final long time;
  private final BigDecimal remaining;

  public CoinfloorPlaceOrder(@JsonProperty("tag") int tag, @JsonProperty("error_code") int errorCode, @JsonProperty("id") int id,
      @JsonProperty("time") long time, @JsonProperty("remaining") int remaining) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.id = id;
    this.time = time / 1000;
    this.remaining = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, remaining);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public int getId() {

    return id;
  }

  public long getTime() {

    return time;
  }

  public BigDecimal getRemaining() {

    return remaining;
  }

  @Override
  public String toString() {

    return "CoinfloorPlaceOrderReturn{tag='" + tag + "', errorCode='" + errorCode + "', id='" + id + "', Time='" + new Date(time) + "', remaining='"
        + remaining + "'}";
  }
}

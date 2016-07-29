package org.knowm.xchange.cointrader.dto.marketdata;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cointrader.Cointrader;
import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;

/**
 * @author Matija Mazi
 */
public class CointraderTicker {

  public final BigDecimal lastTradePrice;
  public final BigDecimal bid;
  public final BigDecimal offer;
  public final BigDecimal high;
  public final BigDecimal low;
  public final BigDecimal average;
  public final BigDecimal volume;

  public CointraderTicker(@JsonProperty("lastTradePrice") BigDecimal lastTradePrice, @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("offer") BigDecimal offer, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low,
      @JsonProperty("average") BigDecimal average, @JsonProperty("volume") BigDecimal volume) {
    this.lastTradePrice = lastTradePrice;
    this.bid = bid;
    this.offer = offer;
    this.high = high;
    this.low = low;
    this.average = average;
    this.volume = volume;
  }

  @Override
  public String toString() {
    return String.format("CointraderTicker{lastTradePrice=%s, bid=%s, offer=%s, high=%s, low=%s, average=%s, volume=%s}", lastTradePrice, bid, offer,
        high, low, average, volume);
  }

  public static class Response extends CointraderBaseResponse<Map<Cointrader.Pair, CointraderTicker>> {

    public Response(@JsonProperty("success") Boolean success, @JsonProperty("message") String message,
        @JsonProperty("data") Map<Cointrader.Pair, CointraderTicker> data) {
      super(success, message, data);
    }
  }

  public enum Type {
    daily, weekly
  }
}

package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Jean-Christophe Laruelle */
@Getter
@ToString
public class KrakenFuturesOpenOrders extends KrakenFuturesResult {

  private final Date serverTime;
  private final List<KrakenFuturesOpenOrder> orders;

  public KrakenFuturesOpenOrders(
      @JsonProperty("result") String result,
      @JsonProperty("error") String error,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("openOrders") List<KrakenFuturesOpenOrder> orders) {

    super(result, error);

    this.serverTime = serverTime;
    this.orders = orders;
  }
}

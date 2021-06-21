package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;

public class GateioCandlestickHistory extends GateioBaseResponse {

  private final List<List<String>> candlesticks;
  private final String elapsed;

  private GateioCandlestickHistory(
      @JsonProperty("data") List<List<String>> candlesticks,
      @JsonProperty("result") boolean result,
      @JsonProperty("elapsed") String elapsed) {

    super(result, null);
    this.candlesticks = candlesticks;
    this.elapsed = elapsed;
  }

  public List<List<String>> getCandlesticks() {
    return candlesticks;
  }

  public String getElapsed() {
    return elapsed;
  }

  @Override
  public String toString() {
    return "BTERPublicTrades [candlesticks=" + candlesticks + ", elapsed=" + elapsed + "]";
  }
}

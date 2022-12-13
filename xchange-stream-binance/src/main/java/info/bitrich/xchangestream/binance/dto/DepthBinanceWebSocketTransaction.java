package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Getter;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class DepthBinanceWebSocketTransaction extends ProductBinanceWebSocketTransaction {

  private final BinanceOrderbook orderBook;
  private final long lastUpdateId;
  private final long firstUpdateId;
  private final long pu;

  public DepthBinanceWebSocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("U") long firstUpdateId,
      @JsonProperty("u") long lastUpdateId,
      @JsonProperty("pu") long pu,
      @JsonProperty("b") List<Object[]> _bids,
      @JsonProperty("a") List<Object[]> _asks) {
    super(eventType, eventTime, symbol);
    this.firstUpdateId = firstUpdateId;
    this.lastUpdateId = lastUpdateId;
    this.pu = pu;
    orderBook = new BinanceOrderbook(lastUpdateId, _bids, _asks);
  }
}

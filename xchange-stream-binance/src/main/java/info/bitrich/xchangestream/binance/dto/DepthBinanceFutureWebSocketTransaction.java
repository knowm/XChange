package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;

public class DepthBinanceFutureWebSocketTransaction extends ProductBinanceWebSocketTransaction {

  private final BinanceOrderbook orderBook;
  private final long lastUpdateId;
  private final long firstUpdateId;
  private final String transactionTime;
  private final long finalUpdateid;

  public DepthBinanceFutureWebSocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") String transactionTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("U") long firstUpdateId,
      @JsonProperty("u") long lastUpdateId,
      //            Final update Id in last stream(ie `u` in last stream)
      @JsonProperty("pu") long finalUpdateid,
      @JsonProperty("b") List<Object[]> _bids,
      @JsonProperty("a") List<Object[]> _asks) {
    super(eventType, eventTime, symbol);
    this.firstUpdateId = firstUpdateId;
    this.lastUpdateId = lastUpdateId;
    this.transactionTime = transactionTime;
    this.finalUpdateid = finalUpdateid;
    orderBook = new BinanceOrderbook(lastUpdateId, _bids, _asks);
  }

  public BinanceOrderbook getOrderBook() {
    return orderBook;
  }

  public long getFirstUpdateId() {
    return firstUpdateId;
  }

  public long getLastUpdateId() {
    return lastUpdateId;
  }

  public String getTransactionTime() {
    return transactionTime;
  }

  public long getFinalUpdateid() {
    return finalUpdateid;
  }
}

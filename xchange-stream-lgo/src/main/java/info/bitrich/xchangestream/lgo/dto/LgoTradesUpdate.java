package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LgoTradesUpdate {

  private final long batchId;
  private final String type;
  private final String channel;
  private final String productId;
  private final List<LgoTrade> trades;

  public LgoTradesUpdate(
      @JsonProperty("batch_id") long batchId,
      @JsonProperty("type") String type,
      @JsonProperty("channel") String channel,
      @JsonProperty("product_id") String productId,
      @JsonProperty("payload") List<LgoTrade> trades) {
    this.batchId = batchId;
    this.type = type;
    this.channel = channel;
    this.productId = productId;
    this.trades = trades;
  }

  public long getBatchId() {
    return batchId;
  }

  public String getType() {
    return type;
  }

  public String getChannel() {
    return channel;
  }

  public String getProductId() {
    return productId;
  }

  public List<LgoTrade> getTrades() {
    return trades;
  }
}

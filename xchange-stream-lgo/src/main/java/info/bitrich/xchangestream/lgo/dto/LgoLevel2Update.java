package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LgoLevel2Update {

  private final long batchId;
  private final String type;
  private final String channel;
  private final String productId;
  private final LgoLevel2Data data;

  public LgoLevel2Update(
      @JsonProperty("batch_id") long batchId,
      @JsonProperty("type") String type,
      @JsonProperty("channel") String channel,
      @JsonProperty("product_id") String productId,
      @JsonProperty("payload") LgoLevel2Data data) {
    this.batchId = batchId;
    this.type = type;
    this.channel = channel;
    this.productId = productId;
    this.data = data;
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

  public LgoLevel2Data getData() {
    return data;
  }
}

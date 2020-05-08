package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LgoUserSnapshot extends LgoUserMessage {

  private final List<LgoUserSnapshotData> snapshotData;

  public LgoUserSnapshot(
      @JsonProperty("batch_id") long batchId,
      @JsonProperty("type") String type,
      @JsonProperty("channel") String channel,
      @JsonProperty("product_id") String productId,
      @JsonProperty("payload") List<LgoUserSnapshotData> snapshotData) {
    super(batchId, type, channel, productId);
    this.snapshotData = snapshotData;
  }

  public List<LgoUserSnapshotData> getSnapshotData() {
    return snapshotData;
  }
}

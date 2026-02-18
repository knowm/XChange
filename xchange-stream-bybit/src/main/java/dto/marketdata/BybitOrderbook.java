package dto.marketdata;

import java.beans.ConstructorProperties;
import lombok.Getter;

@Getter
public class BybitOrderbook {

  private final String topic;
  private final String dataType;
  private final long ts;
  /*
  The timestamp from the matching engine when this orderbook data is produced. It can be correlated with T from public trade channel
   */
  private final long cts;
  private final BybitOrderbookData data;

  @ConstructorProperties({"topic", "type", "ts", "cts", "data"})
  public BybitOrderbook(String topic, String dataType, long ts, long cts, BybitOrderbookData data) {
    this.topic = topic;
    this.dataType = dataType;
    this.ts = ts;
    this.cts = cts;
    this.data = data;
  }
}

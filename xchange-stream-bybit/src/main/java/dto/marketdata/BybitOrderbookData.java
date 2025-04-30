package dto.marketdata;

import java.beans.ConstructorProperties;
import java.util.List;
import lombok.Getter;

@Getter
public class BybitOrderbookData {

  private final String symbolName;
  private final List<BybitPublicOrder> bid;
  private final List<BybitPublicOrder> ask;
  // Update ID. Is a sequence. Occasionally, you'll receive "u"=1, which is a snapshot data due to
  // the restart of the service. So please overwrite your local orderbook
  private final Integer u;
  // Cross sequence
  // You can use this field to compare different levels orderbook data, and for the smaller seq,
  // then it means the data is generated earlier.
  // in docs says than it is Integer, but in fact, we get in response bigger numbers
  private final Long seq;

  @ConstructorProperties({"s", "b", "a", "u", "seq"})
  public BybitOrderbookData(
      String symbolName,
      List<BybitPublicOrder> bid,
      List<BybitPublicOrder> ask,
      Integer u,
      Long seq) {
    this.symbolName = symbolName;
    this.bid = bid;
    this.ask = ask;
    this.u = u;
    this.seq = seq;
  }
}

package org.knowm.xchange.livecoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.livecoin.dto.LivecoinBaseResponse;

/** @author walec51 */
public class LivecoinAllOrderBooks extends LivecoinBaseResponse {

  private Map<String, LivecoinOrderBook> orderBooksByPair =
      new HashMap<String, LivecoinOrderBook>();

  public LivecoinAllOrderBooks(@JsonProperty("success") Boolean success) {
    super(success);
  }

  @JsonAnyGetter
  public Map<String, LivecoinOrderBook> getOrderBooksByPair() {
    return orderBooksByPair;
  }

  @JsonAnySetter
  public void setOrderBooksByPair(String pair, LivecoinOrderBook orderBook) {
    orderBooksByPair.put(pair, orderBook);
  }
}

package info.bitrich.xchangestream.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;

public class CexioWebSocketOrderBookSubscriptionData {
  @JsonProperty final List<String> pair;

  @JsonProperty final boolean subscribe;

  @JsonProperty final int depth;

  public CexioWebSocketOrderBookSubscriptionData(CurrencyPair currencyPair, boolean subscribe) {
    pair =
        new ArrayList<String>(
            Arrays.asList(currencyPair.base.toString(), currencyPair.counter.toString()));
    this.subscribe = subscribe;
    this.depth = 0; // 0 for full depth
  }
}

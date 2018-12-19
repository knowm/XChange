package org.knowm.xchange.coinbene.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;

public class CoinbeneTrades extends CoinbeneResponse {
  private final List<CoinbeneTrade> trades;

  public CoinbeneTrades(@JsonProperty("trades") List<CoinbeneTrade> trades) {
    this.trades = trades;
  }

  public List<CoinbeneTrade> getTrades() {
    return trades;
  }
}

package org.knowm.xchange.coinbase.v3.dto.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CoinbaseAdvancedTradeOrderFillsResponse {
  private final List<CoinbaseAdvancedTradeFills> fills;
  private final String cursor;

  public CoinbaseAdvancedTradeOrderFillsResponse(@JsonProperty("fills") List<CoinbaseAdvancedTradeFills> fills, @JsonProperty("cursor") String cursor ) {
    this.fills = fills;
    this.cursor = cursor;

  }

}

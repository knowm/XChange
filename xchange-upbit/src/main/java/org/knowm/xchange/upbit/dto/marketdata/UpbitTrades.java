package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.upbit.service.UpbitArrayOrMessageDeserializer;

/** @author interwater */
@JsonDeserialize(using = UpbitTrades.UpbitTradesDeserializer.class)
public class UpbitTrades {

  private final UpbitTrade[] upbitTrades;

  /** @param upbitTrades */
  public UpbitTrades(@JsonProperty() UpbitTrade[] upbitTrades) {
    this.upbitTrades = upbitTrades;
  }

  public UpbitTrade[] getUpbitTrades() {
    return upbitTrades;
  }

  static class UpbitTradesDeserializer
      extends UpbitArrayOrMessageDeserializer<UpbitTrade, UpbitTrades> {
    public UpbitTradesDeserializer() {
      super(UpbitTrade.class, UpbitTrades.class);
    }
  }
}

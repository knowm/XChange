package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OkexSpotInstrument {

  /** base currency, ie "BTC" */
  @JsonProperty("base_currency")
  private String baseCurrency;

  /** trading pair, ie "BTC-USDT" */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** minimum trading size, ie "0.001" */
  @JsonProperty("min_size")
  private BigDecimal minSize;

  /** quote currency, ie "USDT" */
  @JsonProperty("quote_currency")
  private String quoteCurrency;

  /** minimum increment size, ie "0.00000001" */
  @JsonProperty("size_increment")
  private BigDecimal sizeIncrement;

  /** trading price increment, ie "0.1" */
  @JsonProperty("tick_size")
  private BigDecimal tickSize;
}

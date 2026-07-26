package org.knowm.xchange.cryptocom.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComInstrument {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("inst_type")
  private String instType;

  @JsonProperty("display_name")
  private String displayName;

  @JsonProperty("base_ccy")
  private String baseCurrency;

  @JsonProperty("quote_ccy")
  private String quoteCurrency;

  @JsonProperty("quote_decimals")
  private Integer quoteDecimals;

  @JsonProperty("quantity_decimals")
  private Integer quantityDecimals;

  @JsonProperty("price_tick_size")
  private String priceTickSize;

  @JsonProperty("qty_tick_size")
  private String qtyTickSize;

  @JsonProperty("tradable")
  private Boolean tradable;

  @JsonProperty("max_leverage")
  private String maxLeverage;
}

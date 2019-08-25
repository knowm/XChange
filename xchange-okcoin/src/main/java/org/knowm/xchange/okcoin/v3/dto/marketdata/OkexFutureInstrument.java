package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OkexFutureInstrument {

  /** Contract ID, e.g. "BTC-USD-190621" */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** Trade currency, such as BTC in BTC-USD-190621 */
  @JsonProperty("underlying_index")
  private String underlyingIndex;

  /** Quote currency，such as USD in BTC-USD-190621 */
  @JsonProperty("quote_currency")
  private String quoteCurrency;

  /** Order price accuracy, ie "0.01" */
  @JsonProperty("tick_size")
  private BigDecimal tickSize;

  /** Contract value (USD), ie "100" */
  @JsonProperty("contract_val")
  private BigDecimal contractVal;

  /** Listing date, ie "2019-06-07" */
  private String listing;

  /** Delivery date, ie "2019-06-21" */
  private String delivery;

  /** Order quantity accuracy, ie "1" */
  @JsonProperty("trade_increment")
  private BigDecimal tradeIncrement;

  /** this_week, next_week, quarter */
  private String alias;
}

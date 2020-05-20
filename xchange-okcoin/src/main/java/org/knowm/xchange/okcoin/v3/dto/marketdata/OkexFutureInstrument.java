package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class OkexFutureInstrument {

  /** Contract ID, e.g. "BTC-USD-190621" */
  private String instrumentId;

  /** Trade currency, such as BTC in BTC-USD-190621 */
  private String underlyingIndex;

  /** Quote currency，such as USD in BTC-USD-190621 */
  private String quoteCurrency;

  /** Order price accuracy, ie "0.01" */
  private BigDecimal tickSize;

  /** Contract value (USD), ie "100" */
  private BigDecimal contractVal;

  /** Listing date, ie "2019-06-07" */
  private String listing;

  /** Delivery date, ie "2019-06-21" */
  private String delivery;

  /** Order quantity accuracy, ie "1" */
  private BigDecimal tradeIncrement;

  /** this_week, next_week, quarter */
  private String alias;

  /** Underlying index，eg：BTC-USD */
  private String underlying;
  /** Transaction currency，eg:BTC in BTC-USD,BTC in BTC-USDT */
  private String baseCurrency;
  /** Settlement currency，eg:BTC */
  private String settlementCurrency;
  /** (true or false) ,inverse contract or not */
  @JsonProperty("is_inverse")
  private boolean inverse;
  /** Contract denomination currency eg: USD，BTC，LTC，ETC , XRP, EOS */
  private String contractValCurrency;
}

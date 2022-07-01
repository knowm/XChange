package org.knowm.xchange.okcoin.v3.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.Data;

@Data
public class OkexSwapInstrument {

  /** Contract ID, e.g. BTC-USD-SWAP */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /** Trade currency, such as the BTC in BTC-USD-SWAP */
  @JsonProperty("underlying_index")
  private String underlyingIndex;

  /** Quote currency, such as the USD in BTC-USD-SWAP */
  @JsonProperty("quote_currency")
  private String quoteCurrency;

  /** Currency of margin, such as the BTC in BTC-USD-SWAP */
  private String coin;

  /** Contract value, ie 100 */
  @JsonProperty("contract_val")
  private BigDecimal contractVal;

  /** Creation time, ie "2018-08-28T02:43:23.000Z" */
  private String listing;
  /** Settlement time, ie "2019-06-20T14:00:00.000Z" */
  private String delivery;

  /** Order quantity accuracy, ie 1 */
  @JsonProperty("size_increment")
  private BigDecimal sizeIncrement;

  /** Order price accuracy, ie "0.1" */
  @JsonProperty("tick_size")
  private BigDecimal tickSize;

  public Date getListing() {
    return Date.from(Instant.parse(listing));
  }

  public Date getDelivery() {
    return Date.from(Instant.parse(delivery));
  }
}

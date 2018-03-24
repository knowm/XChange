package org.knowm.xchange.cryptocompare.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

/**
 * Data object representing a OHLCV History from Cryptocompare
 */

@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public final class CryptocompareHistory {

  @Getter private final String response;
  @Getter private final int type;
  @Getter private final long timeTo;
  @Getter private final long timeFrom;
  @Getter private final CryptocompareOHLCV[] ohlcvTuples;

  public CryptocompareHistory(
		  @JsonProperty(value="Response", required=true) String response,
		  @JsonProperty(value="Type",     required=true) int type,
		  @JsonProperty(value="TimeFrom", required=true) long timeFrom,
		  @JsonProperty(value="TimeTo",   required=true) long timeTo,
		  @JsonProperty(value="Data",     required=true) CryptocompareOHLCV[] ohlcvTuples
		  ) {
	  
	this.response = response; 
	this.type = type;
	this.timeFrom = timeFrom;
	this.timeTo = timeTo;
    this.ohlcvTuples = ohlcvTuples;
  }

}

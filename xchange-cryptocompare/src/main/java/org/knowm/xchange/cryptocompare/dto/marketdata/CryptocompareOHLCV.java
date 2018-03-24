package org.knowm.xchange.cryptocompare.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

/**
 * Data object representing OHLCV tuples from Cryptocompare  
 */

@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public final class CryptocompareOHLCV {
  @Getter private final long timestamp;
  @Getter private final BigDecimal open;
  @Getter private final BigDecimal high;
  @Getter private final BigDecimal low;
  @Getter private final BigDecimal close;
  @Getter private final BigDecimal volumeFrom;
  @Getter private final BigDecimal volumeTo;
  
	/*  
	  "time":1452675600,
	  "close":0.002536,
	  "high":0.002549,
	  "low":0.002501,
	  "open":0.002514,
	  "volumefrom":24753.98,
	  "volumeto":62.27
	*/

  public CryptocompareOHLCV(
		  @JsonProperty(value="time",       required=true) long timestamp, 
	      @JsonProperty(value="open",       required=true) BigDecimal open, 
	      @JsonProperty(value="high",       required=true) BigDecimal high, 
	      @JsonProperty(value="low",        required=true) BigDecimal low, 
	      @JsonProperty(value="close",      required=true) BigDecimal close, 
		  @JsonProperty(value="volumefrom", required=true) BigDecimal volumeFrom,
		  @JsonProperty(value="volumeto"  , required=true) BigDecimal volumeTo) {

	  this.timestamp = timestamp;
	  this.open = open;
	  this.high = high;
	  this.low = low;
	  this.close = close;
	  this.volumeFrom = volumeFrom;
	  this.volumeTo = volumeTo;
  }


}

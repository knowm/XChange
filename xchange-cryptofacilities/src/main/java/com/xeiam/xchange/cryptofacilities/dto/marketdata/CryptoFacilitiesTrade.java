package com.xeiam.xchange.cryptofacilities.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTrade extends CryptoFacilitiesResult {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
	private final Date timestamp;
	private final String uid;
	private final String unit;
	private final String tradeable;
	private final String direction;
	private final BigDecimal quantity;
	private final BigDecimal price;
		
	public CryptoFacilitiesTrade(@JsonProperty("result") String result
			, @JsonProperty("error") String error
			, @JsonProperty("timestamp") String strTimestamp
			, @JsonProperty("uid") String uid
			, @JsonProperty("unit") String unit
			, @JsonProperty("tradeable") String tradeable
			, @JsonProperty("dir") String direction
			, @JsonProperty("qty") BigDecimal quantity
			, @JsonProperty("price") BigDecimal price
			) throws ParseException {
	
		  super(result, error);
		    
		  this.timestamp = DATE_FORMAT.parse(strTimestamp);
		  this.uid = uid;
		  this.unit = unit;
		  this.tradeable = tradeable;
		  this.direction = direction;
		  this.quantity = quantity;
		  this.price = price;
	}

	public String getUnit() {
		return unit;
	}
	
	public String getTradeable() {
		return tradeable;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public String getUid() {
		return uid;
	}

	public String getDirection() {
		return direction;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public String toString() {	
		return "CryptoFacilitiesTrade [uid=" + uid 
			+ ", timestamp=" + DATE_FORMAT.format(timestamp)
			+ ", tradeable=" + tradeable
			+ ", unit=" + unit 
			+ ", dir=" + direction
			+ ", qty=" + quantity
			+ ", price=" + price
			+" ]";
	}
}

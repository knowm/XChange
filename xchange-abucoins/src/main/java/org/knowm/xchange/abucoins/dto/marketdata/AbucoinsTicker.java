package org.knowm.xchange.abucoins.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: bryant_harris
 */
public class AbucoinsTicker {
	/** 	identifier of the last trade */
	String trade_id;
	/** 	last price */
	BigDecimal price;
	/** size of the last trade */
	BigDecimal size;
	/** the best bid */
	BigDecimal bid;
	/** the best ask */
	BigDecimal ask;
	/** 24 hour volume */
	BigDecimal volume;
	/** time in UTC */
	String time;

  /**
   * Constructor
   *
   * @param last
   * @param high
   * @param low
   * @param volume
   * @param bid
   * @param ask
   */

  public AbucoinsTicker(@JsonProperty("trade_id") String trade_id, @JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size, @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask,
		  @JsonProperty("volume") BigDecimal volume, @JsonProperty("time") String time) {
	super();
	this.trade_id = trade_id;
	this.price = price;
	this.size = size;
	this.bid = bid;
	this.ask = ask;
	this.volume = volume;
	this.time = time;
}


public String getTrade_id() {
  return trade_id;
}


public BigDecimal getPrice() {
	return price;
}


public BigDecimal getSize() {
	return size;
}


public BigDecimal getBid() {
	return bid;
}


public BigDecimal getAsk() {
	return ask;
}


public BigDecimal getVolume() {
	return volume;
}


public String getTime() {
	return time;
}




@Override
public String toString() {
	return "AbucoinsTicker [trade_id=" + trade_id + ", price=" + price + ", size=" + size + ", bid=" + bid + ", ask="
			+ ask + ", volume=" + volume + ", time=" + time + "]";
}



}

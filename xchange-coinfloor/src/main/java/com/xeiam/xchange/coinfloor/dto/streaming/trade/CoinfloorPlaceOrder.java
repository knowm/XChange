package com.xeiam.xchange.coinfloor.dto.streaming.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorPlaceOrder{
	  
	  private final int tag;
	  private final int error_code;
	  private final int id;
	  private final long time;
	  private final BigDecimal remaining;
	  
	  public CoinfloorPlaceOrder(@JsonProperty("tag") int tag, @JsonProperty("error_code") int error_code, @JsonProperty("id") int id,
			  @JsonProperty("time") long time, @JsonProperty("remaining") int remaining){
		  this.tag = tag;
		  this.error_code = error_code;
		  this.id = id;
		  this.time = time / 1000;
		  this.remaining = CoinfloorUtils.scaleToBigDecimal("BTC", remaining);
	  }

	  public int getTag(){
		  return tag;
	  }
	  
	  public int getErrorCode(){
		  return error_code;
	  }
	  
	  public int getId(){
		  return id;
	  }
	  
	  public long getTime(){
		  return time;
	  }
	  
	  public BigDecimal getRemaining(){
		  return remaining;
	  }
	  
	  @Override
	  public String toString(){
		  return "CoinfloorPlaceOrderReturn{tag='" + tag + "', errorCode='" + error_code + "', id='" + id + "', Time='" + new Date(time) + "', remaining='" + remaining + "'}";
	  }
}

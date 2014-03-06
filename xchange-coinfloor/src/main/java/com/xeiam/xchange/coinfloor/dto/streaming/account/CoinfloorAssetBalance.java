package com.xeiam.xchange.coinfloor.dto.streaming.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinfloor.CoinfloorUtils;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorAssetBalance{
	  
	  private final String asset;
	  private final BigDecimal balance;
	  
	  public CoinfloorAssetBalance(@JsonProperty("asset") int asset, @JsonProperty("balance") int balance){
		  this.asset = CoinfloorUtils.getCurrency(asset);
		  this.balance = CoinfloorUtils.scaleToBigDecimal(CoinfloorUtils.getCurrency(asset), balance);
	  }

	  public String getAsset(){
		  return asset;
	  }
	  
	  public BigDecimal getBalance(){
		  return balance;
	  }
	  
	  @Override
	  public String toString(){
		  return "CoinfloorAssetBalance{asset='" + asset + "', balance='" + balance + "'}";
	  }
}

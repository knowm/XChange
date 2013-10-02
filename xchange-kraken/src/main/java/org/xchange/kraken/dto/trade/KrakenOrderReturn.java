package org.xchange.kraken.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.ExchangeException;


public class KrakenOrderReturn {
  private String txid;
  @JsonIgnoreProperties(ignoreUnknown = true)
  public KrakenOrderReturn(@JsonProperty("txid")String[] txids){
    if(txids.length<=0){
      throw new ExchangeException("No transaction Id");
    }
    else{
      txid=txids[0];
    }
  }
  public String getTxid(){
    return txid;
  }
}

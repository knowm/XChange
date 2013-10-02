package org.xchange.kraken.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;


public class KrakenCancelCount {
  private int count=-1;
  public KrakenCancelCount(@JsonProperty("count") int count){
    this.count=count;
  }
  public int getCount(){
    return count;
  }
}

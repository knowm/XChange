package org.knowm.xchange.tradeogre.dto.market;

import java.util.Map;

import lombok.Data;

@Data
public class TradeOgreOrderBook {
  public boolean success;
  public Map<String, String> buy;
  public Map<String, String> sell;
}

package org.knowm.xchange.tradeogre.dto.trade;

import lombok.Data;

@Data
public class TradeOgreTradeResponse {
  public boolean success;
  public String uuid;
  public String bnewbalavail;
  public String snewbalavail;
  public String error;
}

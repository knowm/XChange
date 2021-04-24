package org.knowm.xchange.tradeogre.dto.trade;

import lombok.Data;

@Data
public class TradeOgreOrder {
  public String uuid;
  public long date;
  public String type;
  public String price;
  public String quantity;
  public String market;
}

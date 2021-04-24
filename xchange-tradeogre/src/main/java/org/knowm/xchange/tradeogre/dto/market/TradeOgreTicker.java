package org.knowm.xchange.tradeogre.dto.market;

import lombok.Data;

@Data
public class TradeOgreTicker {
  private String volume;
  private String high;
  private String initialprice;
  private String low;
  private String success;
  private String price;
  private String ask;
  private String bid;
}

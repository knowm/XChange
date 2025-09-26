package org.knowm.xchange.tradeogre.dto.trade;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TradeOgreTrade {

  private long date;
  private String type;
  private BigDecimal price;
  private BigDecimal quantity;
}

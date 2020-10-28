package org.knowm.xchange.bittrex.dto.marketdata;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BittrexTicker {
  private String symbol;
  private BigDecimal lastTradeRate;
  private BigDecimal bidRate;
  private BigDecimal askRate;
}

package org.knowm.xchange.bittrex.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BittrexMarketSummary {
  private String symbol;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal volume;
  private BigDecimal quoteVolume;
  private BigDecimal percentChange;
  private Date updatedAt;
}

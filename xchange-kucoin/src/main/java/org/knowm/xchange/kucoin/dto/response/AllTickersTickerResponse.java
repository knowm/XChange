package org.knowm.xchange.kucoin.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AllTickersTickerResponse {
  private String symbol;
  private BigDecimal high;
  private BigDecimal vol;
  private BigDecimal last;
  private BigDecimal low;
  private BigDecimal buy;
  private BigDecimal sell;
  private BigDecimal changePrice;
  private BigDecimal changeRate;
  private BigDecimal volValue;
}

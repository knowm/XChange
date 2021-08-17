package org.knowm.xchange.bittrex.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BittrexTrade {
  private String id;
  private Date executedAt;
  private BigDecimal quantity;
  private BigDecimal rate;
  private String takerSide;
}

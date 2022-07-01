package org.knowm.xchange.tradeogre.dto.account;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TradeOgreBalance {
  private BigDecimal balance;
  private BigDecimal available;
}

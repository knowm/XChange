package org.knowm.xchange.tradeogre.dto.account;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class TradeOgreBalances {
  private Map<String, BigDecimal> balances;
}

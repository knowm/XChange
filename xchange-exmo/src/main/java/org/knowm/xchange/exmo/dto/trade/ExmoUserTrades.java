package org.knowm.xchange.exmo.dto.trade;

import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.dto.trade.UserTrade;

public class ExmoUserTrades {
  private final BigDecimal originalAmount;
  private final List<UserTrade> userTrades;

  public ExmoUserTrades(BigDecimal originalAmount, List<UserTrade> userTrades) {
    this.originalAmount = originalAmount;
    this.userTrades = userTrades;
  }

  public BigDecimal getOriginalAmount() {
    return originalAmount;
  }

  public List<UserTrade> getUserTrades() {
    return userTrades;
  }
}

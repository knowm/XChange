package org.knowm.xchange.ripple.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.ripple.dto.RippleAmount;

public interface IRippleTradeTransaction {
  public List<RippleAmount> getBalanceChanges();

  public BigDecimal getFee();

  public long getOrderId();

  public String getHash();

  public Date getTimestamp();
}

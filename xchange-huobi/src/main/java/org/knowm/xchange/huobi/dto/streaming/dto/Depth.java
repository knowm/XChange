package org.knowm.xchange.huobi.dto.streaming.dto;

import java.math.BigDecimal;

public interface Depth {

  String getSymbolId();

  long getTime();

  long getVersion();

  BigDecimal[] getBidPrice();

  BigDecimal[] getBidAmount();

  BigDecimal[] getAskPrice();

  BigDecimal[] getAskAmount();

  void merge(DepthDiff diff);

}

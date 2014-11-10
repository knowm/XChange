package com.xeiam.xchange.service.polling.trade;

import java.util.Date;

public interface TradeHistoryParamSinceTime extends TradeHistoryParams {
  void setStartTime(Date from);

  Date getStartTime();
}

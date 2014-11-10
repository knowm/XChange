package com.xeiam.xchange.service.polling.trade;

/**
 * @author Rafał Krupiński
 */
public interface TradeHistoryParamCount extends TradeHistoryParams {
  void setCount(Long count);

  Long getCount();
}

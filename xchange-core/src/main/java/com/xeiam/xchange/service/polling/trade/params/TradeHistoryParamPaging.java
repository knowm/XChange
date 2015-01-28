package com.xeiam.xchange.service.polling.trade.params;

public interface TradeHistoryParamPaging extends TradeHistoryParams {

  void setPageLength(Integer pageLength);

  Integer getPageLength();

  void setPageNumber(Integer pageNumber);

  Integer getPageNumber();
}

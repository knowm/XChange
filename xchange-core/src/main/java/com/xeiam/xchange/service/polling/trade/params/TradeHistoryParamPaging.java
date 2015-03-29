package com.xeiam.xchange.service.polling.trade.params;

public interface TradeHistoryParamPaging extends TradeHistoryParams {

  void setPageLength(Integer pageLength);

  Integer getPageLength();

  /** 0-based page number */
  void setPageNumber(Integer pageNumber);

  /** 0-based page number */
  Integer getPageNumber();
}

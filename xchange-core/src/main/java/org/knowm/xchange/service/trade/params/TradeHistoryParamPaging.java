package org.knowm.xchange.service.trade.params;

public interface TradeHistoryParamPaging extends TradeHistoryParams {

  Integer getPageLength();

  void setPageLength(Integer pageLength);

  /** 0-based page number */
  Integer getPageNumber();

  /** 0-based page number */
  void setPageNumber(Integer pageNumber);
}

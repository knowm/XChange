package org.knowm.xchange.service.polling.trade.params;

/**
 * Common implementation of {@link TradeHistoryParamPaging} interface
 */
public class DefaultTradeHistoryParamPaging implements TradeHistoryParamPaging {

  private Integer pageLength;
  private Integer pageNumber;

  public DefaultTradeHistoryParamPaging() {
  }

  public DefaultTradeHistoryParamPaging(Integer pageLength) {
    this(pageLength, 0);
  }

  public DefaultTradeHistoryParamPaging(Integer pageLength, Integer pageNumber) {

    this.pageLength = pageLength;
    this.pageNumber = pageNumber;
  }

  @Override
  public Integer getPageLength() {

    return pageLength;
  }

  @Override
  public void setPageLength(Integer pageLength) {

    this.pageLength = pageLength;
  }

  @Override
  public Integer getPageNumber() {

    return pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {

    this.pageNumber = pageNumber;
  }
}

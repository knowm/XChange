package com.xeiam.xchange.btce.v3.service.polling.trade.params;

import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsIdSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;

import java.util.Date;

/**
 * @author Peter N. Steinmetz
 *         Date: 4/2/15
 *         Time: 6:54 PM
 */
public class BTCETradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamsIdSpan, TradeHistoryParamsTimeSpan,
   TradeHistoryParamCurrencyPair {

  private CurrencyPair pair;
  private BTCEAuthenticated.SortOrder sortOrder;
  private String startId;
  private String endId;
  private Date startTime;
  private Date endTime;

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }

  @Override
  public void setStartId(String startId) {

    this.startId = startId;
  }

  @Override
  public String getStartId() {

    return startId;
  }

  @Override
  public void setEndId(String endId) {

    this.endId = endId;
  }

  @Override
  public String getEndId() {
    return endId;
  }

  @Override
  public void setStartTime(Date startTime) {

    this.startTime = startTime;
  }

  @Override
  public Date getStartTime() {
    return startTime;
  }

  @Override
  public void setEndTime(Date endTime) {

    this.endTime = endTime;
  }

  @Override
  public Date getEndTime() {
    return endTime;
  }

  public void setSortOrder(BTCEAuthenticated.SortOrder sortOrder) {

    this.sortOrder = sortOrder;
  }
}

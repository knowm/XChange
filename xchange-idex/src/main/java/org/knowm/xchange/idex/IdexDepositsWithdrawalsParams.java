package org.knowm.xchange.idex;

import java.util.Date;
import org.knowm.xchange.idex.dto.DepositsWithdrawalsReq;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public final class IdexDepositsWithdrawalsParams extends DepositsWithdrawalsReq
    implements TradeHistoryParams, TradeHistoryParamsTimeSpan {

  public IdexDepositsWithdrawalsParams(String address) {
    setAddress(address);
  }

  @Override
  public Date getStartTime() {
    return new Date(Long.valueOf(getStart()) * 1000);
  }

  @Override
  public void setStartTime(Date date) {
    setStart(String.valueOf(date.getTime() / 1000));
  }

  @Override
  public Date getEndTime() {
    return new Date(Long.valueOf(getEnd()) * 1000);
  }

  @Override
  public void setEndTime(Date date) {
    setEnd(String.valueOf(date.getTime() / 1000));
  }
}

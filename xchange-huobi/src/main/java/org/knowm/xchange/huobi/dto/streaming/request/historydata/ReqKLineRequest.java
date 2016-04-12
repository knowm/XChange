package org.knowm.xchange.huobi.dto.streaming.request.historydata;

import java.util.Date;

import org.knowm.xchange.huobi.dto.streaming.dto.Period;
import org.knowm.xchange.huobi.dto.streaming.request.AbstractSymbolIdRequest;

/**
 * Request of history candlestick data.
 */
public class ReqKLineRequest extends AbstractSymbolIdRequest {

  private final Period period;

  private Date from, to;

  /**
   * @param version 终端版本
   * @param symbolId 交易代码
   * @param period k线类型
   */
  public ReqKLineRequest(int version, String symbolId, Period period) {
    super(version, "reqKLine", symbolId);
    this.period = period;
  }

  /**
   * @return 开始时间，默认最近300条的时间区间。
   */
  public Date getFrom() {
    return from;
  }

  public void setFrom(Date from) {
    this.from = from;
  }

  /**
   * @return 结束时间，默认到最新。
   */
  public Date getTo() {
    return to;
  }

  public void setTo(Date to) {
    this.to = to;
  }

  public Period getPeriod() {
    return period;
  }

}

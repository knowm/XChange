package org.knowm.xchange.huobi.dto.streaming.request.historydata;

import java.util.Date;

import org.knowm.xchange.huobi.dto.streaming.request.AbstractSymbolIdRequest;

/**
 * Request of history time-interval.
 */
public class ReqTimeLineRequest extends AbstractSymbolIdRequest {

  private Date from, to;

  public ReqTimeLineRequest(int version, String symbolId) {
    super(version, "reqTimeLine", symbolId);
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

}

package org.knowm.xchange.huobi.dto.streaming.request.historydata;

import org.knowm.xchange.huobi.dto.streaming.dto.Percent;
import org.knowm.xchange.huobi.dto.streaming.request.AbstractSymbolIdRequest;

/**
 * Request of market-depth.
 */
public class ReqMarketDepthRequest extends AbstractSymbolIdRequest {

  private final Percent percent;

  /**
   * @param version 终端版本
   * @param symbolId 交易代码
   * @param percent 行情深度的百分比，缺省10%
   */
  public ReqMarketDepthRequest(int version, String symbolId, Percent percent) {
    super(version, "reqMarketDepth", symbolId);
    this.percent = percent;
  }

  public Percent getPercent() {
    return percent;
  }

}

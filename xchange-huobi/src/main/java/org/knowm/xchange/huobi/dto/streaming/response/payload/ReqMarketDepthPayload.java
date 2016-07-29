package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.dto.Depth;
import org.knowm.xchange.huobi.dto.streaming.dto.Percent;
import org.knowm.xchange.huobi.dto.streaming.response.historydata.ReqMarketDepthResponse;

/**
 * Payload of {@link ReqMarketDepthResponse}.
 */
public class ReqMarketDepthPayload extends ReqMarketDepthTopPayload implements Depth {

  private final Percent percent;

  /**
   * 行情深度
   *
   * @param symbolId 交易代码
   * @param percent 行情深度百分比
   * @param time 时间
   * @param version 快照版本，方便进行差量更新，如果终端的最后快照版本不一致，则需要重新获取最新完整行情深度。
   * @param bidName 买单文字描述
   * @param bidPrice 买单价格
   * @param bidTotal 累计买单量
   * @param bidAmount 买单量
   * @param askName 卖单文字描述
   * @param askPrice 卖单价格
   * @param askTotal 累计卖单量
   * @param askAmount 卖单量
   */
  public ReqMarketDepthPayload(String symbolId, Percent percent, long time, long version,

      String bidName, BigDecimal[] bidPrice, BigDecimal[] bidTotal, BigDecimal[] bidAmount, String askName, BigDecimal[] askPrice,
      BigDecimal[] askTotal, BigDecimal[] askAmount) {
    super(symbolId, time, version, bidName, bidPrice, bidTotal, bidAmount, askName, askPrice, askTotal, askAmount);
    this.percent = percent;
  }

  public Percent getPercent() {
    return percent;
  }

}

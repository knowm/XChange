package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.historydata.ReqTimeLineResponse;

/**
 * Payload of {@link ReqTimeLineResponse}.
 */
public class ReqTimeLinePayload extends AbstractPayload {

  private final String symbolId;
  private final long[] time;
  private final BigDecimal[] priceLast;
  private final BigDecimal[] amount;
  private final BigDecimal[] volume;
  private final int[] count;

  /**
   * 历史分时
   *
   * @param symbolId 交易代码
   * @param time 时间，秒数
   * @param priceLast 收盘
   * @param amount 成交量
   * @param volume 成交额
   * @param count 成交笔数
   */
  public ReqTimeLinePayload(String symbolId, long[] time, BigDecimal[] priceLast, BigDecimal[] amount, BigDecimal[] volume, int[] count) {
    super();
    this.symbolId = symbolId;
    this.time = time;
    this.priceLast = priceLast;
    this.amount = amount;
    this.volume = volume;
    this.count = count;
  }

  public String getSymbolId() {
    return symbolId;
  }

  public long[] getTime() {
    return time;
  }

  public BigDecimal[] getPriceLast() {
    return priceLast;
  }

  public BigDecimal[] getAmount() {
    return amount;
  }

  public BigDecimal[] getVolume() {
    return volume;
  }

  public int[] getCount() {
    return count;
  }

}

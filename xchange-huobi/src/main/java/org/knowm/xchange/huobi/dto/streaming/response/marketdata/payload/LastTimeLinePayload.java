package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.LastTimeLine;
import org.knowm.xchange.huobi.dto.streaming.response.payload.AbstractPayload;

/**
 * Payload of {@link LastTimeLine}.
 */
public class LastTimeLinePayload extends AbstractPayload {

  private final long _id;
  private final String symbolId;
  private final long time;
  private final BigDecimal priceLast;
  private final BigDecimal amount;
  private final BigDecimal volume;
  private final int count;

  /**
   * 最后分时
   *
   * @param _id 唯一id
   * @param symbolId 交易代码
   * @param time 时间，秒数
   * @param priceLast 收盘
   * @param amount 成交量
   * @param volume 成交额
   * @param count 成交笔数
   */
  public LastTimeLinePayload(long _id, String symbolId, long time, BigDecimal priceLast, BigDecimal amount, BigDecimal volume, int count) {
    super();
    this._id = _id;
    this.symbolId = symbolId;
    this.time = time;
    this.priceLast = priceLast;
    this.amount = amount;
    this.volume = volume;
    this.count = count;
  }

  public long get_id() {
    return _id;
  }

  public String getSymbolId() {
    return symbolId;
  }

  public long getTime() {
    return time;
  }

  public BigDecimal getPriceLast() {
    return priceLast;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public int getCount() {
    return count;
  }

}

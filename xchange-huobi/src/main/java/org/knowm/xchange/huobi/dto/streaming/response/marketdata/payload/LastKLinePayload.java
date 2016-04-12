package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.dto.Period;
import org.knowm.xchange.huobi.dto.streaming.response.marketdata.LastKLine;
import org.knowm.xchange.huobi.dto.streaming.response.payload.AbstractPayload;

/**
 * Payload of {@link LastKLine}.
 */
public class LastKLinePayload extends AbstractPayload {

  private final long _id;
  private final String symbolId;
  private final long time;
  private final Period period;
  private final BigDecimal priceOpen;
  private final BigDecimal priceHigh;
  private final BigDecimal priceLow;
  private final BigDecimal priceLast;
  private final BigDecimal amount;
  private final BigDecimal volume;
  private final int count;

  /**
   * 最后k线
   *
   * @param _id 唯一id
   * @param symbolId 交易代码
   * @param time 时间
   * @param period k线周期
   * @param priceOpen 开盘
   * @param priceHigh 最高
   * @param priceLow 最低
   * @param priceLast 收盘
   * @param amount 成交量
   * @param volume 成交额
   * @param count 成交笔数
   */
  public LastKLinePayload(long _id, String symbolId, long time, Period period, BigDecimal priceOpen, BigDecimal priceHigh, BigDecimal priceLow,
      BigDecimal priceLast, BigDecimal amount, BigDecimal volume, int count) {
    super();
    this._id = _id;
    this.symbolId = symbolId;
    this.time = time;
    this.period = period;
    this.priceOpen = priceOpen;
    this.priceHigh = priceHigh;
    this.priceLow = priceLow;
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

  public Period getPeriod() {
    return period;
  }

  public BigDecimal getPriceOpen() {
    return priceOpen;
  }

  public BigDecimal getPriceHigh() {
    return priceHigh;
  }

  public BigDecimal getPriceLow() {
    return priceLow;
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

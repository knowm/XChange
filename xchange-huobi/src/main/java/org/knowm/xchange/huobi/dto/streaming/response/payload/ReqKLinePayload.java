package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.dto.Period;
import org.knowm.xchange.huobi.dto.streaming.response.historydata.ReqKLineResponse;

/**
 * Payload of {@link ReqKLineResponse}.
 */
public class ReqKLinePayload extends AbstractPayload {

  private final String symbolId;
  private final Period period;
  private final long[] time;
  private final BigDecimal[] priceOpen;
  private final BigDecimal[] priceHigh;
  private final BigDecimal[] priceLow;
  private final BigDecimal[] priceLast;
  private final BigDecimal[] amount;
  private final BigDecimal[] volume;
  private final int[] count;

  /**
   * 历史k线
   *
   * @param symbolId 交易代码
   * @param period k线周期
   * @param time 时间
   * @param priceOpen 开盘
   * @param priceHigh 最高
   * @param priceLow 最低
   * @param priceLast 收盘
   * @param amount 成交量
   * @param volume 成交额
   * @param count 成交笔数
   */
  public ReqKLinePayload(String symbolId, Period period, long[] time, BigDecimal[] priceOpen, BigDecimal[] priceHigh, BigDecimal[] priceLow,
      BigDecimal[] priceLast, BigDecimal[] amount, BigDecimal[] volume, int[] count) {
    this.symbolId = symbolId;
    this.period = period;
    this.time = time;
    this.priceOpen = priceOpen;
    this.priceHigh = priceHigh;
    this.priceLow = priceLow;
    this.priceLast = priceLast;
    this.amount = amount;
    this.volume = volume;
    this.count = count;
  }

  public String getSymbolId() {
    return symbolId;
  }

  public Period getPeriod() {
    return period;
  }

  public long[] getTime() {
    return time;
  }

  public BigDecimal[] getPriceOpen() {
    return priceOpen;
  }

  public BigDecimal[] getPriceHigh() {
    return priceHigh;
  }

  public BigDecimal[] getPriceLow() {
    return priceLow;
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

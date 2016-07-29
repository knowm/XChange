package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.MarketOverview;
import org.knowm.xchange.huobi.dto.streaming.response.payload.AbstractPayload;

/**
 * Payload of {@link MarketOverview}.
 */
public class MarketOverviewPayload extends AbstractPayload {

  private final String symbolId;
  private final BigDecimal priceNew;
  private final BigDecimal priceOpen;
  private final BigDecimal priceHigh;
  private final BigDecimal priceLow;
  private final BigDecimal priceAsk;
  private final BigDecimal priceBid;
  private final BigDecimal totalVolume;
  private final BigDecimal totalAmount;

  /**
   * 市场概况
   *
   * @param symbolId 交易代码
   * @param priceNew 当前价格
   * @param priceOpen 开盘
   * @param priceHigh 最高
   * @param priceLow 最低
   * @param priceAsk 卖一
   * @param priceBid 买一
   * @param totalVolume 今日累计成交量
   * @param totalAmount 今日累计成交额
   */
  public MarketOverviewPayload(String symbolId, BigDecimal priceNew, BigDecimal priceOpen, BigDecimal priceHigh, BigDecimal priceLow,
      BigDecimal priceAsk, BigDecimal priceBid, BigDecimal totalVolume, BigDecimal totalAmount) {
    super();
    this.symbolId = symbolId;
    this.priceNew = priceNew;
    this.priceOpen = priceOpen;
    this.priceHigh = priceHigh;
    this.priceLow = priceLow;
    this.priceAsk = priceAsk;
    this.priceBid = priceBid;
    this.totalVolume = totalVolume;
    this.totalAmount = totalAmount;
  }

  public String getSymbolId() {
    return symbolId;
  }

  public BigDecimal getPriceNew() {
    return priceNew;
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

  public BigDecimal getPriceAsk() {
    return priceAsk;
  }

  public BigDecimal getPriceBid() {
    return priceBid;
  }

  public BigDecimal getTotalVolume() {
    return totalVolume;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

}

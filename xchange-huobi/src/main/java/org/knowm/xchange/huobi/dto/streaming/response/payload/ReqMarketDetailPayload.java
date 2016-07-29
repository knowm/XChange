package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.historydata.ReqMarketDetailResponse;

/**
 * Payload of {@link ReqMarketDetailResponse}.
 */
public class ReqMarketDetailPayload extends AbstractPayload {

  private final String symbolId;
  private final BigDecimal priceNew;
  private final BigDecimal priceOpen;
  private final BigDecimal priceHigh;
  private final BigDecimal priceLow;
  private final BigDecimal priceLast;
  private final int level;
  private final BigDecimal amount;
  private final BigDecimal totalAmount;
  private final String amp;
  private Trades trades;
  private Orders bids;
  private Orders asks;
  private final BigDecimal commissionRatio;
  private final BigDecimal poor;
  private final BigDecimal updownVolume;
  private final BigDecimal updownRatio;
  private final BigDecimal priceAverage;
  private final BigDecimal volumeRatio;
  private final BigDecimal turnVolume;
  private final BigDecimal turnoverRate;
  private final BigDecimal outerDisc;
  private final BigDecimal innerDisc;
  private final BigDecimal totalVolume;

  /**
   * 盘口信息
   *
   * @param symbolId 交易代码
   * @param priceNew 最新成交价
   * @param priceOpen 最近24小时开盘
   * @param priceHigh 最近24小时最高
   * @param priceLow 最近24小时最低
   * @param priceLast 最近24小时收盘
   * @param level
   * @param amount 最近24小时成交量
   * @param totalAmount
   * @param amp
   * @param trades
   * @param bids 10卖单
   * @param asks 10买单
   * @param commissionRatio 委比：(委买手数－委卖手数)/(委买手数＋委卖手数)*100%
   * @param poor 委差：委买委卖的差值（即委差）
   * @param updownVolume 涨跌量：最新价减去开盘价，原来的level字段
   * @param updownRatio 涨跌幅：涨跌比例：level比开盘价，原来的amp字段
   * @param priceAverage 均价
   * @param volumeRatio
   * @param turnVolume 金额
   * @param turnoverRate 换手
   * @param outerDisc 外盘：外盘(即买盘):是指以卖出价成交的股票手数,就是先有卖出的价格,而后才有成交。
   * @param innerDisc 内盘：内盘(即卖盘):是指以买进价格成交的股票手数, 就是先有买进的价格,而后才有成交。
   * @param totalVolume 最近24小时成交额
   */
  public ReqMarketDetailPayload(String symbolId, BigDecimal priceNew, BigDecimal priceOpen, BigDecimal priceHigh, BigDecimal priceLow,
      BigDecimal priceLast, int level, BigDecimal amount, BigDecimal totalAmount, String amp, Trades trades, Orders bids, Orders asks,
      BigDecimal commissionRatio, BigDecimal poor, BigDecimal updownVolume, BigDecimal updownRatio, BigDecimal priceAverage, BigDecimal volumeRatio,
      BigDecimal turnVolume, BigDecimal turnoverRate, BigDecimal outerDisc, BigDecimal innerDisc, BigDecimal totalVolume) {
    this.symbolId = symbolId;
    this.priceNew = priceNew;
    this.priceOpen = priceOpen;
    this.priceHigh = priceHigh;
    this.priceLow = priceLow;
    this.priceLast = priceLast;
    this.level = level;
    this.amount = amount;
    this.totalAmount = totalAmount;
    this.amp = amp;
    this.trades = trades;
    this.bids = bids;
    this.asks = asks;
    this.commissionRatio = commissionRatio;
    this.poor = poor;
    this.updownVolume = updownVolume;
    this.updownRatio = updownRatio;
    this.priceAverage = priceAverage;
    this.volumeRatio = volumeRatio;
    this.turnVolume = turnVolume;
    this.turnoverRate = turnoverRate;
    this.outerDisc = outerDisc;
    this.innerDisc = innerDisc;
    this.totalVolume = totalVolume;
  }

  public Trades getTrades() {
    return trades;
  }

  public void setTrades(Trades trades) {
    this.trades = trades;
  }

  public Orders getBids() {
    return bids;
  }

  public void setBids(Orders bids) {
    this.bids = bids;
  }

  public Orders getAsks() {
    return asks;
  }

  public void setAsks(Orders asks) {
    this.asks = asks;
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

  public BigDecimal getPriceLast() {
    return priceLast;
  }

  public int getLevel() {
    return level;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public String getAmp() {
    return amp;
  }

  public BigDecimal getCommissionRatio() {
    return commissionRatio;
  }

  public BigDecimal getPoor() {
    return poor;
  }

  public BigDecimal getUpdownVolume() {
    return updownVolume;
  }

  public BigDecimal getUpdownRatio() {
    return updownRatio;
  }

  public BigDecimal getPriceAverage() {
    return priceAverage;
  }

  public BigDecimal getVolumeRatio() {
    return volumeRatio;
  }

  public BigDecimal getTurnVolume() {
    return turnVolume;
  }

  public BigDecimal getTurnoverRate() {
    return turnoverRate;
  }

  public BigDecimal getOuterDisc() {
    return outerDisc;
  }

  public BigDecimal getInnerDisc() {
    return innerDisc;
  }

  public BigDecimal getTotalVolume() {
    return totalVolume;
  }

}

package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.dto.TradeDetail;
import org.knowm.xchange.huobi.dto.streaming.response.historydata.ReqTradeDetailTopResponse;

/**
 * Payload of {@link ReqTradeDetailTopResponse}.
 */
public class ReqTradeDetailTopPayload extends AbstractPayload implements TradeDetail {

  private final String symbolId;
  private final long[] tradeId;
  private final BigDecimal[] price;
  private final long[] time;
  private final BigDecimal[] amount;
  private final int[] direction;
  private final Orders[] topAsks;
  private final Orders[] topBids;

  /**
   * 交易明细
   *
   * @param symbolId 交易代码
   * @param tradeId 交易id
   * @param price 成交价格
   * @param time 成交时间
   * @param amount 成交量
   * @param direction 成交类型：买入或者卖出
   * @param topAsks top5卖单
   * @param topBids top5买单
   */
  public ReqTradeDetailTopPayload(String symbolId, long[] tradeId, BigDecimal[] price, long[] time, BigDecimal[] amount, int[] direction,
      Orders[] topAsks, Orders[] topBids) {
    super();
    this.symbolId = symbolId;
    this.tradeId = tradeId;
    this.price = price;
    this.time = time;
    this.amount = amount;
    this.direction = direction;
    this.topAsks = topAsks;
    this.topBids = topBids;
  }

  @Override
  public String getSymbolId() {
    return symbolId;
  }

  @Override
  public long[] getTradeId() {
    return tradeId;
  }

  @Override
  public BigDecimal[] getPrice() {
    return price;
  }

  @Override
  public long[] getTime() {
    return time;
  }

  @Override
  public BigDecimal[] getAmount() {
    return amount;
  }

  @Override
  public int[] getDirection() {
    return direction;
  }

  @Override
  public Orders[] getTopAsks() {
    return topAsks;
  }

  @Override
  public Orders[] getTopBids() {
    return topBids;
  }

}

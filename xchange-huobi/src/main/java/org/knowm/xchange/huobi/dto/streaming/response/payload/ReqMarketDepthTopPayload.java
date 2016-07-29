package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.apache.commons.lang3.ArrayUtils;

import org.knowm.xchange.huobi.dto.streaming.dto.Depth;
import org.knowm.xchange.huobi.dto.streaming.dto.DepthDiff;
import org.knowm.xchange.huobi.dto.streaming.response.historydata.ReqMarketDepthTopResponse;

/**
 * Payload of {@link ReqMarketDepthTopResponse}.
 */
public class ReqMarketDepthTopPayload extends AbstractPayload implements Depth {

  private final String symbolId;
  private final long time;
  private long version;

  private final String bidName;
  private BigDecimal[] bidPrice;
  private BigDecimal[] bidTotal;
  private BigDecimal[] bidAmount;

  private final String askName;
  private BigDecimal[] askPrice;
  private BigDecimal[] askTotal;
  private BigDecimal[] askAmount;

  /**
   * Top条行情深度
   *
   * @param symbolId 交易代码
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
  public ReqMarketDepthTopPayload(String symbolId, long time, long version,

      String bidName, BigDecimal[] bidPrice, BigDecimal[] bidTotal, BigDecimal[] bidAmount,

      String askName, BigDecimal[] askPrice, BigDecimal[] askTotal, BigDecimal[] askAmount

  ) {
    this.symbolId = symbolId;
    this.time = time;
    this.version = version;
    this.bidName = bidName;
    this.bidPrice = bidPrice;
    this.bidTotal = bidTotal;
    this.bidAmount = bidAmount;
    this.askName = askName;
    this.askPrice = askPrice;
    this.askTotal = askTotal;
    this.askAmount = askAmount;
  }

  @Override
  public String getSymbolId() {
    return symbolId;
  }

  @Override
  public long getTime() {
    return time;
  }

  @Override
  public long getVersion() {
    return version;
  }

  public String getBidName() {
    return bidName;
  }

  @Override
  public BigDecimal[] getBidPrice() {
    return bidPrice;
  }

  public BigDecimal[] getBidTotal() {
    return bidTotal;
  }

  @Override
  public BigDecimal[] getBidAmount() {
    return bidAmount;
  }

  public String getAskName() {
    return askName;
  }

  @Override
  public BigDecimal[] getAskPrice() {
    return askPrice;
  }

  public BigDecimal[] getAskTotal() {
    return askTotal;
  }

  @Override
  public BigDecimal[] getAskAmount() {
    return askAmount;
  }

  @Override
  public void merge(DepthDiff diff) {
    if (diff.getVersionOld() != this.getVersion()) {
      throw new IllegalArgumentException("Mismatched version.");
    }

    this.version = diff.getVersion();

    for (int i = 0, l = diff.getBidUpdate().getRow().length; i < l; i++) {
      BigDecimal price = diff.getBidUpdate().getPrice()[i];
      BigDecimal amount = diff.getBidUpdate().getAmount()[i];
      int row = diff.getBidUpdate().getRow()[i];

      this.bidPrice[row] = price;
      this.bidAmount[row] = amount;
      this.bidTotal[row] = price.multiply(amount);
    }

    for (int i = 0, l = diff.getBidInsert().getRow().length; i < l; i++) {
      BigDecimal price = diff.getBidInsert().getPrice()[i];
      BigDecimal amount = diff.getBidInsert().getAmount()[i];
      int row = diff.getBidInsert().getRow()[i];

      this.bidPrice = ArrayUtils.add(this.bidPrice, row, price);
      this.bidAmount = ArrayUtils.add(this.bidAmount, row, amount);
      this.bidTotal = ArrayUtils.add(this.bidTotal, row, price.multiply(amount));
    }

    for (int i = 0, l = diff.getBidDelete().length; i < l; i++) {
      int row = diff.getBidDelete()[i];

      this.bidPrice = ArrayUtils.remove(this.bidPrice, row);
      this.bidAmount = ArrayUtils.remove(this.bidAmount, row);
      this.bidTotal = ArrayUtils.remove(this.bidTotal, row);
    }

    for (int i = 0, l = diff.getAskUpdate().getRow().length; i < l; i++) {
      BigDecimal price = diff.getAskUpdate().getPrice()[i];
      BigDecimal amount = diff.getAskUpdate().getAmount()[i];
      int row = diff.getAskUpdate().getRow()[i];

      this.askPrice[row] = price;
      this.askAmount[row] = amount;
      this.askTotal[row] = price.multiply(amount);
    }

    for (int i = 0, l = diff.getAskInsert().getRow().length; i < l; i++) {
      BigDecimal price = diff.getAskInsert().getPrice()[i];
      BigDecimal amount = diff.getAskInsert().getAmount()[i];
      int row = diff.getAskInsert().getRow()[i];

      this.askPrice = ArrayUtils.add(this.askPrice, row, price);
      this.askAmount = ArrayUtils.add(this.askAmount, row, amount);
      this.askTotal = ArrayUtils.add(this.askTotal, row, price.multiply(amount));
    }

    for (int i = 0, l = diff.getAskDelete().length; i < l; i++) {
      int row = diff.getAskDelete()[i];

      this.askPrice = ArrayUtils.remove(this.askPrice, row);
      this.askAmount = ArrayUtils.remove(this.askAmount, row);
      this.askTotal = ArrayUtils.remove(this.askTotal, row);
    }
  }

}

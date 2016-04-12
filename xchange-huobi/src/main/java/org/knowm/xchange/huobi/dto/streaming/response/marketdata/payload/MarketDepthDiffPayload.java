package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import org.knowm.xchange.huobi.dto.streaming.dto.DepthDiff;
import org.knowm.xchange.huobi.dto.streaming.dto.Percent;
import org.knowm.xchange.huobi.dto.streaming.response.marketdata.MarketDepthDiff;

/**
 * Payload of {@link MarketDepthDiff}
 */
public class MarketDepthDiffPayload extends MarketDepthTopDiffPayload implements DepthDiff {

  private final Percent percent;

  /**
   * 差量行情深度
   *
   * @param symbolId 交易代码
   * @param percent 行情深度百分比
   * @param version 快照版本，方便进行差量更新，如果终端的最后快照版本不一致，则需要重新获取最新完整行情深度。
   * @param versionOld 产生该差量的旧快照版本，方便进行差量更新，如果终端的最后快照版本不一致，则需要重新获取最新完整行情深度。
   * @param bidInsert 委托买单需要添加的行
   * @param bidDelete 委托买单需要删除的行
   * @param bidUpdate 委托买单需要修改的行，以及该行新的委单量。
   * @param askInsert 委托卖单需要添加的行
   * @param askDelete 委托卖单需要删除的行
   * @param askUpdate 委托卖单需要修改的行，以及该行新的委单量。
   */
  public MarketDepthDiffPayload(String symbolId, Percent percent, long version, long versionOld, Update bidInsert, int[] bidDelete, Update bidUpdate,
      Update askInsert, int[] askDelete, Update askUpdate) {
    super(symbolId, version, versionOld, bidInsert, bidDelete, bidUpdate, askInsert, askDelete, askUpdate);
    this.percent = percent;
  }

  public Percent getPercent() {
    return percent;
  }

}

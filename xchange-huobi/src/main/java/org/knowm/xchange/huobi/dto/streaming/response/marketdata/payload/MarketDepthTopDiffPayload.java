package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import org.knowm.xchange.huobi.dto.streaming.dto.DepthDiff;
import org.knowm.xchange.huobi.dto.streaming.response.marketdata.MarketDepthTopDiff;
import org.knowm.xchange.huobi.dto.streaming.response.payload.AbstractPayload;

/**
 * Payload of {@link MarketDepthTopDiff}.
 */
public class MarketDepthTopDiffPayload extends AbstractPayload implements DepthDiff {

  private final String symbolId;

  private final long version;
  private final long versionOld;

  private final Update bidInsert;
  private final int[] bidDelete;
  private final Update bidUpdate;

  private final Update askInsert;
  private final int[] askDelete;
  private final Update askUpdate;

  /**
   * 差量行情深度
   *
   * @param symbolId 交易代码
   * @param version 快照版本，方便进行差量更新，如果终端的最后快照版本不一致，则需要重新获取最新完整行情深度。
   * @param versionOld 产生该差量的旧快照版本，方便进行差量更新，如果终端的最后快照版本不一致，则需要重新获取最新完整行情深度。
   * @param bidInsert 委托买单需要添加的行
   * @param bidDelete 委托买单需要删除的行
   * @param bidUpdate 委托买单需要修改的行，以及该行新的委单量。
   * @param askInsert 委托卖单需要添加的行
   * @param askDelete 委托卖单需要删除的行
   * @param askUpdate 委托卖单需要修改的行，以及该行新的委单量。
   */
  public MarketDepthTopDiffPayload(String symbolId, long version, long versionOld, Update bidInsert, int[] bidDelete, Update bidUpdate,
      Update askInsert, int[] askDelete, Update askUpdate) {
    this.symbolId = symbolId;
    this.version = version;
    this.versionOld = versionOld;
    this.bidInsert = bidInsert;
    this.bidDelete = bidDelete;
    this.bidUpdate = bidUpdate;
    this.askInsert = askInsert;
    this.askDelete = askDelete;
    this.askUpdate = askUpdate;
  }

  public String getSymbolId() {
    return symbolId;
  }

  public long getVersion() {
    return version;
  }

  public long getVersionOld() {
    return versionOld;
  }

  public Update getBidInsert() {
    return bidInsert;
  }

  public int[] getBidDelete() {
    return bidDelete;
  }

  public Update getBidUpdate() {
    return bidUpdate;
  }

  public Update getAskInsert() {
    return askInsert;
  }

  public int[] getAskDelete() {
    return askDelete;
  }

  public Update getAskUpdate() {
    return askUpdate;
  }

}

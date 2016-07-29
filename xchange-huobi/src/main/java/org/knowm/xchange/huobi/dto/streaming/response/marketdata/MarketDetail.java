package org.knowm.xchange.huobi.dto.streaming.response.marketdata;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload.MarketDetailPayload;

/**
 * Push of market detail.
 */
public class MarketDetail extends Message<MarketDetailPayload> {

  private final long _id;
  private final long idCur;
  private final long idPrev;

  public MarketDetail(int version, long _id, String msgType, String symbolId, long idCur, long idPrev, MarketDetailPayload payload) {
    super(version, msgType, symbolId, payload);
    this._id = _id;
    this.idCur = idCur;
    this.idPrev = idPrev;
  }

  public long get_id() {
    return _id;
  }

  public long getIdCur() {
    return idCur;
  }

  public long getIdPrev() {
    return idPrev;
  }

}

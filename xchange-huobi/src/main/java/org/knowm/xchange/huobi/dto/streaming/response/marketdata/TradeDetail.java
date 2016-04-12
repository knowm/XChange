package org.knowm.xchange.huobi.dto.streaming.response.marketdata;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload.TradeDetailPayload;

/**
 * Push of trade details.
 */
public class TradeDetail extends Message<TradeDetailPayload> {

  private final long _id;
  private final long idCur;
  private final long idPrev;
  private final long timeMax;
  private final long timeMin;

  public TradeDetail(int version, long _id, String msgType, String symbolId, long idCur, long idPrev, long timeMax, long timeMin,
      TradeDetailPayload payload) {
    super(version, msgType, symbolId, payload);
    this._id = _id;
    this.idCur = idCur;
    this.idPrev = idPrev;
    this.timeMax = timeMax;
    this.timeMin = timeMin;
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

  public long getTimeMax() {
    return timeMax;
  }

  public long getTimeMin() {
    return timeMin;
  }

}

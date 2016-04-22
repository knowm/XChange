package org.knowm.xchange.huobi.dto.streaming.dto;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload.Update;

public interface DepthDiff {

  long getVersion();

  long getVersionOld();

  Update getBidInsert();

  int[] getBidDelete();

  Update getBidUpdate();

  Update getAskInsert();

  int[] getAskDelete();

  Update getAskUpdate();

}

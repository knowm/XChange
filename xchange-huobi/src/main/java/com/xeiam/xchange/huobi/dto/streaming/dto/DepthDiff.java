package com.xeiam.xchange.huobi.dto.streaming.dto;

import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.payload.Update;

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

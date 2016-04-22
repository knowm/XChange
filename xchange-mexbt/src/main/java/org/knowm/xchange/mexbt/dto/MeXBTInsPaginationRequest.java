package org.knowm.xchange.mexbt.dto;

import org.knowm.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTInsPaginationRequest extends MeXBTInsRequest {

  private final long startIndex;
  private final int count;

  public MeXBTInsPaginationRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest, String ins, long startIndex,
      int count) {
    super(apiKey, nonceFactory, meXBTDigest, ins);
    this.startIndex = startIndex;
    this.count = count;
  }

  public long getStartIndex() {
    return startIndex;
  }

  public int getCount() {
    return count;
  }

}

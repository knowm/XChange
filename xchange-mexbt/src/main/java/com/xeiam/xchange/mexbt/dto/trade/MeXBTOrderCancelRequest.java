package com.xeiam.xchange.mexbt.dto.trade;

import com.xeiam.xchange.mexbt.dto.MeXBTInsRequest;
import com.xeiam.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTOrderCancelRequest extends MeXBTInsRequest {

  private final String serverOrderId;

  public MeXBTOrderCancelRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest, String ins,
      String serverOrderId) {
    super(apiKey, nonceFactory, meXBTDigest, ins);
    this.serverOrderId = serverOrderId;
  }

  protected String getServerOrderId() {
    return serverOrderId;
  }

}

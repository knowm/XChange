package org.knowm.xchange.mexbt.dto.trade;

import org.knowm.xchange.mexbt.dto.MeXBTInsRequest;
import org.knowm.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTOrderModifyRequest extends MeXBTInsRequest {

  private final String serverOrderId;
  private final int modifyAction;

  public MeXBTOrderModifyRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest, String ins,
      String serverOrderId, int modifyAction) {
    super(apiKey, nonceFactory, meXBTDigest, ins);
    this.serverOrderId = serverOrderId;
    this.modifyAction = modifyAction;
  }

  protected String getServerOrderId() {
    return serverOrderId;
  }

  protected int getModifyAction() {
    return modifyAction;
  }

}

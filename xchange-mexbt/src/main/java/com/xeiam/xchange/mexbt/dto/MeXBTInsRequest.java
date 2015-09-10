package com.xeiam.xchange.mexbt.dto;

import com.xeiam.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTInsRequest extends MeXBTRequest {

  private final String ins;

  public MeXBTInsRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest, String ins) {
    super(apiKey, nonceFactory, meXBTDigest);
    this.ins = ins;
  }

  public String getIns() {
    return ins;
  }

}

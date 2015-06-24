package com.xeiam.xchange.mexbt.dto.account;

import java.math.BigDecimal;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.mexbt.dto.MeXBTInsRequest;
import com.xeiam.xchange.mexbt.service.MeXBTDigest;

public class MeXBTWithdrawRequest extends MeXBTInsRequest {

  private final BigDecimal amount;
  private final String sendToAddress;

  public MeXBTWithdrawRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest, String ins, BigDecimal amount, String sendToAddress) {
    super(apiKey, nonceFactory, meXBTDigest, ins);
    this.amount = amount;
    this.sendToAddress = sendToAddress;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getSendToAddress() {
    return sendToAddress;
  }

}

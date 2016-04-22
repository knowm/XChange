package org.knowm.xchange.mexbt.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.knowm.xchange.mexbt.dto.BigDecimalAsStringSerializer;
import org.knowm.xchange.mexbt.dto.MeXBTInsRequest;
import org.knowm.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTWithdrawRequest extends MeXBTInsRequest {

  private final BigDecimal amount;
  private final String sendToAddress;

  public MeXBTWithdrawRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest, String ins, BigDecimal amount,
      String sendToAddress) {
    super(apiKey, nonceFactory, meXBTDigest, ins);
    this.amount = amount;
    this.sendToAddress = sendToAddress;
  }

  @JsonSerialize(using = BigDecimalAsStringSerializer.class)
  public BigDecimal getAmount() {
    return amount;
  }

  public String getSendToAddress() {
    return sendToAddress;
  }

}

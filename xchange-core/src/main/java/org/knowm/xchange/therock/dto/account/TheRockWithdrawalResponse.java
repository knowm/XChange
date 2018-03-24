package org.knowm.xchange.therock.dto.account;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class TheRockWithdrawalResponse {

  private Integer transactionId;

  public Integer getTransactionId() {
    return transactionId;
  }

  @Override
  public String toString() {
    return String.format("TheRockWithdrawalResponse{transactionId=%d}", transactionId);
  }
}

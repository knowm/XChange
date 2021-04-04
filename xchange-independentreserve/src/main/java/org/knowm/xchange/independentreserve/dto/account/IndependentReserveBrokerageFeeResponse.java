package org.knowm.xchange.independentreserve.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;

public class IndependentReserveBrokerageFeeResponse {
  List<IndependentReserveBrokerageFee> independentReserveBrokerageFees;

  @JsonCreator
  public IndependentReserveBrokerageFeeResponse(
      List<IndependentReserveBrokerageFee> independentReserveBrokerageFees) {
    this.independentReserveBrokerageFees = independentReserveBrokerageFees;
  }

  public List<IndependentReserveBrokerageFee> getIndependentReserveBrokerageFees() {
    return independentReserveBrokerageFees;
  }
}

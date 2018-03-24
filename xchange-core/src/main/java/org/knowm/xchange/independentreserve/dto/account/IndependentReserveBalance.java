package org.knowm.xchange.independentreserve.dto.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveBalance {
  private final List<IndependentReserveAccount> independentReserveAccounts;

  @JsonCreator
  public IndependentReserveBalance(List<IndependentReserveAccount> independentReserveAccounts) {
    this.independentReserveAccounts = independentReserveAccounts;
  }

  public List<IndependentReserveAccount> getIndependentReserveAccounts() {
    return independentReserveAccounts;
  }
}

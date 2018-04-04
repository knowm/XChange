package org.knowm.xchange.independentreserve.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;

/** Author: Kamil Zbikowski Date: 4/10/15 */
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

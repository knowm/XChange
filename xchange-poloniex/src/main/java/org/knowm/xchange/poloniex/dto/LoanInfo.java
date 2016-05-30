package org.knowm.xchange.poloniex.dto;

import org.knowm.xchange.dto.LoanOrder;

import java.util.List;

/**
 * <p>
 * DTO representing loan information
 * </p>
 */
public final class LoanInfo {

  /**
   * Provided loans
   */
  private final List<LoanOrder> providedLoans;

  /**
   * Used loans
   */
  private final List<LoanOrder> usedLoans;


  /**
   * Constructs an {@link LoanInfo}.
   *
   * @param providedLoans provided loans.
   * @param usedLoans used loans.
   */
  public LoanInfo(List<LoanOrder> providedLoans, List<LoanOrder> usedLoans) {
    this.providedLoans = providedLoans;
    this.usedLoans = usedLoans;
  }

  public List<LoanOrder> getProvidedLoans() {
    return providedLoans;
  }

  public List<LoanOrder> getUsedLoans() {
    return usedLoans;
  }

}

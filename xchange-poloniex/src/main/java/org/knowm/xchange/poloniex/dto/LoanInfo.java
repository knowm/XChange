package org.knowm.xchange.poloniex.dto;

import java.util.List;

import org.knowm.xchange.dto.LoanOrder;

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
   * Opened loans
   */
  private final List<LoanOrder> openedLoans;

  /**
   * Constructs an {@link LoanInfo}.
   *
   * @param providedLoans provided loans.
   * @param usedLoans used loans.
   */
  public LoanInfo(List<LoanOrder> providedLoans, List<LoanOrder> usedLoans, List<LoanOrder> openedLoans) {
    this.providedLoans = providedLoans;
    this.usedLoans = usedLoans;
    this.openedLoans = openedLoans;
  }

  public List<LoanOrder> getProvidedLoans() {
    return providedLoans;
  }

  public List<LoanOrder> getUsedLoans() {
    return usedLoans;
  }

  public List<LoanOrder> getOpenedLoans() {
    return openedLoans;
  }

}

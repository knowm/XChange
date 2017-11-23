package org.knowm.xchange.vircurex.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VircurexAccountInfoReturn {

  private final int status;

  private final String account;

  private final Map<String, Map<String, BigDecimal>> availableFunds;

  /**
   * Constructor
   *
   * @param aStatus
   * @param anAccount
   * @param someBalances
   */
  public VircurexAccountInfoReturn(@JsonProperty("status") int aStatus, @JsonProperty("account") String anAccount,
      @JsonProperty("balances") Map<String, Map<String, BigDecimal>> someBalances) {

    availableFunds = someBalances;
    status = aStatus;
    account = anAccount;
  }

  public int getStatus() {

    return status;
  }

  public String getAccount() {

    return account;
  }

  public Map<String, Map<String, BigDecimal>> getAvailableFunds() {

    return availableFunds;
  }

}

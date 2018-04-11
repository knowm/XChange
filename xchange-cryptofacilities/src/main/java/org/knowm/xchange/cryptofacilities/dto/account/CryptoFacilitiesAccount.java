package org.knowm.xchange.cryptofacilities.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Panchen */
public class CryptoFacilitiesAccount extends CryptoFacilitiesResult {

  private final CryptoFacilitiesAccountInfo accountInfo;

  public CryptoFacilitiesAccount(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("account") CryptoFacilitiesAccountInfo accountInfo) {

    super(result, error);

    this.accountInfo = accountInfo;
  }

  public Map<String, BigDecimal> getBalances() {
    return accountInfo.getBalances();
  }

  public Map<String, BigDecimal> getAuxiliary() {
    return accountInfo.getAuxiliary();
  }

  public Map<String, BigDecimal> getMarginRequirements() {
    return accountInfo.getMarginRequirements();
  }

  public Map<String, BigDecimal> getTriggerEstimates() {
    return accountInfo.getTriggerEstimates();
  }
}

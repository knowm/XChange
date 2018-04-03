package org.knowm.xchange.cryptofacilities.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.cryptofacilities.dto.CryptoFacilitiesResult;

/** @author Panchen */
public class CryptoFacilitiesAccounts extends CryptoFacilitiesResult {

  private final Map<String, CryptoFacilitiesAccountInfo> accounts;

  public CryptoFacilitiesAccounts(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") String strServerTime,
      @JsonProperty("error") String error,
      @JsonProperty("accounts") Map<String, CryptoFacilitiesAccountInfo> accounts) {

    super(result, error);

    this.accounts = accounts;
  }

  public Map<String, CryptoFacilitiesAccountInfo> getAccounts() {
    return accounts;
  }
}

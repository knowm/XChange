package org.knowm.xchange.jubi.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dzf on 2017/7/7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JubiBalance {
  private final String uid;
  private final BigDecimal asset;
  private final boolean result;
  private final String code;
  private final int mobileFlag;
  private final int nameAuthorized;
  private final Map<String, BigDecimal> availableFunds;
  private final Map<String, BigDecimal> lockedFunds;

  public JubiBalance(@JsonProperty("uid") String uid,
                     @JsonProperty("asset") BigDecimal asset,
                     @JsonProperty("result") boolean result,
                     @JsonProperty("code") String code,
                     @JsonProperty("moflag") int mobileFlag,
                     @JsonProperty("nameauth") int nameAuthorized) {
    this.uid = uid;
    this.asset = asset;
    this.result = result;
    this.code = code;
    this.mobileFlag = mobileFlag;
    this.nameAuthorized = nameAuthorized;
    this.availableFunds = new HashMap<>();
    this.lockedFunds = new HashMap<>();
  }

  public String getUid() {
    return uid;
  }

  public BigDecimal getAsset() {
    return asset;
  }

  public boolean isResult() {
    return result;
  }

  public String getCode() {
    return code;
  }

  public int getMobileFlag() {
    return mobileFlag;
  }

  public int getNameAuthorized() {
    return nameAuthorized;
  }

  public Map<String, BigDecimal> getAvailableFunds() {
    return availableFunds;
  }

  public Map<String, BigDecimal> getLockedFunds() {
    return lockedFunds;
  }

  @JsonAnySetter
  public void set(String name, String value) throws NotYetImplementedForExchangeException {
    if (name.endsWith("_balance")) {
      availableFunds.put(name.substring(0, name.indexOf("_balance")), new BigDecimal(value));
    } else if (name.endsWith("_lock")) {
      lockedFunds.put(name.substring(0, name.indexOf("_lock")), new BigDecimal(value));
    } else {
      throw new NotYetImplementedForExchangeException(String.format("Unsupported balance data: %s=%s", name, value));
    }
  }

  @Override
  public String toString() {
    return String.format("JubiBalance: {uid=%s, asset=%s, result=%b, code=%s, mobileFlag=%d, nameAuthorized=%d, availableFunds=%s, lockedFunds=%s}",
            uid, asset, result, code, mobileFlag, nameAuthorized, availableFunds, lockedFunds);
  }
}

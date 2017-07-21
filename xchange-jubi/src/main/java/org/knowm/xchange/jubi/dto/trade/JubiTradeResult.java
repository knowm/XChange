package org.knowm.xchange.jubi.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Dzf on 2017/7/16.
 */
public class JubiTradeResult {
  private final boolean success;
  private final int errorCode;
  private final BigDecimal id;

  @JsonCreator
  public JubiTradeResult(@JsonProperty("result") final boolean result,
                         @JsonProperty("code") final int errorCode,
                         @JsonProperty("id") final  BigDecimal id) {
    this.success = result;
    this.errorCode = errorCode;
    this.id = id;
  }

  public BigDecimal getId() {
    return id;
  }

  public boolean isSuccess() {
    return success;
  }

  public int getErrorCode() {
    return errorCode;
  }

  @Override
  public String toString() {
    return String.format("JubiTradeResult{success=%s, errorCode=%s}", success, errorCode);
  }
}

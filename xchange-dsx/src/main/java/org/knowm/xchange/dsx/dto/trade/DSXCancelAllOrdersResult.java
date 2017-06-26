package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCancelAllOrdersResult {

  private final Map<String, BigDecimal> funds;

  public DSXCancelAllOrdersResult(@JsonProperty("funds") Map<String, BigDecimal> funds) {

    this.funds = funds;
  }

  public Map<String, BigDecimal> getFunds() {

    return funds;
  }

  @Override
  public String toString() {
    return "DSXCancelAllOrdersResult{" +
        "funds=" + funds +
        '}';
  }
}

package org.knowm.xchange.dsx.dto.trade;

import java.util.Map;

import org.knowm.xchange.dsx.dto.account.DSXCurrencyAmount;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCancelAllOrdersResult {

  private final Map<String, DSXCurrencyAmount> funds;

  public DSXCancelAllOrdersResult(@JsonProperty("funds") Map<String, DSXCurrencyAmount> funds) {

    this.funds = funds;
  }

  public Map<String, DSXCurrencyAmount> getFunds() {

    return funds;
  }

  @Override
  public String toString() {
    return "DSXCancelAllOrdersResult{" +
        "funds=" + funds +
        '}';
  }
}

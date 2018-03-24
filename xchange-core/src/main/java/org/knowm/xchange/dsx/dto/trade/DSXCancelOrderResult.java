package org.knowm.xchange.dsx.dto.trade;

import java.text.MessageFormat;
import java.util.Map;

import org.knowm.xchange.dsx.dto.account.DSXCurrencyAmount;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCancelOrderResult {

  private final Map<String, DSXCurrencyAmount> funds;
  private final long orderId;

  public DSXCancelOrderResult(@JsonProperty("funds") Map<String, DSXCurrencyAmount> funds, @JsonProperty("orderId") long orderId) {

    this.funds = funds;
    this.orderId = orderId;
  }

  public Map<String, DSXCurrencyAmount> getFunds() {

    return funds;
  }

  public long getOrderId() {

    return orderId;
  }

  @Override
  public String toString() {

    return MessageFormat.format("DSXCancelOrderResult[orderId={0}, funds={1}]", orderId, funds);
  }
}

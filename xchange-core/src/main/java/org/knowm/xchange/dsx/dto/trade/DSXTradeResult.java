package org.knowm.xchange.dsx.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

import org.knowm.xchange.dsx.dto.account.DSXCurrencyAmount;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTradeResult {

  private final long orderId;
  private final BigDecimal received;
  private final BigDecimal remains;
  private final Map<String, DSXCurrencyAmount> funds;

  public DSXTradeResult(@JsonProperty("orderId") long orderId, @JsonProperty("received") BigDecimal received,
      @JsonProperty("remains") BigDecimal remains, @JsonProperty("funds") Map<String, DSXCurrencyAmount> funds) {

    this.orderId = orderId;
    this.received = received;
    this.remains = remains;
    this.funds = funds;
  }

  public long getOrderId() {

    return orderId;
  }

  public BigDecimal getReceived() {

    return received;
  }

  public BigDecimal getRemains() {

    return remains;
  }

  public Map<String, DSXCurrencyAmount> getFunds() {

    return funds;
  }

  @Override
  public String toString() {

    return MessageFormat.format("DSXTrade[orderId={0}, received={1}, remains={2}, funds={3}]", orderId, received, remains, funds);
  }
}

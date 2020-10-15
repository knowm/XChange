package org.knowm.xchange.coindcx.dto;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * Gemini order response contains details of any trades that have just executed in the order entry
 * return value. If a LimitOrder of this type is supplied to the trade service orderEntry method it
 * will be populated with this information.
 */
public class CoindcxLimitOrder extends LimitOrder {

  /** */
  private static final long serialVersionUID = 1L;

  private CoindcxOrderStatusResponse response = null;

  public CoindcxLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal limitPrice) {
    super(type, originalAmount, currencyPair, id, timestamp, limitPrice);
  }

  public CoindcxOrderStatusResponse getResponse() {
    return response;
  }

  public void setResponse(CoindcxOrderStatusResponse value) {
    response = value;
  }

  public static class Builder extends LimitOrder.Builder {

    public Builder(OrderType orderType, CurrencyPair currencyPair) {
      super(orderType, currencyPair);
    }

    public CoindcxLimitOrder build() {
      final CoindcxLimitOrder order =
          new CoindcxLimitOrder(
              orderType, originalAmount, (CurrencyPair) instrument, id, timestamp, limitPrice);
      order.setOrderFlags(flags);
      return order;
    }
  }
}

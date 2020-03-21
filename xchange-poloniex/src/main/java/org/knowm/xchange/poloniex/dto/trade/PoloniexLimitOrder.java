package org.knowm.xchange.poloniex.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

/**
 * Poloniex order response contains details of any trades that have just executed in the order entry
 * return value. If a LimitOrder of this type is supplied to the trade service orderEntry method it
 * will be populated with this information.
 */
public class PoloniexLimitOrder extends LimitOrder {

  private PoloniexTradeResponse response = null;

  public PoloniexLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      Instrument currencyPair,
      String id,
      Date timestamp,
      BigDecimal limitPrice) {
    super(type, originalAmount, currencyPair, id, timestamp, limitPrice);
  }

  public PoloniexTradeResponse getResponse() {
    return response;
  }

  public void setResponse(PoloniexTradeResponse value) {
    response = value;
  }

  public static class Builder extends LimitOrder.Builder {

    public Builder(OrderType orderType, CurrencyPair currencyPair) {
      super(orderType, currencyPair);
    }

    public PoloniexLimitOrder build() {
      final PoloniexLimitOrder order =
          new PoloniexLimitOrder(orderType, originalAmount, instrument, id, timestamp, limitPrice);
      order.setOrderFlags(flags);
      return order;
    }
  }
}

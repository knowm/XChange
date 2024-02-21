package org.knowm.xchange.gateio.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

@Value
public class GateioUserTrade extends UserTrade {

  Role role;

  /**
   * @param type               The trade type (BID side or ASK side)
   * @param originalAmount     The depth of this trade
   * @param instrument         The exchange identifier (e.g. "BTC/USD")
   * @param price              The price (either the bid or the ask)
   * @param timestamp          The timestamp of the trade
   * @param id                 The id of the trade
   * @param orderId            The id of the order responsible for execution of this trade
   * @param feeAmount          The fee that was charged by the exchange for this trade
   * @param feeCurrency        The symbol of the currency in which the fee was charged
   * @param orderUserReference The id that the user has insert to the trade
   * @param role               Trade role
   */
  public GateioUserTrade(OrderType type,
      BigDecimal originalAmount, Instrument instrument,
      BigDecimal price, Date timestamp, String id, String orderId,
      BigDecimal feeAmount, Currency feeCurrency,
      String orderUserReference, Role role) {
    super(type, originalAmount, instrument, price, timestamp, id, orderId, feeAmount, feeCurrency,
        orderUserReference);
    this.role = role;
  }


}

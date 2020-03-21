package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public interface OpenOrdersParamInstrument extends OpenOrdersParams {

  @Override
  default boolean accept(LimitOrder order) {

    return accept((Order) order);
  }

  @Override
  default boolean accept(Order order) {

    return order != null
        && (getInstrument() == null || getInstrument().equals(order.getInstrument()));
  }

  Instrument getInstrument();

  void setInstrument(Instrument instrument);

  /**
   * @deprecated CurrencyPair is a subtype of Instrument <br>
   *     use {@link #getInstrument()} instead like this:
   *     <blockquote>
   *     <pre>
   * getInstrument()
   * </pre>
   *     </blockquote>
   */
  @Deprecated
  default CurrencyPair getCurrencyPair() {
    if (getInstrument() instanceof CurrencyPair) return (CurrencyPair) getInstrument();
    return null;
  };

  /**
   * @param pair
   * @deprecated CurrencyPair is a subtype of Instrument <br>
   *     use {@link #setInstrument(instrument)} instead like this:
   *     <blockquote>
   *     <pre>
   * setInstrument(instrument)
   * </pre>
   *     </blockquote>
   */
  @Deprecated
  default void setCurrencyPair(CurrencyPair pair) {
    setInstrument(pair);
  }
}

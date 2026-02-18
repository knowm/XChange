package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.instrument.Instrument;

/** Data object representing a Trade */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Trade implements Serializable {

  private static final long serialVersionUID = -4078893146776655648L;

  /** Did this trade result from the execution of a bid or a ask? */
  protected OrderType type;

  /** Amount that was traded */
  protected BigDecimal originalAmount;

  /** The instrument */
  protected Instrument instrument;

  /** The price */
  protected BigDecimal price;

  /** The timestamp of the trade according to the exchange's server, null if not provided */
  protected Date timestamp;

  /** The trade id */
  protected String id;

  protected String makerOrderId;

  protected String takerOrderId;

  /**
   * @deprecated CurrencyPair is a subtype of Instrument - this method will throw an exception if
   *     the order was for a derivative
   *     <p>use {@link #getInstrument()} instead
   */
  @Deprecated
  @JsonIgnore
  public CurrencyPair getCurrencyPair() {
    if (instrument == null) {
      return null;
    }
    if (!(instrument instanceof CurrencyPair)) {
      throw new IllegalStateException(
          "The instrument of this order is not a currency pair: " + instrument);
    }
    return (CurrencyPair) instrument;
  }
}

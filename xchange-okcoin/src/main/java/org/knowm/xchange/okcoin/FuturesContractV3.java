package org.knowm.xchange.okcoin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

/**
 * Value object to provide the following to API:
 *
 * <ul>
 *   <li>Provision of major currency symbol pairs (EUR/USD, GBP/USD etc)
 *   <li>Provision of arbitrary symbol pairs for exchange index trading, notional currencies etc
 * </ul>
 *
 * <p>Symbol pairs are quoted, for example, as EUR/USD 1.25 such that 1 EUR can be purchased with
 * 1.25 USD
 */
public class FuturesContractV3 extends Instrument
    implements Comparable<FuturesContractV3>, Serializable {

  public final FuturesContract prompt;
  public final CurrencyPair underlyier;

  @JsonCreator
  public FuturesContractV3(CurrencyPair underlyier, FuturesContract prompt) {

    this.prompt = prompt;
    this.underlyier = underlyier;
  }

  /**
   * String constructor In general the CurrencyPair.base is what you're wanting to buy/sell. The
   * CurrencyPair.counter is what currency you want to use to pay/receive for your purchase/sale.
   *
   * @param baseSymbol The base symbol is what you're wanting to buy/sell
   * @param counterSymbol The counter symbol is what currency you want to use to pay/receive for
   *     your purchase/sale.
   */
  /**
   * Parse currency pair from a string in the same format as returned by toString() method - ABC/XYZ
   */
  @JsonValue
  @Override
  public String toString() {

    return underlyier + "/" + prompt;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((underlyier == null) ? 0 : underlyier.hashCode());
    result = prime * result + ((prompt == null) ? 0 : prompt.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    FuturesContractV3 other = (FuturesContractV3) obj;
    if (prompt == null) {
      if (other.prompt != null) {
        return false;
      }
    } else if (!prompt.equals(other.prompt)) {
      return false;
    }

    if (underlyier == null) {
      if (other.underlyier != null) {
        return false;
      }
    } else if (!underlyier.equals(other.underlyier)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(FuturesContractV3 o) {

    FuturesContractV3 c = (FuturesContractV3) o;
    return prompt.compareTo(c.prompt);
  }
}

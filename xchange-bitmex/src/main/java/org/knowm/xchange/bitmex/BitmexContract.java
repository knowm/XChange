package org.knowm.xchange.bitmex;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

/** Delivery dates for future date currencies */
@JsonSerialize(using = BitmexUtils.CustomBitmexContractSerializer.class)
public class BitmexContract implements Comparable<BitmexContract>, Serializable {

  public final BitmexPrompt prompt;
  public final CurrencyPair pair;

  public BitmexContract(CurrencyPair pair, BitmexPrompt prompt) {

    this.prompt = prompt;
    this.pair = pair;
    // TODO Auto-generated constructor stub
  }

  @Override
  public String toString() {

    return pair.base + "/" + pair.counter + "/" + prompt;
  }

  public boolean contains(Currency currency) {

    return pair.base.equals(currency) || pair.counter.equals(currency);
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((pair.base == null) ? 0 : pair.base.hashCode());
    result = prime * result + ((pair.counter == null) ? 0 : pair.counter.hashCode());
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
    BitmexContract other = (BitmexContract) obj;
    if (pair.base == null) {
      if (other.pair.base != null) {
        return false;
      }
    } else if (!pair.base.equals(other.pair.base)) {
      return false;
    }
    if (pair.counter == null) {
      if (other.pair.counter != null) {
        return false;
      }
    } else if (!pair.counter.equals(other.pair.counter)) {
      return false;
    }
    if (prompt == null) {
      if (other.prompt != null) {
        return false;
      }
    } else if (!prompt.equals(other.prompt)) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(BitmexContract o) {

    return (pair.base.compareTo(o.pair.base) << 16)
        + pair.counter.compareTo(o.pair.counter)
        + prompt.compareTo(o.prompt);
  }
}

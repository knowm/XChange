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

    return pair.getBase() + "/" + pair.getCounter() + "/" + prompt;
  }

  public boolean contains(Currency currency) {

    return pair.getBase().equals(currency) || pair.getCounter().equals(currency);
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((pair.getBase() == null) ? 0 : pair.getBase().hashCode());
    result = prime * result + ((pair.getCounter() == null) ? 0 : pair.getCounter().hashCode());
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
    if (pair.getBase() == null) {
      if (other.pair.getBase() != null) {
        return false;
      }
    } else if (!pair.getBase().equals(other.pair.getBase())) {
      return false;
    }
    if (pair.getCounter() == null) {
      if (other.pair.getCounter() != null) {
        return false;
      }
    } else if (!pair.getCounter().equals(other.pair.getCounter())) {
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

    return (pair.getBase().compareTo(o.pair.getBase()) << 16)
        + pair.getCounter().compareTo(o.pair.getCounter())
        + prompt.compareTo(o.prompt);
  }
}

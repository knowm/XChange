package org.knowm.xchange.btcturk.dto;

import java.util.Objects;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/**
 * @author mertguner
 */
public class BTCTurkPair {
  public final CurrencyPair pair;

  public BTCTurkPair(CurrencyPair pair) {
    this.pair = pair;
  }

  public BTCTurkPair(String pair) {
    this(CurrencyPairDeserializer.getCurrencyPairFromString(pair));
  }

  @Override
  public boolean equals(Object o) {
    return this == o
        || !(o == null || getClass() != o.getClass())
            && Objects.equals(pair, ((BTCTurkPair) o).pair);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pair);
  }

  @Override
  public String toString() {
    return pair == null
        ? ""
        : String.format("%s%s", pair.getBase().getCurrencyCode(), pair.getCounter().getCurrencyCode());
  }
}

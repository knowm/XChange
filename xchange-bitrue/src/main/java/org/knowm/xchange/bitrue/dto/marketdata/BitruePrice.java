package org.knowm.xchange.bitrue.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.Assert;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import java.math.BigDecimal;

public final class BitruePrice implements Comparable<BitruePrice> {

  private final CurrencyPair pair;
  private final BigDecimal price;

  public BitruePrice(
      @JsonProperty("symbol") String symbol, @JsonProperty("price") BigDecimal price) {
    this(CurrencyPairDeserializer.getCurrencyPairFromString(symbol), price);
  }

  public BitruePrice(CurrencyPair pair, BigDecimal price) {
    Assert.notNull(price, "Null price");
    Assert.notNull(pair, "Null pair");
    this.pair = pair;
    this.price = price;
  }

  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public int compareTo(BitruePrice o) {
    if (pair.compareTo(o.pair) == 0) return price.compareTo(o.price);
    return pair.compareTo(o.pair);
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + ((pair == null) ? 0 : pair.hashCode());
    result = 31 * result + ((price == null) ? 0 : price.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof BitruePrice)) return false;
    BitruePrice other = (BitruePrice) obj;
    return pair.equals(other.pair) && price.equals(other.price);
  }

  @Override
  public String toString() {
    return "[" + pair + "] => " + price;
  }
}

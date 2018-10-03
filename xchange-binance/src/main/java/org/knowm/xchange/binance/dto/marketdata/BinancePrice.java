package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.Assert;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public final class BinancePrice implements Comparable<BinancePrice> {

  private final CurrencyPair pair;
  private final BigDecimal price;

  public BinancePrice(
      @JsonProperty("symbol") String symbol, @JsonProperty("price") BigDecimal price) {
    this(CurrencyPairDeserializer.getCurrencyPairFromString(symbol), price);
  }

  public BinancePrice(CurrencyPair pair, BigDecimal price) {
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
  public int compareTo(BinancePrice o) {
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
    if (!(obj instanceof BinancePrice)) return false;
    BinancePrice other = (BinancePrice) obj;
    return pair.equals(other.pair) && price.equals(other.price);
  }

  public String toString() {
    return "[" + pair + "] => " + price;
  }
}

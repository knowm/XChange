package info.bitrich.xchangestream.binance;

import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.Map;
import java.util.Set;

public class KlineSubscription {
  private final Map<CurrencyPair, Set<KlineInterval>> klines;

  public KlineSubscription(Map<CurrencyPair, Set<KlineInterval>> klines) {this.klines = klines;}

  public Map<CurrencyPair, Set<KlineInterval>> getKlines() {
    return klines;
  }

  public boolean isEmpty() {
    return klines.values().stream().allMatch(Set::isEmpty);
  }

  public boolean hasUnauthenticated() {
    return !isEmpty();
  }
}

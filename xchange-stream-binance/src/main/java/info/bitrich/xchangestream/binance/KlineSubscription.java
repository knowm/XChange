package info.bitrich.xchangestream.binance;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.instrument.Instrument;

public class KlineSubscription {
  private final Map<Instrument, Set<KlineInterval>> klines;

  public KlineSubscription(Map<Instrument, Set<KlineInterval>> klines) {
    this.klines = klines;
  }

  public Map<Instrument, Set<KlineInterval>> getKlines() {
    return klines;
  }

  public boolean isEmpty() {
    return klines.values().stream().allMatch(Set::isEmpty);
  }

  public boolean hasUnauthenticated() {
    return !isEmpty();
  }

  public boolean contains(Instrument instrument, KlineInterval interval) {
    return Optional.ofNullable(klines.get(instrument))
        .filter(intervals -> intervals.contains(interval))
        .isPresent();
  }
}

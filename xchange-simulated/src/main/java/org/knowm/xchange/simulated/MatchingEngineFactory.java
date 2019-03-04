package org.knowm.xchange.simulated;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import org.knowm.xchange.currency.CurrencyPair;

public final class MatchingEngineFactory {

  public static final MatchingEngineFactory INSTANCE = new MatchingEngineFactory();

  private final ConcurrentMap<CurrencyPair, MatchingEngine> engines = new ConcurrentHashMap<>();

  public MatchingEngine create(CurrencyPair currencyPair, int priceScale, Consumer<Fill> onFill) {
    return engines.computeIfAbsent(currencyPair,
        pair -> new MatchingEngine(pair, priceScale, onFill));
  }

  public MatchingEngine create(CurrencyPair currencyPair, int priceScale) {
    return engines.computeIfAbsent(currencyPair,
        pair -> new MatchingEngine(pair, priceScale));
  }
}

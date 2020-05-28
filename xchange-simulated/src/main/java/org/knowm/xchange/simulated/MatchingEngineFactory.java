package org.knowm.xchange.simulated;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Represents a single virtual cryptocurrency exchange - effectively a set of order books for each
 * currency where trades can be placed as maker orders and taker orders can be matched.
 *
 * <p>If shared between instances of {@link SimulatedExchange}, this ensures that all users will be
 * trading against the same order books and thus each other.
 *
 * @author Graham Crockford
 */
public class MatchingEngineFactory {

  private final ConcurrentMap<CurrencyPair, MatchingEngine> engines = new ConcurrentHashMap<>();

  private final AccountFactory accountFactory;

  public MatchingEngineFactory(AccountFactory accountFactory) {
    this.accountFactory = accountFactory;
  }

  MatchingEngine create(
      CurrencyPair currencyPair, int priceScale, BigDecimal minimumAmount, Consumer<Fill> onFill) {
    return engines.computeIfAbsent(
        currencyPair,
        pair -> new MatchingEngine(accountFactory, pair, priceScale, minimumAmount, onFill));
  }

  MatchingEngine create(CurrencyPair currencyPair, int priceScale, BigDecimal minimumAmount) {
    return engines.computeIfAbsent(
        currencyPair, pair -> new MatchingEngine(accountFactory, pair, priceScale, minimumAmount));
  }

  Collection<MatchingEngine> engines() {
    return engines.values();
  }
}

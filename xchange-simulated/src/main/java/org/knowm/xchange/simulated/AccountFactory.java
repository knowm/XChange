package org.knowm.xchange.simulated;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.knowm.xchange.ExchangeSpecification;

/**
 * An instance of {@link AccountFactory} represents a single set of user accounts. A user account is
 * identified by its {@link ExchangeSpecification#getApiKey()} and consist of a set of per-currency
 * balances.
 *
 * <p>If shared between {@link SimulatedExchange} instances, this ensures that they all share the
 * same scope of user accounts.
 *
 * @author Graham Crockford
 */
public class AccountFactory {

  private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

  Account get(String apiKey) {
    return accounts.computeIfAbsent(apiKey, key -> new Account());
  }
}

package org.knowm.xchange.simulated;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AccountFactory {

  private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

  Account get(String apiKey) {
    return accounts.computeIfAbsent(apiKey, key -> new Account());
  }
}

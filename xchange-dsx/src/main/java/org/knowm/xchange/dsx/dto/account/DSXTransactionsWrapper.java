package org.knowm.xchange.dsx.dto.account;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Mikhail Wall
 */

public class DSXTransactionsWrapper {

  private final Map<String, DSXTransaction[]> transactionsMap;

  @JsonCreator
  public DSXTransactionsWrapper(Map<String, DSXTransaction[]> transactionsMap) {

    this.transactionsMap = transactionsMap;
  }

  public Map<String, DSXTransaction[]> getTransactionsMap() {

    return transactionsMap;
  }

  public DSXTransaction[] getTransactions(String pair) {

    DSXTransaction[] result = null;
    if (transactionsMap.containsKey(pair)) {
      result = transactionsMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {
    return "DSXTransactionsWrapper{" +
        "transactionsMap=" + transactionsMap +
        '}';
  }
}

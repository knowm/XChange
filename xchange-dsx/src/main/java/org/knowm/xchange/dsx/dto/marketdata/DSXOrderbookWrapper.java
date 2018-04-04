package org.knowm.xchange.dsx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

/** @author Mikhail Wall */
public class DSXOrderbookWrapper {

  private final Map<String, DSXOrderbook> orderbookMap;

  @JsonCreator
  public DSXOrderbookWrapper(Map<String, DSXOrderbook> result) {

    this.orderbookMap = result;
  }

  public Map<String, DSXOrderbook> getOrderbookMap() {

    return orderbookMap;
  }

  public DSXOrderbook getOrderbook(String pair) {

    DSXOrderbook result = null;
    if (orderbookMap.containsKey(pair)) {
      result = orderbookMap.get(pair);
    }
    return result;
  }

  @Override
  public String toString() {

    return "DSXOrderbookWrapper{" + "orderbookMap=" + orderbookMap.toString() + '}';
  }
}

package org.knowm.xchange.independentreserve.util;

/**
 * Author: Kamil Zbikowski Date: 4/13/15
 */
public enum ExchangeEndpoint {
  GET_ACCOUNTS("GetAccounts"), GET_OPEN_ORDERS("GetOpenOrders"), PLACE_LIMIT_ORDER("PlaceLimitOrder"), CANCEL_ORDER("CancelOrder")
  , GET_TRADES("GetTrades"), GET_TRANSACTIONS("GetTransactions");

  private String endpointName;

  ExchangeEndpoint(String endpointName) {
    this.endpointName = endpointName;
  }

  public final static String getUrlBasingOnEndpoint(String sslUri, ExchangeEndpoint endpoint) {
    return sslUri + "/Private/" + endpoint.endpointName;

  }
}

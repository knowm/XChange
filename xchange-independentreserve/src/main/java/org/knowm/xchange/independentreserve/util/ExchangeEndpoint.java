package org.knowm.xchange.independentreserve.util;

import org.knowm.xchange.independentreserve.IndependentReserveAuthenticated;

/** Author: Kamil Zbikowski Date: 4/13/15 */
public enum ExchangeEndpoint {
  GET_ACCOUNTS("GetAccounts"),
  GET_OPEN_ORDERS("GetOpenOrders"),
  GET_DIGITAL_CURRENCY_DEPOSIT_ADDRESS(
      IndependentReserveAuthenticated.GetDigitalCurrencyDepositAddress),
  PLACE_LIMIT_ORDER("PlaceLimitOrder"),
  PLACE_MARKET_ORDER("PlaceMarketOrder"),
  CANCEL_ORDER("CancelOrder"),
  GET_TRADES("GetTrades"),
  GET_TRANSACTIONS("GetTransactions"),
  GET_ORDER_DETAILS("GetOrderDetails"),
  SYNCH_DIGITAL_CURRENCY_DEPOSIT_ADDRESS_WITH_BLOCKCHAIN(
      IndependentReserveAuthenticated.SynchDigitalCurrencyDepositAddressWithBlockchain),
  WITHDRAW_DIGITAL_CURRENCY(IndependentReserveAuthenticated.WithdrawDigitalCurrency);

  private String endpointName;

  ExchangeEndpoint(String endpointName) {
    this.endpointName = endpointName;
  }

  public static String getUrlBasingOnEndpoint(String sslUri, ExchangeEndpoint endpoint) {
    return sslUri + "/Private/" + endpoint.endpointName;
  }
}

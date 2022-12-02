package org.knowm.xchange.independentreserve.util;

import static org.knowm.xchange.independentreserve.IndependentReserveAuthenticated.GetDigitalCurrencyDepositAddress;
import static org.knowm.xchange.independentreserve.IndependentReserveAuthenticated.SynchDigitalCurrencyDepositAddressWithBlockchain;
import static org.knowm.xchange.independentreserve.IndependentReserveAuthenticated.WithdrawDigitalCurrency;

import org.knowm.xchange.independentreserve.IndependentReserveAuthenticated;

/** Author: Kamil Zbikowski Date: 4/13/15 */
public enum ExchangeEndpoint {
  CANCEL_ORDER("CancelOrder"),
  GET_ACCOUNTS("GetAccounts"),
  GET_BROKER_FEES(IndependentReserveAuthenticated.GetBrokerageFees),
  GET_DIGITAL_CURRENCY_DEPOSIT_ADDRESS(GetDigitalCurrencyDepositAddress),
  GET_OPEN_ORDERS("GetOpenOrders"),
  GET_ORDER_DETAILS("GetOrderDetails"),
  GET_TRADES("GetTrades"),
  GET_TRANSACTIONS("GetTransactions"),
  PLACE_LIMIT_ORDER("PlaceLimitOrder"),
  PLACE_MARKET_ORDER("PlaceMarketOrder"),
  SYNCH_DIGITAL_CURRENCY_DEPOSIT_ADDRESS_WITH_BLOCKCHAIN(
      SynchDigitalCurrencyDepositAddressWithBlockchain),
  WITHDRAW_DIGITAL_CURRENCY(WithdrawDigitalCurrency);

  private String endpointName;

  ExchangeEndpoint(String endpointName) {
    this.endpointName = endpointName;
  }

  public static String getUrlBasingOnEndpoint(String sslUri, ExchangeEndpoint endpoint) {
    return sslUri + "/Private/" + endpoint.endpointName;
  }
}

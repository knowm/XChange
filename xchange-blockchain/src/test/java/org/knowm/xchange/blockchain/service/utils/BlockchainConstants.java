package org.knowm.xchange.blockchain.service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlockchainConstants {
  public static final String URL_ACCOUNT = "/v3/exchange/accounts";
  public static final String URL_DEPOSITS = "/v3/exchange/deposits";
  public static final String URL_FEES = "/v3/exchange/fees";
  public static final String URL_SYMBOLS = "/v3/exchange/symbols";
  public static final String URL_DEPOSIT_BY_CURRENCY = "/v3/exchange/deposits/BTC";
  public static final String URL_WITHDRAWALS = "/v3/exchange/withdrawals";
  public static final String URL_ORDERS = "/v3/exchange/orders";
  public static final String URL_ORDERS_BY_ID_1 = "/v3/exchange/orders/11111111";
  public static final String URL_ORDERS_BY_ID_2 = "/v3/exchange/orders/22222222";
  public static final String URL_ORDERS_BY_ID = "/v3/exchange/orders/111111211";
  public static final String URL_TRADES = "/v3/exchange/trades";
  public static final String URL_ORDERBOOOK_L3 = "/v3/exchange/l3/BTC-USD";
  public static final String WITHDRAWAL_ID = "3QXYWgRGX2BPYBpUDBssGbeWEa5zq6snBZ";
  public static final String STATUS_CODE_401 = "Unauthorized (HTTP status code: 401)";
  public static final String STATUS_CODE_400 = "Bad Request (HTTP status code: 400)";
  public static final String STATUS_CODE_404 = " (HTTP status code: 404)";
  public static final String HTTP_CODE_400 = "HTTP status code was not OK: 400";
  public static final String STATUS_CODE_500 = " (HTTP status code: 500)";
  public static final String ADDRESS = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
  public static final String ADDRESS_DEPOSIT = "3CrbF4Z45fnJs62jFs1p3LkR8KiZSGKJFL";
  public static final String ACCOUNT_INFORMATION_JSON = "accountInformation.json";
  public static final String ORDERS_JSON = "orders.json";
  public static final String ORDERBOOK_JSON = "orderbook.json";
  public static final String ORDERBOOK_FAILURE_JSON = "orderbook_failure.json";
  public static final String NEW_ORDER_MARKET_JSON = "new_order_market.json";
  public static final String NEW_ORDER_LIMIT_JSON = "new_order_limit.json";
  public static final String NEW_ORDER_STOP_JSON = "new_order_stop.json";
  public static final String ORDER_NOT_FOUND_JSON = "order_not_found.json";
  public static final String DEPOSIT_SUCCESS_JSON = "deposit-success.json";
  public static final String WITHDRAWAL_SUCCESS_JSON = "withdraw-success.json";
  public static final String WITHDRAWAL_FAILURE_JSON = "withdraw-failure.json";
  public static final String DEPOSIT_HISTORY_SUCCESS_JSON = "depositHistory-success.json";
  public static final String WITHDRAWAL_HISTORY_SUCCESS_JSON = "withdrawHistory-success.json";
  public static final String FEES_JSON = "fees.json";
  public static final String SYMBOL_JSON = "symbols.json";
  public static final String DEPOSIT_FAILURE_JSON = "deposit-failure.json";
  public static final String NOT_IMPLEMENTED_YET = "Not implemented yet";
  public static final String BENEFICIARY = "ea1f34b3-e77a-4646-9cfa-5d6d3518c6d3";
  public static final String ORDER_ID = "11111111";
  public static final String MARKET_ORDER_ID = "22222222";
  public static final String STOP_ORDER_ID = "33333333";
  public static final Long END_TIME = 12 * 30 * 24 * 60 * 60 * 1000L;
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String APPLICATION = "application/json";
}

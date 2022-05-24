package org.knowm.xchange.blockchain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlockchainConstants {

    public static final String ENDPOINT_RATE_LIMIT = "endpointLimit";
    public static final String GET_TICKER = "getTicker";
    public static final String GET_TICKERS = "getTickers";
    public static final String GET_ORDER_BOOK_L2 = "getOrderBookL2";
    public static final String GET_ORDER_BOOK_L3 = "getOrderBookL3";
    public static final String GET_EXCHANGE_TRADES = "getExchangeTrades";
    public static final String GET_DEPOSIT_ADDRESS = "getDepositAddress";
    public static final String GET_ORDERS = "getOrders";
    public static final String GET_TRADES = "getTrades";
    public static final String POST_ORDER = "postOrder";

    public static final String CURRENCY_PAIR_SYMBOL_FORMAT = "%s-%s";

    public static final String SELL = "sell";
    public static final String BUY = "buy";
    public static final String CURRENCY = "currency";

    public static final String AUTHORIZATION = "Authorization";
    public static final String X_API_TOKEN = "X-API-Token";
    public static final String BEARER_TOKEN_FORMAT = "Bearer %s";

    public static final String MARKET = "MARKET";
    public static final String LIMIT = "LIMIT";
    public static final String STOP = "STOP";
}

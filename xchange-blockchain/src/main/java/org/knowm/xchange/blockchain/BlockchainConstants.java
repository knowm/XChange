package org.knowm.xchange.blockchain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlockchainConstants {
    public static final String ENDPOINT_RATE_LIMIT = "endpointLimit";
    public static final String GET_DEPOSIT_ADDRESS = "getDepositAddress";
    public static final String GET_ACCOUNT_INFORMATION = "getAccountInformation";
    public static final String GET_WITHDRAWAL = "postWithdrawFunds";
    public static final String GET_FEES = "getFees";
    public static final String GET_DEPOSIT_HISTORY = "depositHistory";
    public static final String GET_WITHDRAWAL_HISTORY = "withdrawHistory";
    public static final String GET_ORDERS = "getOrders";
    public static final String GET_ORDER = "getOrder";
    public static final String POST_ORDER = "postOrder";
    public static final String CANCEL_ORDER = "cancelOrder";
    public static final String CANCEL_ALL_ORDERS = "cancelAllOrders";
    public static final String GET_SYMBOLS = "getSymbols";
    public static final String GET_TRADES = "getTrades";
    public static final String CURRENCY_PAIR_SYMBOL_FORMAT = "%s-%s";
    public static final String X_API_TOKEN = "X-API-Token";
    public static final String WITHDRAWAL_EXCEPTION = "Invalid WithdrawFundsParams parameter. Only DefaultWithdrawFundsParams is supported.";
    public static final String EXCEPTION_MESSAGE = "Operation failed without any error message";
    public static final String FUNDING_RECORD_TYPE_UNSUPPORTED =  "Invalid FundingRecord parameter. Only DefaultWithdrawFundsParams is supported.";
    public static final String CURRENCY_PAIR_EXCEPTION = "Invalid TradeHistoryParams type, it should be an instance of BlockchainTradeHistoryParams";
    public static final String OPEN = "OPEN";
    public static final String REJECTED = "REJECTED";
    public static final String REFUNDING = "REFUNDING";
    public static final String PENDING = "PENDING";
    public static final String FAILED = "FAILED";
    public static final String COMPLETED = "COMPLETED";
    public static final String UNCONFIRMED = "UNCONFIRMED";
    public static final String CANCELED = "CANCELED";
    public static final String FILLED = "FILLED";
    public static final String PART_FILLED = "PART_FILLED";
    public static final String EXPIRED = "EXPIRED";
    public static final String STATUS_INVALID = "Unknown withdraw status: ";
    public static final String NOT_IMPLEMENTED_YET = "Not implemented yet";
    public static final String MARKET = "MARKET";
    public static final String LIMIT = "LIMIT";
    public static final String STOP = "STOP";
    public static final String BUY = "buy";
    public static final String SELL = "sell";
}

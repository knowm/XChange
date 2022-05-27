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
    public static final String GET_SYMBOLS = "getSymbols";
    public static final String CURRENCY_PAIR_SYMBOL_FORMAT = "%s-%s";
    public static final String X_API_TOKEN = "X-API-Token";
    public static final String WITHDRAWAL_EXCEPTION = "Don't know how to withdraw of BlockchainWithdrawal: ";
    public static final String EXCEPTION_MESSAGE = "Operation failed without any error message";
    public static final String FUNDING_RECORD_TYPE_UNSUPPORTED =  "Unsupported FundingRecord.Type of HistoryParamsFundingType: ";
    public static final String REJECTED = "REJECTED";
    public static final String REFUNDING = "REFUNDING";
    public static final String PENDING = "PENDING";
    public static final String FAILED = "FAILED";
    public static final String COMPLETED = "COMPLETED";
    public static final String UNCONFIRMED = "UNCONFIRMED";
    public static final String STATUS_INVALID = "Unknown withdraw status: ";
}

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
    public static final String WITHDRAWAL_ID = "3QXYWgRGX2BPYBpUDBssGbeWEa5zq6snBZ";
    public static final String STATUS_CODE_401 = "Unauthorized (HTTP status code: 401)";
    public static final String STATUS_CODE_400 = "Bad Request (HTTP status code: 400)";
    public static final String ADDRESS = "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa";
    public static final String ADDRESS_DEPOSIT = "3CrbF4Z45fnJs62jFs1p3LkR8KiZSGKJFL";
    public static final String ACCOUNT_INFORMATION_JSON = "accountInformation.json";
    public static final String DEPOSIT_SUCCESS_JSON = "deposit-success.json";
    public static final String WITHDRAWAL_SUCCESS_JSON = "withdraw-success.json";
    public static final String WITHDRAWAL_FAILURE_JSON = "withdraw-failure.json";
    public static final String DEPOSIT_HISTORY_SUCCESS_JSON = "depositHistory-success.json";
    public static final String WITHDRAWAL_HISTORY_SUCCESS_JSON = "withdrawHistory-success.json";
    public static final String FEES_JSON = "fees.json";
    public static final String SYMBOL_JSON = "symbols.json";
    public static final String DEPOSIT_FAILURE_JSON = "deposit-failure.json";
}

package com.xeiam.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;
import com.xeiam.xchange.gatecoin.dto.trade.GatecoinTradeHistory;

/**
 *
 * @author sumedha
 */
public class GatecoinTradeHistoryResult {
    private final GatecoinTradeHistory[] transactions;
     private final Response responseStatus;

    public GatecoinTradeHistoryResult(@JsonProperty("transactions") GatecoinTradeHistory[] transactions,@JsonProperty("responseStatus")  Response responseStatus) {
        this.transactions = transactions;
        this.responseStatus = responseStatus;
    }

    public GatecoinTradeHistory[] getTransactions() {
        return transactions;
    }

    public Response getResponseStatus() {
        return responseStatus;
    }
}

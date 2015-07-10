
package com.xeiam.xchange.gatecoin.dto.marketdata.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinTransaction;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumdeha
 */
public class GatecoinTransactionResult {
    
     private final GatecoinTransaction[] transactions;
      private final Response responseStatus;

      @JsonCreator
    public GatecoinTransactionResult(@JsonProperty("transactions") GatecoinTransaction[] transactions, @JsonProperty("responseStatus")  Response responseStatus) {
        this.transactions = transactions;
        this.responseStatus = responseStatus;
    }

    public GatecoinTransaction[] getTransactions() {
        return transactions;
    }

    public Response getResponseStatus() {
        return responseStatus;
    }
}

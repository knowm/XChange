
package com.xeiam.xchange.gatecoin.dto.account.Results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.account.GatecoinBalance;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumedha
 */
public class GatecoinBalanceResult {
    private final GatecoinBalance[] balances;
    private final Response responseStatus;

    public GatecoinBalanceResult(@JsonProperty("balances") GatecoinBalance[] balances,
            @JsonProperty("responseStatus") Response responseStatus) {
        this.balances = balances;
        this.responseStatus = responseStatus;
    }

    public GatecoinBalance[] getBalances() {
        return balances;
    }

    public Response getResponseStatus() {
        return responseStatus;
    }
}

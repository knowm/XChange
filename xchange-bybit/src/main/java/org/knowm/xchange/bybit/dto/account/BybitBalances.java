package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BybitBalances {

    @JsonProperty("balances")
    private List<BybitBalance> balances;

    public List<BybitBalance> getBalances() {
        return balances;
    }

    @Override
    public String toString() {
        return "BybitBalances{" +
                "balances=" + balances +
                '}';
    }
}

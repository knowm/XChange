package org.knowm.xchange.poloniex.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PoloniexDepositsWithdrawalsResponse {

    private final List<PoloniexDeposit> deposits;
    private final List<PoloniexWithdrawal> withdrawals;

    @JsonCreator
    public PoloniexDepositsWithdrawalsResponse(@JsonProperty("deposits") List<PoloniexDeposit> deposits,
            @JsonProperty("withdrawals") List<PoloniexWithdrawal> withdrawals) {
        this.deposits = deposits;
        this.withdrawals = withdrawals;
    }

    public List<PoloniexDeposit> getDeposits() { return deposits; }
    public List<PoloniexWithdrawal> getWithdrawals() { return withdrawals; }

    @Override
    public String toString() {
        return "DepositsWithdrawalsResponse [deposits=" + deposits + ", withdrawals=" + withdrawals + "]";
    }
}

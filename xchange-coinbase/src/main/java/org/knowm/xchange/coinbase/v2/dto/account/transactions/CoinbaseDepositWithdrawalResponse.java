package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class CoinbaseDepositWithdrawalResponse {
    private final List<CoinbaseBuySell> data;

    public CoinbaseDepositWithdrawalResponse(@JsonProperty("data") List<CoinbaseBuySell> data) {
        this.data = data;
    }
}

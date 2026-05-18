package org.knowm.xchange.coinbase.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbasePagination;
import org.knowm.xchange.coinbase.v2.dto.account.transactions.CoinbaseShowTransactionV2;

import java.util.List;

@Getter
public class CoinbaseExpandTransactionsResponse {
    private final List<CoinbaseShowTransactionV2> data;
    private final CoinbasePagination pagination;

    public CoinbaseExpandTransactionsResponse(@JsonProperty("pagination") CoinbasePagination pagination,
                                              @JsonProperty("data") List<CoinbaseShowTransactionV2> data) {
        this.data = data;
        this.pagination = pagination;
    }
}

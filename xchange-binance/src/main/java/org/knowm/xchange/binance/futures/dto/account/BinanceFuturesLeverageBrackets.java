package org.knowm.xchange.binance.futures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BinanceFuturesLeverageBrackets {
    public final String symbol;
    public final List<BinanceFuturesLeverageBracket> brackets;

    public BinanceFuturesLeverageBrackets(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("brackets") List<BinanceFuturesLeverageBracket> brackets) {
        this.symbol = symbol;
        this.brackets = brackets;
    }
}

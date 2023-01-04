package org.knowm.xchange.binance.dto.account.futures;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
public class BinanceFutureAccountInformation {

    private final BigDecimal totalWalletBalance;

    private final List<BinancePosition> positions;

    public BinanceFutureAccountInformation(
            @JsonProperty("totalWalletBalance") BigDecimal totalWalletBalance,
            @JsonProperty("positions") List<BinancePosition> positions) {
        this.totalWalletBalance = totalWalletBalance;
        this.positions = positions;
    }
}

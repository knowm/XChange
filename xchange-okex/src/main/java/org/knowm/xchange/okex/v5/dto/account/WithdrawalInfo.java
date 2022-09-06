package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithdrawalInfo {

    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("chain")
    private String chain;

    @JsonProperty("amt")
    private String withdrawalAmount;

    @JsonProperty("wdId")
    private String withdrawalId;

    @JsonProperty("clientId")
    private String clientId;
}

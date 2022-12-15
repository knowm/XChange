package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** https://www.okx.com/docs-v5/en/#rest-api-funding-withdrawal */
@Getter
@NoArgsConstructor
@ToString
public class OkexWithdrawalResponse {
    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("amt")
    private String amount;

    @JsonProperty("chain")
    private String chain;

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("wdId")
    private String withdrawalId;
}

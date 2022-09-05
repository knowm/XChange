package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class OkexWithdrawRequest {

    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("amt")
    private String withdrawalAmount;

    @JsonProperty("dest")
    private String destination;

    @JsonProperty("toAddr")
    private String toAddress;

    @JsonProperty("fee")
    private String transactionFee;

    @JsonProperty("chain")
    private String chain;

    @JsonProperty("clientId")
    private String clientId;
}

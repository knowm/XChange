package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/** Author: Marc Deveaux (MDVX) Created: 10-24-2022 */
/** https://www.okx.com/docs-v5/en/#rest-api-funding-funds-transfer * */
@Builder
public class OkexAssetTransferRequest {
    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("amt")
    private String amount;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("subAcct")
    private String subAccount;

    @JsonProperty("type")
    private String type;

    @JsonProperty("loanTrans")
    private Boolean loanTrans;

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("omitPosRisk")
    private String omitPosRisk;
}

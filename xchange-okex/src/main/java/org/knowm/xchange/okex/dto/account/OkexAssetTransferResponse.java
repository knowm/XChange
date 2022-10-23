package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/** Author: Marc Deveaux (MDVX) Created: 10-24-2022 */
/** https://www.okx.com/docs-v5/en/#rest-api-funding-funds-transfer * */
@Getter
public class OkexAssetTransferResponse {
    @JsonProperty("transId")
    private String transId;

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("from")
    private String from;

    @JsonProperty("amt")
    private String amount;

    @JsonProperty("to")
    private String to;
}

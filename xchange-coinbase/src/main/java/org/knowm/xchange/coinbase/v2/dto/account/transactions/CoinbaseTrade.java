package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseTrade {

    private final String id;
    private final String paymentMethodName;
    private final String resource;
    private final String resourcePath;

    public CoinbaseTrade(
            @JsonProperty("id") String id,
            @JsonProperty("payment_method_name") String paymentMethodName,
            @JsonProperty("resource") String resource,
            @JsonProperty("resource_path") String resourcePath) {
        this.id = id;
        this.paymentMethodName = paymentMethodName;
        this.resource = resource;
        this.resourcePath = resourcePath;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":"
                + '\"'
                + id
                + '\"'
                + ",\"paymentMethodName\":"
                + '\"'
                + paymentMethodName
                + '\"'
                + ",\"resource\":"
                + '\"'
                + resource
                + '\"'
                + ",\"resourcePath\":"
                + '\"'
                + resourcePath
                + '\"'
                + '}';
    }
}

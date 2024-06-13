package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseTransactionDetails {
    private final String title;
    private final String subtitle;
    private final String paymentMethodName;

    public CoinbaseTransactionDetails(
            @JsonProperty("title") String title,
            @JsonProperty("subtitle") String subtitle,
            @JsonProperty("payment_method_name") String paymentMethodName) {
        this.title = title;
        this.subtitle = subtitle;
        this.paymentMethodName = paymentMethodName;
    }

    @Override
    public String toString() {
        return "{"
                + "\"title\":"
                + '\"'
                + title
                + '\"'
                + ",\"subtitle\":"
                + '\"'
                + subtitle
                + '\"'
                + ",\"paymentMethodName\":"
                + '\"'
                + paymentMethodName
                + '\"'
                + "}";
    }
}
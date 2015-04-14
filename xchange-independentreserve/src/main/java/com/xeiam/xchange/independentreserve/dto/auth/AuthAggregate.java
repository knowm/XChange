package com.xeiam.xchange.independentreserve.dto.auth;

/**
 * Author: Kamil Zbikowski
 * Date: 4/13/15
 */
public class AuthAggregate {
    private final String apiKey;
    private String signature;
    private final Long nonce;

    public AuthAggregate(String apiKey, Long nonce) {
        this.apiKey = apiKey;
        this.nonce = nonce;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Long getNonce() {
        return nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

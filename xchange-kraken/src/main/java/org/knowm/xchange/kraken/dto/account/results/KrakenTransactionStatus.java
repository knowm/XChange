package org.knowm.xchange.kraken.dto.account.results;

public enum KrakenTransactionStatus {
    INITIAL("Initial"),
    PENDING("Pending"),
    SETTLED("Settled"),
    SUCCESS("Success"),
    FAILURE("Failure");
    private final String code;

    KrakenTransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

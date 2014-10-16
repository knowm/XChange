package com.xeiam.xchange.cryptonit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitCoin {
/*
    "id": {
        "id": "id",
        "label": "XYZcoin" // label associate with this account
        "type": "litecoin", // some type (bitcoin, litecoin,etc.)
        "current": "4.87696051", // your current balance
        "unconfirmed": "0.00000000" // your unconfirmed balance
    }
*/

    private String id;
    private String label;
    private String type;
    private BigDecimal current;
    private BigDecimal unconfirmed;

    public CryptonitCoin(@JsonProperty("id") String id, @JsonProperty("label") String label, @JsonProperty("type") String type, @JsonProperty("current") BigDecimal current, @JsonProperty("unconfirmed") BigDecimal unconfirmed) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.current = current;
        this.unconfirmed = unconfirmed;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public BigDecimal getUnconfirmed() {
        return unconfirmed;
    }

    @Override
    public String toString() {
        return "CryptonitCoin{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", current=" + current +
                ", unconfirmed=" + unconfirmed +
                '}';
    }
}

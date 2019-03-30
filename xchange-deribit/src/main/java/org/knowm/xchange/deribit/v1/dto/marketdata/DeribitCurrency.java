package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitCurrency {

    @JsonProperty("txFee")
    private Float txFee;

    @JsonProperty("minConfirmation")
    private Integer minConfirmation;

    @JsonProperty("isActive")
    private Boolean isActive;

    @JsonProperty("currencyLong")
    private String currencyLong;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("coinType")
    private String coinType;

    @JsonProperty("baseaddress")
    private Object baseAddress;


    public Float getTxFee() {
        return txFee;
    }

    public Integer getMinConfirmation() {
        return minConfirmation;
    }

    public Boolean getActive() {
        return isActive;
    }

    public String getCurrencyLong() {
        return currencyLong;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCoinType() {
        return coinType;
    }

    public Object getBaseAddress() {
        return baseAddress;
    }
}

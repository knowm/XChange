
package org.knowm.xchange.bitcoinde.trade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * @author kaiserfr
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "min_trust_level",
    "only_kyc_full",
    "seat_of_bank",
    "payment_option"
})
public class BitcoindeOrderRequirements {

    @JsonProperty("min_trust_level")
    private String minTrustLevel;
    @JsonProperty("only_kyc_full")
    private Boolean onlyKycFull;
    @JsonProperty("seat_of_bank")
    private List<String> seatOfBank = null;
    @JsonProperty("payment_option")
    private Integer paymentOption;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public BitcoindeOrderRequirements() {
    }

    /**
     * 
     * @param onlyKycFull
     * @param seatOfBank
     * @param minTrustLevel
     * @param paymentOption
     */
    public BitcoindeOrderRequirements(String minTrustLevel, Boolean onlyKycFull, List<String> seatOfBank, Integer paymentOption) {
        super();
        this.minTrustLevel = minTrustLevel;
        this.onlyKycFull = onlyKycFull;
        this.seatOfBank = seatOfBank;
        this.paymentOption = paymentOption;
    }

    @JsonProperty("min_trust_level")
    public String getMinTrustLevel() {
        return minTrustLevel;
    }

    @JsonProperty("min_trust_level")
    public void setMinTrustLevel(String minTrustLevel) {
        this.minTrustLevel = minTrustLevel;
    }

    @JsonProperty("only_kyc_full")
    public Boolean getOnlyKycFull() {
        return onlyKycFull;
    }

    @JsonProperty("only_kyc_full")
    public void setOnlyKycFull(Boolean onlyKycFull) {
        this.onlyKycFull = onlyKycFull;
    }

    @JsonProperty("seat_of_bank")
    public List<String> getSeatOfBank() {
        return seatOfBank;
    }

    @JsonProperty("seat_of_bank")
    public void setSeatOfBank(List<String> seatOfBank) {
        this.seatOfBank = seatOfBank;
    }

    @JsonProperty("payment_option")
    public Integer getPaymentOption() {
        return paymentOption;
    }

    @JsonProperty("payment_option")
    public void setPaymentOption(Integer paymentOption) {
        this.paymentOption = paymentOption;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

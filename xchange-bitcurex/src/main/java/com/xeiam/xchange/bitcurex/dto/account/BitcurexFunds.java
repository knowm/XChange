package com.xeiam.xchange.bitcurex.dto.account;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class BitcurexFunds {

    @JsonProperty("usd")
    private BigDecimal usd;
    @JsonProperty("pln")
    private BigDecimal pln;
    @JsonProperty("eur")
    private BigDecimal eur;
    @JsonProperty("btc")
    private BigDecimal btc;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The usd
     */
    @JsonProperty("usd")
    public BigDecimal getUsd() {
        return usd;
    }

    /**
     *
     * @param usd The usd
     */
    @JsonProperty("usd")
    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    /**
     *
     * @return The pln
     */
    @JsonProperty("pln")
    public BigDecimal getPln() {
        return pln;
    }

    /**
     *
     * @param pln The pln
     */
    @JsonProperty("pln")
    public void setPln(BigDecimal pln) {
        this.pln = pln;
    }

    /**
     *
     * @return The eur
     */
    @JsonProperty("eur")
    public BigDecimal getEur() {
        return eur;
    }

    /**
     *
     * @param eur The eur
     */
    @JsonProperty("eur")
    public void setEur(BigDecimal eur) {
        this.eur = eur;
    }

    /**
     *
     * @return The btc
     */
    @JsonProperty("btc")
    public BigDecimal getBtc() {
        return btc;
    }

    /**
     *
     * @param btc The btc
     */
    @JsonProperty("btc")
    public void setBtc(BigDecimal btc) {
        this.btc = btc;
    }

    /**
     *
     * @return The Error
     */
    @JsonProperty("status")
    public String getStatus() {
        return status
;    }

    /**
     *
     * @param status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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

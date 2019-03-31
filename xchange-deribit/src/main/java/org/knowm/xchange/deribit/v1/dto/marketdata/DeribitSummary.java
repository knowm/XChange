package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitSummary {

    @JsonProperty("currentFunding") public BigDecimal currentFunding;
    @JsonProperty("funding8h") public BigDecimal funding8h;
    @JsonProperty("instrumentName") public String instrumentName;
    @JsonProperty("openInterest") public BigDecimal openInterest;
    @JsonProperty("openInterestAmount") public BigDecimal openInterestAmount;
    @JsonProperty("high") public BigDecimal high;
    @JsonProperty("low") public BigDecimal low;
    @JsonProperty("volume") public BigDecimal volume;
    @JsonProperty("volumeUsd") public BigDecimal volumeUsd;
    @JsonProperty("volumeBtc") public BigDecimal volumeBtc;
    @JsonProperty("last") public BigDecimal last;
    @JsonProperty("bidPrice") public BigDecimal bidPrice;
    @JsonProperty("askPrice") public BigDecimal askPrice;
    @JsonProperty("midPrice") public BigDecimal midPrice;
    @JsonProperty("markPrice") public BigDecimal markPrice;
    @JsonProperty("uPx") public BigDecimal uPx;
    @JsonProperty("uIx") public String uIx;
    @JsonProperty("iR") public BigDecimal iR;
    @JsonProperty("created") public String created;
    @JsonProperty("estDelPrice") public BigDecimal estDelPrice;

    public BigDecimal getCurrentFunding() {
        return currentFunding;
    }

    public BigDecimal getFunding8h() {
        return funding8h;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public BigDecimal getOpenInterest() {
        return openInterest;
    }

    public BigDecimal getOpenInterestAmount() {
        return openInterestAmount;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public BigDecimal getVolumeUsd() {
        return volumeUsd;
    }

    public BigDecimal getVolumeBtc() {
        return volumeBtc;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    public BigDecimal getMidPrice() {
        return midPrice;
    }

    public BigDecimal getMarkPrice() {
        return markPrice;
    }

    public BigDecimal getuPx() {
        return uPx;
    }

    public String getuIx() {
        return uIx;
    }

    public BigDecimal getiR() {
        return iR;
    }

    public String getCreated() {
        return created;
    }

    public BigDecimal getEstDelPrice() {
        return estDelPrice;
    }
}

package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitOrderbook {

    @JsonProperty("instrument") public String instrument;
    @JsonProperty("bids") public List<DeribitOrder> bids = null;
    @JsonProperty("asks") public List<DeribitOrder> asks = null;
    @JsonProperty("tstamp") public long tstamp;
    @JsonProperty("last") public BigDecimal last;
    @JsonProperty("low") public BigDecimal low;
    @JsonProperty("high") public BigDecimal high;
    @JsonProperty("mark") public BigDecimal mark;
    @JsonProperty("state") public String state;
    @JsonProperty("settlementPrice") public BigDecimal settlementPrice;
    @JsonProperty("deliveryPrice") public BigDecimal deliveryPrice;
    @JsonProperty("uPx") public BigDecimal uPx;
    @JsonProperty("uIx") public String uIx;
    @JsonProperty("iR") public BigDecimal iR;
    @JsonProperty("markIv") public BigDecimal markIv;
    @JsonProperty("askIv") public BigDecimal askIv;
    @JsonProperty("bidIv") public BigDecimal bidIv;
    @JsonProperty("delta") public BigDecimal delta;
    @JsonProperty("gamma") public BigDecimal gamma;
    @JsonProperty("vega") public BigDecimal vega;
    @JsonProperty("theta") public BigDecimal theta;
    @JsonProperty("max") public BigDecimal max;
    @JsonProperty("min") public BigDecimal min;
    @JsonProperty("contractMultiplier") public int contractMultiplier;


    public String getInstrument() {
        return instrument;
    }

    public List<DeribitOrder> getBids() {
        return bids;
    }

    public List<DeribitOrder> getAsks() {
        return asks;
    }

    public long getTstamp() {
        return tstamp;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
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

    public BigDecimal getMarkIv() {
        return markIv;
    }

    public BigDecimal getAskIv() {
        return askIv;
    }

    public BigDecimal getBidIv() {
        return bidIv;
    }

    public BigDecimal getDelta() {
        return delta;
    }

    public BigDecimal getGamma() {
        return gamma;
    }

    public BigDecimal getVega() {
        return vega;
    }

    public BigDecimal getTheta() {
        return theta;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public int getContractMultiplier() {
        return contractMultiplier;
    }
}
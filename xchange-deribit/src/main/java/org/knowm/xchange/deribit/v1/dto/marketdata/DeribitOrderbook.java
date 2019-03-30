package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitOrderbook {

    @JsonProperty("instrument") public String instrument;
    @JsonProperty("bids") public List<DeribitOrder> bids = null;
    @JsonProperty("asks") public List<DeribitOrder> asks = null;
    @JsonProperty("tstamp") public long tstamp;
    @JsonProperty("last") public float last;
    @JsonProperty("low") public float low;
    @JsonProperty("high") public float high;
    @JsonProperty("mark") public float mark;
    @JsonProperty("state") public String state;
    @JsonProperty("settlementPrice") public float settlementPrice;
    @JsonProperty("deliveryPrice") public float deliveryPrice;
    @JsonProperty("uPx") public float uPx;
    @JsonProperty("uIx") public String uIx;
    @JsonProperty("iR") public float iR;
    @JsonProperty("markIv") public float markIv;
    @JsonProperty("askIv") public float askIv;
    @JsonProperty("bidIv") public float bidIv;
    @JsonProperty("delta") public float delta;
    @JsonProperty("gamma") public float gamma;
    @JsonProperty("vega") public float vega;
    @JsonProperty("theta") public float theta;
    @JsonProperty("max") public float max;
    @JsonProperty("min") public float min;
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

    public float getLast() {
        return last;
    }

    public float getLow() {
        return low;
    }

    public float getHigh() {
        return high;
    }

    public float getMark() {
        return mark;
    }

    public String getState() {
        return state;
    }

    public float getSettlementPrice() {
        return settlementPrice;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }

    public float getuPx() {
        return uPx;
    }

    public String getuIx() {
        return uIx;
    }

    public float getiR() {
        return iR;
    }

    public float getMarkIv() {
        return markIv;
    }

    public float getAskIv() {
        return askIv;
    }

    public float getBidIv() {
        return bidIv;
    }

    public float getDelta() {
        return delta;
    }

    public float getGamma() {
        return gamma;
    }

    public float getVega() {
        return vega;
    }

    public float getTheta() {
        return theta;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public int getContractMultiplier() {
        return contractMultiplier;
    }
}
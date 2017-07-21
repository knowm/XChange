package org.knowm.xchange.luno.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoFeeInfo {

    public final BigDecimal makerFee;
    public final BigDecimal takerFee;
    public final BigDecimal thirtyDayVolume;
    
    public LunoFeeInfo(@JsonProperty(value="maker_fee", required=true) BigDecimal makerFee
            , @JsonProperty(value="taker_fee", required=true) BigDecimal takerFee
            , @JsonProperty(value="thirty_day_volume", required=true) BigDecimal thirtyDayVolume) {
        this.makerFee = makerFee;
        this.takerFee = takerFee;
        this.thirtyDayVolume = thirtyDayVolume;
    }

    @Override
    public String toString() {
        return "LunoFeeInfo [makerFee=" + makerFee + ", takerFee=" + takerFee + ", thirtyDayVolume=" + thirtyDayVolume + "]";
    }
}

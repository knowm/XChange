package org.knowm.xchange.bl3p.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Bl3pAmountObj {

    @JsonProperty("value_int")
    public long valueInt;

    @JsonProperty("display_short")
    public String displayShort;

    @JsonProperty("display")
    public String display;

    @JsonProperty("currency")
    public String currency;

    @JsonProperty("value")
    public BigDecimal value;

}

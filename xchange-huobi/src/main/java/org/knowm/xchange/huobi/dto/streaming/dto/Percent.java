package org.knowm.xchange.huobi.dto.streaming.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Market data depth percents.
 */
public enum Percent {

  @SerializedName("10") PERCENT10,

  @SerializedName("20") PERCENT20,

  @SerializedName("50") PERCENT50,

  @SerializedName("80") PERCENT80,

  @SerializedName("100") PERCENT100,

  @SerializedName("top") PERCENT_TOP;

}

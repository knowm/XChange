package org.knowm.xchange.huobi.dto.streaming.dto;

import com.google.gson.annotations.SerializedName;

/**
 * K Line periods.
 */
public enum Period {

  @SerializedName("1min") KLINE_1MIN,

  @SerializedName("5min") KLINE_5MIN,

  @SerializedName("15min") KLINE_15MIN,

  @SerializedName("30min") KLINE_30MIN,

  @SerializedName("60min") KLINE_60MIN,

  @SerializedName("1day") KLINE_1DAY,

  @SerializedName("1week") KLINE_1WEEK,

  @SerializedName("1mon") KLINE_1MON,

  @SerializedName("1year") KLINE_1YEAR,

  @SerializedName("tl") KLINE_TIMELINE;

}

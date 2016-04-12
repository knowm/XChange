package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

import com.google.gson.annotations.SerializedName;

/**
 * Push type.
 */
public enum PushType {

  @SerializedName("pushLong") PUSH_LONG,

  @SerializedName("pushShort") PUSH_SHORT;

}

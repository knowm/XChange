package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.utils.jackson.UnixTimestampNanoSecondsDeserializer;

@Builder
@Jacksonized
@Value
public class BybitResult<V> {

  @JsonProperty("ret_code")
  int retCode;

  @JsonProperty("ret_msg")
  String retMsg;

  @JsonProperty("ext_code")
  String extCode;

  @JsonProperty("ext_info")
  String extInfo;

  @JsonProperty("result")
  V result;

  @JsonProperty("time_now")
  @JsonDeserialize(using = UnixTimestampNanoSecondsDeserializer.class)
  Date timeNow;

  public boolean isSuccess() {
    return retCode == 0;
  }
}

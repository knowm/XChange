package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitBalance {

  @JsonProperty("coin")
  String coin;

  @JsonProperty("coinId")
  String coinId;

  @JsonProperty("coinName")
  String coinName;

  @JsonProperty("total")
  String total;

  @JsonProperty("free")
  String free;

  @JsonProperty("locked")
  String locked;
}

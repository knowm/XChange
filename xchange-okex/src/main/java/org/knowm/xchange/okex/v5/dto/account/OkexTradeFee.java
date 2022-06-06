package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OkexTradeFee {
  @JsonProperty("category")
  String category;

  @JsonProperty("delivery")
  String delivery;

  @JsonProperty("exercise")
  String exercise;

  @JsonProperty("instType")
  String instType;

  @JsonProperty("level")
  String level;

  @JsonProperty("maker")
  String maker;

  @JsonProperty("taker")
  String taker;

  @JsonProperty("timestamp")
  String timestamp;
}

package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knowm.xchange.vertex.NanoSecondsDeserializer;
import java.time.Instant;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RewardsList {

  private Rewards[] rewards;

  @JsonDeserialize(using = NanoSecondsDeserializer.class)
  private Instant update_time;
}

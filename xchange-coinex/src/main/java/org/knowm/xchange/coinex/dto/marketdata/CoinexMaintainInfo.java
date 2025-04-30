package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CoinexMaintainInfo {

  @JsonProperty("started_at")
  private Instant startTime;

  @JsonProperty("ended_at")
  private Instant endTime;

  @JsonProperty("scope")
  private List<String> scope;

  @JsonProperty("announce_enabled")
  private Boolean announceEnabled;

  @JsonProperty("announce_url")
  private URI announceUrl;

  @JsonProperty("protect_duration_start")
  private Instant protectStart;

  @JsonProperty("protect_duration_end")
  private Instant protectEnd;
}

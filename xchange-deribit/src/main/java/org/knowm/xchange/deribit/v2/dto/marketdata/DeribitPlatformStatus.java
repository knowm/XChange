package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class DeribitPlatformStatus {

  @JsonProperty("locked")
  private Boolean locked;

  @JsonProperty("locked_indices")
  private List<String> lockedCurrencies;

}

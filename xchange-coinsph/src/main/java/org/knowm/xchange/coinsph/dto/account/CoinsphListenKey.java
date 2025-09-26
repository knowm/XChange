package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.coinsph.dto.CoinsphResponse;

@Data
@NoArgsConstructor
public class CoinsphListenKey extends CoinsphResponse {

  @JsonProperty("listenKey")
  private String listenKey;
}

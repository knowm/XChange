package org.knowm.xchange.livecoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.livecoin.dto.LivecoinBaseResponse;

/** @author walec51 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivecoinCancelResponse extends LivecoinBaseResponse {

  private final boolean cancelled;

  public LivecoinCancelResponse(
      @JsonProperty("success") Boolean success, @JsonProperty("cancelled") Boolean cancelled) {
    super(success);
    this.cancelled = cancelled != null ? cancelled : false;
  }

  public boolean isCancelled() {
    return cancelled;
  }
}

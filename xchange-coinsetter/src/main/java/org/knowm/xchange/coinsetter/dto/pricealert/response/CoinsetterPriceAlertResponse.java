package org.knowm.xchange.coinsetter.dto.pricealert.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinsetter.dto.CoinsetterResponse;

/**
 * Response of adding price alert.
 */
public class CoinsetterPriceAlertResponse extends CoinsetterResponse {

  private final UUID uuid;

  /**
   * @param message Response message
   * @param requestStatus Status of the add request (ex: "SUCCESS")
   * @param uuid Unique identifier of newly created price alert
   */
  public CoinsetterPriceAlertResponse(@JsonProperty("message") String message, @JsonProperty("requestStatus") String requestStatus,
      @JsonProperty("uuid") UUID uuid) {

    super(message, requestStatus);
    this.uuid = uuid;
  }

  public UUID getUuid() {

    return uuid;
  }

  @Override
  public String toString() {

    return "CoinsetterPriceAlertResponse [uuid=" + uuid + "]";
  }

}

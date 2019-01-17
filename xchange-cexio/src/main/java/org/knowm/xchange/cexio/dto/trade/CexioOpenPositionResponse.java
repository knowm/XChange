package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object is returned when a position is opened
 *
 * @author Andrea Fossi.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CexioOpenPositionResponse {
  @JsonProperty("e")
  private String eventName;

  @JsonProperty("error")
  private String errorMessage;

  @JsonProperty("ok")
  private String status;

  @JsonProperty("data")
  private CexioOpenPosition position;

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public CexioOpenPosition getPosition() {
    return position;
  }

  public void setPosition(CexioOpenPosition position) {
    this.position = position;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}

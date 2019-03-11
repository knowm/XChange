package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Andrea Fossi. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CexioClosePositionResponse {
  @JsonProperty("e")
  private String eventName;

  @JsonProperty("error")
  private String errorMessage;

  @JsonProperty("ok")
  private String status;

  @JsonProperty("data")
  private CexioClosePosition position;

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

  public CexioClosePosition getPosition() {
    return position;
  }

  public void setPosition(CexioClosePosition position) {
    this.position = position;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}

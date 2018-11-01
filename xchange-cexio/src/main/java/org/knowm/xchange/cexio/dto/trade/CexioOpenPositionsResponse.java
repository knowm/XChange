package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Object is returned when open positions are requested
 *
 * @author Andrea Fossi.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CexioOpenPositionsResponse {
  @JsonProperty("e")
  private String eventName;

  @JsonProperty("error")
  private String errorMessage;

  @JsonProperty("ok")
  private String status;

  @JsonProperty("data")
  private List<CexioPosition> positions;

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<CexioPosition> getPositions() {
    return positions;
  }

  public void setPositions(List<CexioPosition> positions) {
    this.positions = positions;
  }
}

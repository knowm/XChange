package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;

/** @author pchertalev */
public class KrakenSystemStatus extends KrakenEvent {

  private final String connectionID;
  /** online|maintenance|(custom status tbd) */
  private final String status;

  private final String version;

  @JsonCreator
  public KrakenSystemStatus(
      @JsonProperty("event") KrakenEventType event,
      @JsonProperty("connectionID") String connectionID,
      @JsonProperty("status") String status,
      @JsonProperty("version") String version) {
    super(event);
    this.connectionID = connectionID;
    this.status = status;
    this.version = version;
  }

  public String getConnectionID() {
    return connectionID;
  }

  public String getStatus() {
    return status;
  }

  public String getVersion() {
    return version;
  }

  @Override
  public String toString() {
    return "KrakenSystemStatus{"
        + "connectionID='"
        + connectionID
        + '\''
        + ", event='"
        + super.getEvent()
        + '\''
        + ", status='"
        + status
        + '\''
        + ", version='"
        + version
        + '\''
        + '}';
  }
}

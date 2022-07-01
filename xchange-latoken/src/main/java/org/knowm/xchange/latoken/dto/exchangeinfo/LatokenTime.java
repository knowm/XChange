package org.knowm.xchange.latoken.dto.exchangeinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 * Response schema:
 *
 * <pre>
 * {
 * 	"time": "2019-04-18T9:00:00.0Z",
 * 	"unixTimeSeconds": 1555578000,
 * 	"unixTimeMiliseconds": 1555578000000
 * }
 * </pre>
 *
 * @author Ezer
 */
public class LatokenTime {
  private final Date time;

  public LatokenTime(
      @JsonProperty("time") String time,
      @JsonProperty("unixTimeSeconds") long unixTimeSeconds,
      @JsonProperty("unixTimeMiliseconds") long unixTimeMiliseconds) {

    this.time = new Date(unixTimeMiliseconds);
  }

  public Date getTime() {
    return time;
  }
}

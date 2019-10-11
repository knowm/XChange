package org.knowm.xchange.latoken.dto.exchangeinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LatokenRateLimit {

  private final String endpoint;
  private final String timePeriod;
  private final long requestLimit;

  /**
   * C'tor
   *
   * @param endpoint
   * @param timePeriod
   * @param requestLimit
   */
  public LatokenRateLimit(
      @JsonProperty("endpoint") String endpoint,
      @JsonProperty("timePeriod") String timePeriod,
      @JsonProperty("requestLimit") long requestLimit) {

    this.endpoint = endpoint;
    this.timePeriod = timePeriod;
    this.requestLimit = requestLimit;
  }

  /**
   * API endpoint with method type
   *
   * @return
   */
  public String getEndpoint() {
    return endpoint;
  }

  /**
   * Time limit of requests
   *
   * @return
   */
  public String getTimePeriod() {
    return timePeriod;
  }

  /**
   * Amount of requests allowed in time period
   *
   * @return
   */
  public long getRequestLimit() {
    return requestLimit;
  }

  @Override
  public String toString() {
    return "LatokenRateLimit [endpoint = "
        + endpoint
        + ", timePeriod = "
        + timePeriod
        + ", requestLimit = "
        + requestLimit
        + "]";
  }
}

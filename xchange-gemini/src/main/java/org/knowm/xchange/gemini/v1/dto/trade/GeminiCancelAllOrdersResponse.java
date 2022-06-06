package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class GeminiCancelAllOrdersResponse {
  private final String result;
  private final Details details;

  public GeminiCancelAllOrdersResponse(
      @JsonProperty("result") String result, @JsonProperty("details") Details details) {
    this.result = result;
    this.details = details;
  }

  @Getter
  public static final class Details {
    private final long[] cancelRejects;
    private final long[] cancelledOrders;

    public Details(
        @JsonProperty("cancelRejects") long[] cancelsRejects,
        @JsonProperty("cancelledOrders") long[] cancelledOrders) {
      this.cancelRejects = cancelsRejects;
      this.cancelledOrders = cancelledOrders;
    }
  }
}

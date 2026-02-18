package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class KrakenEarnAmountAllocated {
  @JsonProperty("bonding")
  private final State bonding;

  @JsonProperty("exit_queue")
  private final State exitQueue;

  @JsonProperty("unbonding")
  private final State unbonding;

  @JsonProperty("pending")
  private final KrakenEarnAmount pending;

  @JsonProperty("total")
  private final KrakenEarnAmount total;

  @Getter
  @ToString
  @Builder
  @Jacksonized
  public static class State {
    @JsonProperty("native")
    private final BigDecimal nativeAmount;

    @JsonProperty("converted")
    private final BigDecimal convertedAmount;

    @JsonProperty("allocation_count")
    private final Integer allocationCount;

    @JsonProperty("allocations")
    private final List<KrakenEarnAllocationDetail> allocations;
  }
}

package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class BybitInternalDepositRecordsResponse {

  @JsonProperty("rows")
  private List<BybitInternalDepositRecord> rows;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;
  @Getter
  @ToString
  @Builder
  @Jacksonized
  public static class BybitInternalDepositRecord {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("coin")
    private String coin;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("status")
    private BybitInternalDepositStatus status;

    @JsonProperty("address")
    private String address;

    @JsonProperty("createdTime")
    private Date createdTime;

    @Getter
    @AllArgsConstructor
    public enum BybitInternalDepositStatus {

      PROCESSING(0),

      SUCCESS(1),

      FAILED(2);

      @JsonValue
      private final int value;
    }
  }
}

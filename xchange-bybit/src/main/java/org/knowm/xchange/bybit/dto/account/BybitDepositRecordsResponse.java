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
public class BybitDepositRecordsResponse {

  @JsonProperty("rows")
  private List<BybitDepositRecord> rows;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;

  @Getter
  @ToString
  @Builder
  @Jacksonized
  public static class BybitDepositRecord {

    @JsonProperty("coin")
    private String coin;

    @JsonProperty("chain")
    private String chain;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("txID")
    private String txID;

    @JsonProperty("status")
    private BybitDepositStatus status;

    @JsonProperty("toAddress")
    private String toAddress;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("depositFee")
    private BigDecimal depositFee;

    @JsonProperty("successAt")
    private Date successAt;

    @JsonProperty("confirmations")
    private Integer confirmations;

    @JsonProperty("txIndex")
    private Integer txIndex;

    @JsonProperty("blockHash")
    private String blockHash;

    @JsonProperty("batchReleaseLimit")
    private BigDecimal batchReleaseLimit;

    @JsonProperty("depositType")
    private BybitDepositType depositType;

    @Getter
    @AllArgsConstructor
    public enum BybitDepositStatus {

      UNKNOWN(0),
      TO_BE_CONFIRMED(1),
      PROCESSING(2),
      SUCCESS(3),
      DEPOSIT_FAILED(4),
      PENDING_TO_BE_CREDITED_TO_FUNDING_POOL(10011),
      CREDITED_TO_FUNDING_POOL_SUCCESSFULLY(10012);

      @JsonValue
      private final Integer value;
    }

    @Getter
    @AllArgsConstructor
    public enum BybitDepositType {

      NORMAL_DEPOSIT(0),
      DAILY_DEPOSIT_LIMIT_REACHED(10),
      ABNORMAL_DEPOSIT(20);

      @JsonValue
      private final Integer value;
    }
  }
}

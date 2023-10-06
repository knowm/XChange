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
public class BybitWithdrawRecordsResponse {

  @JsonProperty("rows")
  private List<BybitWithdrawRecord> rows;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;

  @Getter
  @ToString
  @Builder
  @Jacksonized
  public static class BybitWithdrawRecord {

    @JsonProperty("withdrawId")
    private String withdrawId;

    @JsonProperty("txID")
    private String txID;

    @JsonProperty("withdrawType")
    private Integer withdrawType;

    @JsonProperty("coin")
    private String coin;

    @JsonProperty("chain")
    private String chain;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("withdrawFee")
    private BigDecimal withdrawFee;

    @JsonProperty("status")
    private BybitWithdrawStatus status;

    @JsonProperty("toAddress")
    private String toAddress;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("createTime")
    private Date createTime;

    @JsonProperty("updateTime")
    private Date updateTime;

    @Getter
    @AllArgsConstructor
    public enum BybitWithdrawStatus {
      SECURITY_CHECK("SecurityCheck"),
      PENDING("Pending"),
      SUCCESS("success"),
      CANCEL_BY_USER("CancelByUser"),
      REJECT("Reject"),
      FAIL("Fail"),
      BLOCKCHAIN_CONFIRMED("BlockchainConfirmed"),
      UNKNOWN("Unknown");

      @JsonValue private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum BybitWithdrawType {
      ON_CHAIN(0),
      OFF_CHAIN(1),
      ALL(2);

      @JsonValue private final Integer value;
    }
  }
}

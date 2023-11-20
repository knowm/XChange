package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;

@Getter
@ToString
@Builder
@Jacksonized
public class BybitTransfersResponse {

  @JsonProperty("list")
  private List<BybitTransfer> internalTransfers;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;

  @Getter
  @ToString
  @Builder
  @Jacksonized
  public static class BybitTransfer {

    @JsonProperty("transferId")
    private String transferId;

    @JsonProperty("coin")
    private String coin;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("fromMemberId")
    private String fromMember;

    @JsonProperty("toMemberId")
    private String toMember;

    @JsonProperty("fromAccountType")
    private BybitAccountType fromAccountType;

    @JsonProperty("toAccountType")
    private BybitAccountType toAccountType;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("status")
    private BybitTransferStatus status;
  }

  public enum BybitTransferStatus {
    SUCCESS,
    FAILED,
    PENDING
  }
}

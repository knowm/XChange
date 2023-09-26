package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;

@Getter
@ToString
@AllArgsConstructor
public class BybitInternalTransfersResponse {

  @JsonProperty("list")
  private List<BybitInternalTransfer> internalTransfers;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;

  @AllArgsConstructor
  @Getter
  @ToString
  public static class BybitInternalTransfer {

    @JsonProperty("transferId")
    private String transferId;

    @JsonProperty("coin")
    private String coin;

    @JsonProperty("amount")
    private BigDecimal amount;

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

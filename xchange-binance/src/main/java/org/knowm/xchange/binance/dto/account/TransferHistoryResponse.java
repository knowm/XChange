package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

public final class TransferHistoryResponse
    extends WapiResponse<List<TransferHistoryResponse.TransferHistory>> {

  private final TransferHistory[] transferHistories;

  public TransferHistoryResponse(
      @JsonProperty("transfers") TransferHistory[] transferHistories,
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg) {
    super(success, msg);
    this.transferHistories = transferHistories;
  }

  @Override
  public List<TransferHistory> getData() {
    return Arrays.asList(transferHistories);
  }

  @Override
  public String toString() {
    return "transferHistories [rows=" + Arrays.toString(transferHistories) + "]";
  }

  @Data
  public static final class TransferHistory {
    private String from;
    private String to;
    private String asset;
    private BigDecimal qty;
    private long time;
  }
}

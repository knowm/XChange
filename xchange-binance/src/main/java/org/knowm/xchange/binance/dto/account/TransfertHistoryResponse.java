package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

public final class TransfertHistoryResponse
    extends WapiResponse<List<TransfertHistoryResponse.TransfertHistory>> {

  private final TransfertHistory[] transfertHistories;

  public TransfertHistoryResponse(
      @JsonProperty("transfers") TransfertHistoryResponse.TransfertHistory[] transfertHistories,
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg) {
    super(success, msg);
    this.transfertHistories = transfertHistories;
  }

  @Override
  public List<TransfertHistory> getData() {
    return Arrays.asList(transfertHistories);
  }

  @Override
  public String toString() {
    return "transfertHistories [rows=" + Arrays.toString(transfertHistories) + "]";
  }

  @Data
  public static final class TransfertHistory {
    private String from;
    private String to;
    private String asset;
    private BigDecimal qty;
    private long time;

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public String getTo() {
      return to;
    }

    public void setTo(String to) {
      this.to = to;
    }

    public String getAsset() {
      return asset;
    }

    public void setAsset(String asset) {
      this.asset = asset;
    }

    public BigDecimal getQty() {
      return qty;
    }

    public void setQty(BigDecimal qty) {
      this.qty = qty;
    }

    public long getTime() {
      return time;
    }

    public void setTime(long time) {
      this.time = time;
    }
  }
}

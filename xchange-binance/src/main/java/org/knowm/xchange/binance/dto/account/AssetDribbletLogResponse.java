package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

public final class AssetDribbletLogResponse
    extends WapiResponse<AssetDribbletLogResponse.AssetDribbletLogResults> {

  private final AssetDribbletLogResults results;

  public AssetDribbletLogResponse(
      @JsonProperty("results") AssetDribbletLogResults results,
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg) {
    super(success, msg);
    this.results = results;
  }

  @Override
  public AssetDribbletLogResults getData() {
    return results;
  }

  @Override
  public String toString() {
    return "AssetDribbletLogResult [results="
        + results
        + ", success="
        + success
        + ", msg="
        + msg
        + "]";
  }

  @Data
  @NoArgsConstructor
  public static class AssetDribbletLogResults {
    private BigDecimal total;
    private BnbExchange[] rows;

    public List<BnbExchange> getData() {
      return Arrays.asList(rows);
    }
  }

  @Data
  @NoArgsConstructor
  public static class BnbExchange {
    private String transfered_total;
    private String service_charge_total;
    private BigInteger tran_id;
    private BnbExchangeLog[] logs;
    private String operate_time;

    public List<BnbExchangeLog> getData() {
      return Arrays.asList(logs);
    }

    public LocalDateTime getOperateTime() {
      return LocalDateTime.parse(operate_time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
  }

  @Data
  @NoArgsConstructor
  public static class BnbExchangeLog {
    private BigInteger tranId;
    private String serviceChargeAmount;
    private String uid;
    private String amount;
    private String operateTime;
    private String transferedAmount;
    private String fromAsset;

    public LocalDateTime getOperateTime() {
      return LocalDateTime.parse(operateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
  }
}

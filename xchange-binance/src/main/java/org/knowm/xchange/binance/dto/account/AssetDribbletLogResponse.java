package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public final class AssetDribbletLogResponse
    extends WapiResponse<List<AssetDribbletLogResponse.AssetDribbletLogResult>> {

  private final AssetDribbletLogResult results;

  public AssetDribbletLogResponse(
      @JsonProperty("results") AssetDribbletLogResult results,
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg) {
    super(success, msg);
    this.results = results;
  }

  @Override
  public List<AssetDribbletLogResult> getData() {
    return Collections.singletonList(results);
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
  public static final class AssetDribbletLogResult {
    private BigDecimal total;
    private BnbExchange[] rows;
  }

  @Data
  public static final class BnbExchange {
    private String transferedTotal;
    private String serviceChargeTotal;
    private BigInteger tranId;
    private BnbExchangeLog[] bnbExchangeLogs;
    private LocalDateTime operateTime;
  }

  @Data
  public static final class BnbExchangeLog {
    private BigInteger tranId;
    private String serviceChargeAmount;
    private String uid;
    private String amount;
    private LocalDateTime operateTime;
    private String transferedAmount;
    private String fromAsset;
  }
}

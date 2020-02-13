package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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
  public static final class AssetDribbletLogResults {
    private BigDecimal total;
    private BnbExchange[] rows;

    public List<BnbExchange> getData() {
      return Arrays.asList(rows);
    }
  }

  @Data
  public static final class BnbExchange {
    private String transfered_total;
    private String service_charge_total;
    private BigInteger tran_id;
    private BnbExchangeLog[] bnbExchangeLogs;
    private LocalDateTime operate_time;

    public List<BnbExchangeLog> getData() {
      return Arrays.asList(bnbExchangeLogs);
    }
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
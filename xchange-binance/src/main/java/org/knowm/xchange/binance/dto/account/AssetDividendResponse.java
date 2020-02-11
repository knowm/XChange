package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public final class AssetDividendResponse
    extends WapiResponse<List<AssetDividendResponse.assetDividendRow>> {

  private final assetDividendRow[] rows;

  public AssetDividendResponse(
      @JsonProperty("rows") assetDividendRow[] rows,
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg) {
    super(success, msg);
    this.rows = rows;
  }

  @Override
  public List<assetDividendRow> getData() {
    return Arrays.asList(rows);
  }

  @Override
  public String toString() {
    return "assetDividendRow [rows="
        + Arrays.toString(rows)
        + ", success="
        + success
        + ", msg="
        + msg
        + "]";
  }

  @Data
  public static final class assetDividendRow {
    private BigDecimal amount;
    private String asset;
    private long divTime;
    private String enInfo;
    private long tranId;
  }
}

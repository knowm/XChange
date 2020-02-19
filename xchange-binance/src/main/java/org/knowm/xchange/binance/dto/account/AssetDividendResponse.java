package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public final class AssetDividendResponse
    extends SapiResponse<List<AssetDividendResponse.AssetDividend>> {
  private final AssetDividend[] rows;
  private final BigDecimal total;

  public AssetDividendResponse(@JsonProperty("rows") AssetDividend[] rows, @JsonProperty("total") BigDecimal total) {
    this.rows = rows;
    this.total = total;
  }

  @Override
  public List<AssetDividend> getData() {
    return Arrays.asList(rows);
  }

  public BigDecimal getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return "assetDividendRow [rows=" + Arrays.toString(rows) + "]";
  }

  @Data
  public static final class AssetDividend {
    private BigDecimal amount;
    private String asset;
    private long divTime;
    private String enInfo;
    private long tranId;
  }
}

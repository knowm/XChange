package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.Data;

public final class AssetDividendList extends SapiResponse<List<AssetDividendList.AssetDividend>> {

  private final AssetDividend[] rows;

  private final Long total;

  public AssetDividendList(
      @JsonProperty("rows") AssetDividend[] rows,
      @JsonProperty("total") Long total) {
    this.rows = rows;
    this.total = total;
  }

  @Override
  public List<AssetDividend> getData() {
    return Arrays.asList(rows);
  }

  @Override
  public String toString() {
    return "AssetDividendList [rows=" + Arrays.toString(rows) + "]";
  }

  @Data
  public static final class AssetDividend {

    //    {
    //           "amount":"10.00000000",
    //            "asset":"BHFT",
    //            "divTime":1563189166000,
    //            "enInfo":"BHFT distribution",
    //            "tranId":2968885920
    //    },

    private BigDecimal amount;
    private String asset;
    private Long divTime;
    private String enInfo;
    private Long tranId;
  }
}

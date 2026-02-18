package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public final class BinanceLockedPositionResponse extends SapiResponse<List<BinanceLockedPosition>> {

  @JsonProperty("rows")
  private final List<BinanceLockedPosition> rows;

  @JsonProperty("total")
  private final Long total;

  @Override
  public List<BinanceLockedPosition> getData() {
    return rows != null ? rows : List.of();
  }
}

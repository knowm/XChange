package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public final class BinanceFlexiblePositionResponse
    extends SapiResponse<List<BinanceFlexiblePosition>> {

  @JsonProperty("rows")
  private final List<BinanceFlexiblePosition> rows;

  @JsonProperty("total")
  private final Long total;

  @Override
  public List<BinanceFlexiblePosition> getData() {
    return rows != null ? rows : List.of();
  }
}

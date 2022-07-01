package org.knowm.xchange.coincheck.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoincheckTradesContainer {
  @JsonProperty boolean success;
  @JsonProperty CoincheckPagination pagination;
  @JsonProperty List<CoincheckTrade> data;
}

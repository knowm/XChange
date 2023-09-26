package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.BybitCategory;

@Getter
@ToString
@Builder
@Jacksonized
public class BybitTradeHistoryResponse {

  @JsonProperty("category")
  private BybitCategory category;

  @JsonProperty("list")
  private List<BybitUserTradeDto> tradeHistoryList;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;
}

package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.bybit.dto.BybitCategory;

@Getter
@ToString
@AllArgsConstructor
public class BybitTradeHistoryResponse {

  @JsonProperty("category")
  private BybitCategory category;

  @JsonProperty("list")
  private List<BybitUserTradeDto> tradeHistoryList;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;

  /** No args constructor for use in serialization */
  public BybitTradeHistoryResponse() {
  }
}

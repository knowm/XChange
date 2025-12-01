package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class DeribitTrades {

  @JsonProperty("trades")
  private List<DeribitTrade> trades;

  @JsonProperty("has_more")
  private boolean hasMore;
}

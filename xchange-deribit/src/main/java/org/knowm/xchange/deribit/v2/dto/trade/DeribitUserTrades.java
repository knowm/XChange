package org.knowm.xchange.deribit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class DeribitUserTrades {

  private List<DeribitUserTrade> trades;

  @JsonProperty("has_more")
  private boolean hasMore;
}

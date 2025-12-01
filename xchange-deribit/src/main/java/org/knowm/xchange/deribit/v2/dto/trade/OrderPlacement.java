package org.knowm.xchange.deribit.v2.dto.trade;

import java.util.List;
import lombok.Data;

@Data
public class OrderPlacement {

  private List<DeribitUserTrade> trades;
  private DeribitOrder order;
}

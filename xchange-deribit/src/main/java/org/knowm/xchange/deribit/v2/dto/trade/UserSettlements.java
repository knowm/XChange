package org.knowm.xchange.deribit.v2.dto.trade;

import java.util.List;
import lombok.Data;

@Data
public class UserSettlements {

  private List<Settlement> settlements;

  /** Continuation token for pagination. */
  private String continuation;

  public boolean hasMore() {
    return !"none".equals(continuation);
  }

}

package org.knowm.xchange.okcoin.v3.dto.trade;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.okcoin.v3.dto.account.FuturesPosition;

@Data
@EqualsAndHashCode(callSuper = false)
public class FuturesPositionsResponse extends OkexResponse {

  private List<List<FuturesPosition>> holding;
}

package org.knowm.xchange.bittrex.dto.batch;

import java.util.Map;
import lombok.Data;

@Data
public class BatchResponse {
  /**
   * possible payloads examples:
   *
   * <p>1st example: BatchResponse(payload={id=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx,
   * marketSymbol=ANT-ETH, direction=BUY, type=LIMIT, quantity=93.47332424, limit=0.00610003,
   * timeInForce=GOOD_TIL_CANCELLED, fillQuantity=0.00000000, commission=0.00000000,
   * proceeds=0.00000000, status=CLOSED, createdAt=2020-07-01T08:57:51.26Z,
   * updatedAt=2020-07-01T08:57:52.62Z, closedAt=2020-07-01T08:57:52.62Z}, status=200)
   *
   * <p>2nd example: BatchResponse(payload={code=INSUFFICIENT_FUNDS}, status=409)
   */
  private Map payload;

  private String status;
}

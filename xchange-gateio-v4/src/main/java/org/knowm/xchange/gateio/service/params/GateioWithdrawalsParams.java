package org.knowm.xchange.gateio.service.params;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
public class GateioWithdrawalsParams {

  private Currency currency;

  private Integer pageLength;

  private Integer zeroBasedPageNumber;

  private Instant startTime;

  private Instant endTime;

}

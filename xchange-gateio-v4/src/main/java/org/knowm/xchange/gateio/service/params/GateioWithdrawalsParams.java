package org.knowm.xchange.gateio.service.params;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GateioWithdrawalsParams {

  private Currency currency;

  private Integer pageLength;

  private Integer zeroBasedPageNumber;

  private Instant startTime;

  private Instant endTime;

}

package org.knowm.xchange.gateio.dto.account.params;

import java.time.Instant;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class GateioSubAccountTransfersParams {

  private String subAccountId;

  private Integer pageLength;

  private Integer zeroBasedPageNumber;

  private Instant startTime;

  private Instant endTime;

}

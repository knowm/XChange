package org.knowm.xchange.bitget.dto.account.params;

import java.time.Instant;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.bitget.dto.account.BitgetAccountType;
import org.knowm.xchange.currency.Currency;

@Data
@SuperBuilder
public class BitgetTransferHistoryParams {

  private Currency currency;

  private BitgetAccountType fromAccountType;

  private Instant startTime;

  private Instant endTime;

  private String clientOid;

  private Integer limit;

  private String endId;
}

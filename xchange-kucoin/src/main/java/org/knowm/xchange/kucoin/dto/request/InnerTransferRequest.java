package org.knowm.xchange.kucoin.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
public class InnerTransferRequest {

  /** Request ID */
  private final String clientOid;
  /** Account ID of payer(obtained through the "list account" interface). */
  private final String payAccountId;
  /** Account ID of receiver */
  private final String recAccountId;
  /**
   * Transfer amount, a quantity that exceeds the precision of the currency（ Obtained through the
   * currencies interface ）.
   */
  private final BigDecimal amount;
}

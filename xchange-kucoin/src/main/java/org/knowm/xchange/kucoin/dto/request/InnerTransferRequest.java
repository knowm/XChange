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

  /** currency */
  private final String currency;

  /** Account type of payer: main, trade, margin or pool */
  private final String from;

  /** Account type of payee: main, trade, margin or pool */
  private final String to;

  /** Transfer amount, the amount is a positive integer multiple of the currency precision. */
  private final BigDecimal amount;
}

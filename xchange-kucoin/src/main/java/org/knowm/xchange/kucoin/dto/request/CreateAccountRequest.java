package org.knowm.xchange.kucoin.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
public class CreateAccountRequest {

  /** Account type: main, trade, margin */
  private final String type;
  /** currency */
  private final String currency;
}

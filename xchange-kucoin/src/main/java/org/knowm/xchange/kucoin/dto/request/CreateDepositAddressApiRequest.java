package org.knowm.xchange.kucoin.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
public class CreateDepositAddressApiRequest {

  String currency;

  String chain;

}

package org.knowm.xchange.coinsph.dto.account;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class CoinsphCashOutResponse {
  String externalOrderId;
  String internalOrderId;
}

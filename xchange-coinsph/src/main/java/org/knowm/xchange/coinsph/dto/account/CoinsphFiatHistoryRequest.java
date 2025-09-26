package org.knowm.xchange.coinsph.dto.account;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CoinsphFiatHistoryRequest {
  String fiatCurrency;

  @Builder.Default int pageSize = 100;
}

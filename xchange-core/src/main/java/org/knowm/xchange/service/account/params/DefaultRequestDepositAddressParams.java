package org.knowm.xchange.service.account.params;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Value
@NonFinal
@Builder
@Jacksonized
public class DefaultRequestDepositAddressParams implements RequestDepositAddressParams {
  Currency currency;
  String network;

  @Builder.Default boolean newAddress = false;

  String[] extraArguments;

  public static DefaultRequestDepositAddressParams create(Currency currency, String... args) {
    return DefaultRequestDepositAddressParams.builder()
        .currency(currency)
        .extraArguments(args)
        .build();
  }
}

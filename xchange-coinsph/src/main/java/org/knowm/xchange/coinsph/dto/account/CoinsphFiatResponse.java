package org.knowm.xchange.coinsph.dto.account;

import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@Value
@NonFinal
public class CoinsphFiatResponse<T> {
  int status;
  String error;
  T data;
}

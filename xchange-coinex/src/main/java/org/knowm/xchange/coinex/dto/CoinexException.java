package org.knowm.xchange.coinex.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CoinexException extends RuntimeException {

  Integer code;

  Object data;

  String message;
}

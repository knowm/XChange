package org.knowm.xchange.bitfinex.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.bitfinex.dto.BitfinexException;

@SuppressWarnings("serial")
@Getter
@ToString
public class BitfinexExceptionV2 extends BitfinexException {

  private String type;
  private Long errorCode;
  private String message;

  // ["error",10100,"apikey: invalid"]
  @JsonCreator
  public BitfinexExceptionV2(Object[] arr) {
    type = arr[0].toString();
    Object errorCode = arr[1];
    this.errorCode = errorCode == null ? null : Long.valueOf(errorCode.toString());
    message = arr[2].toString();
  }
}

package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.enigma.dto.BaseResponse;

@Setter
@Getter
public class EnigmaDepositAddressResponse extends BaseResponse {
  @JsonProperty("result")
  private boolean result;

  @JsonProperty("message")
  private String message;

  @JsonProperty("address")
  private String address;

}

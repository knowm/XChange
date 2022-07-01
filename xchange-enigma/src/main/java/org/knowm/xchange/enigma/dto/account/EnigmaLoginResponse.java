package org.knowm.xchange.enigma.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.enigma.dto.BaseResponse;

@Getter
@Setter
public class EnigmaLoginResponse extends BaseResponse {

  private String key;

  public EnigmaLoginResponse(
      @JsonProperty("code") Integer code,
      @JsonProperty("message") String message,
      @JsonProperty("result") Boolean result,
      @JsonProperty("key") String key) {
    super(code, message, result);
    this.key = key;
  }

  public String getKey() {
    return this.key;
  }
}

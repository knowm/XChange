package org.knowm.xchange.livecoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.livecoin.dto.LivecoinBaseResponse;

/** @author walec51 */
public class LivecoinPaymentOutResponse extends LivecoinBaseResponse {

  private final Map data;

  public LivecoinPaymentOutResponse(
      @JsonProperty("success") Boolean success, @JsonProperty("data") Map data) {
    super(success);
    this.data = data;
  }

  public Map getData() {
    return data;
  }
}

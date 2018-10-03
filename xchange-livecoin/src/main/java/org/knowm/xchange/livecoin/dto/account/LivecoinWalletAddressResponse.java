package org.knowm.xchange.livecoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.livecoin.dto.LivecoinBaseResponse;

/** @author walec51 */
public class LivecoinWalletAddressResponse extends LivecoinBaseResponse {

  private final String wallet;

  public LivecoinWalletAddressResponse(
      @JsonProperty("success") Boolean success, @JsonProperty("wallet") String wallet) {
    super(success);
    this.wallet = wallet;
  }

  public String getWallet() {
    return wallet;
  }
}

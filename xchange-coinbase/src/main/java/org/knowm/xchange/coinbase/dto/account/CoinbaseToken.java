package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;

/** @author jamespedwards42 */
public class CoinbaseToken extends CoinbaseBaseResponse {

  private final CoinbaseTokenInfo token;

  private CoinbaseToken(
      @JsonProperty("token") final CoinbaseTokenInfo token,
      @JsonProperty("success") final boolean success,
      @JsonProperty("errors") final List<String> errors) {

    super(success, errors);
    this.token = token;
  }

  public String getTokenId() {

    return token.getTokenId();
  }

  public String getAddress() {

    return token.getAddress();
  }

  @Override
  public String toString() {

    return "CoinbaseToken [token=" + token + "]";
  }

  private static class CoinbaseTokenInfo {

    private final String tokenId;
    private final String address;

    private CoinbaseTokenInfo(
        @JsonProperty("token_id") final String tokenId,
        @JsonProperty("address") final String address) {

      this.tokenId = tokenId;
      this.address = address;
    }

    private String getTokenId() {

      return tokenId;
    }

    private String getAddress() {

      return address;
    }

    @Override
    public String toString() {

      return "CoinbaseTokenInfo [tokenId=" + tokenId + ", address=" + address + "]";
    }
  }
}

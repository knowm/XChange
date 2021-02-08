package info.bitrich.xchangestream.coinmate.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.coinmate.v2.dto.auth.AuthParams;

import java.util.Map;

public class CoinmateAuthenticatedChannelData {
  @JsonProperty("channel")
  private String channel;

  @JsonProperty("clientId")
  private String clientId;

  @JsonProperty("publicKey")
  private String publicKey;

  @JsonProperty("signature")
  private String signature;

  @JsonProperty("nonce")
  private String nonce;

  @JsonCreator
  public CoinmateAuthenticatedChannelData(@JsonProperty("channel") String channel,
                                          AuthParams authParams) {
    this.channel = channel;
    Map<String, String> params = authParams.getParams();
    clientId = params.get("clientId");
    nonce = params.get("nonce");
    publicKey = params.get("publicKey");
    signature = params.get("signature");
  }

  public String getChannel() {
    return channel;
  }
}

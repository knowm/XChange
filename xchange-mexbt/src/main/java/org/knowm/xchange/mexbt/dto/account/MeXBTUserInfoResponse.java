package org.knowm.xchange.mexbt.dto.account;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.mexbt.dto.MeXBTResponse;

public class MeXBTUserInfoResponse extends MeXBTResponse {

  private final Map<String, String>[] userInfoKVP;

  public MeXBTUserInfoResponse(@JsonProperty("isAccepted") boolean isAccepted, @JsonProperty("rejectReason") String rejectReason,
      @JsonProperty("userInfoKVP") Map<String, String>[] userInfoKVP) {
    super(isAccepted, rejectReason);
    this.userInfoKVP = userInfoKVP;
  }

  public Map<String, String>[] getUserInfoKVP() {
    return userInfoKVP;
  }

}

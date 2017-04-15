package org.knowm.xchange.livecoin.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LivecoinRestrictions {

  private final Boolean success;
  private List<LivecoinRestriction> restrictions = new ArrayList<>();

  public LivecoinRestrictions(@JsonProperty("success") Boolean success, @JsonProperty("restrictions") List<LivecoinRestriction> restrictions) {
    super();
    this.success = success;
    this.restrictions = restrictions;
  }

  public Boolean getSuccess() {
    return success;
  }

  public List<LivecoinRestriction> getRestrictions() {
    return restrictions;
  }

}

package org.knowm.xchange.dase.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DaseUserProfile {

  private final String portfolioId;

  @JsonCreator
  public DaseUserProfile(@JsonProperty("portfolio_id") String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getPortfolioId() {
    return portfolioId;
  }
}

package org.knowm.xchange.gatehub.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Wallet {
  private String address;

  private String name;

  public String getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }
}

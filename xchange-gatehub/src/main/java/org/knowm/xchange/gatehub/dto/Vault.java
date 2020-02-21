package org.knowm.xchange.gatehub.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Vault {
  private String assetCode;

  private Wallet coldWallet;

  public String getAssetCode() {
    return assetCode;
  }

  public Wallet getColdWallet() {
    return coldWallet;
  }
}

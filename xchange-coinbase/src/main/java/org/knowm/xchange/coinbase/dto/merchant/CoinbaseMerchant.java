package org.knowm.xchange.coinbase.dto.merchant;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author jamespedwards42 */
public class CoinbaseMerchant {

  private final String companyName;
  private final String logo;

  private CoinbaseMerchant(
      @JsonProperty("company_name") final String companyName,
      @JsonProperty("logo") final String logo) {

    this.companyName = companyName;
    this.logo = logo;
  }

  public String getCompanyName() {

    return companyName;
  }

  public String getLogo() {

    return logo;
  }

  @Override
  public String toString() {

    return "CoinbaseMerchant [companyName=" + companyName + ", logo=" + logo + "]";
  }
}

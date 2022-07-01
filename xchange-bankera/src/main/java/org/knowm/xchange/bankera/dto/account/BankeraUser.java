package org.knowm.xchange.bankera.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankeraUser {
  private int id;
  private List<BankeraWallet> wallets;

  public BankeraUser(
      @JsonProperty("id") int id, @JsonProperty("wallets") List<BankeraWallet> wallets) {
    this.id = id;
    this.wallets = wallets;
  }

  public int getId() {
    return id;
  }

  public List<BankeraWallet> getWallets() {
    return wallets;
  }
}

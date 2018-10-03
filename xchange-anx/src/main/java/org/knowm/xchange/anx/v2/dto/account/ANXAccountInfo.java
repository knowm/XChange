package org.knowm.xchange.anx.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.anx.v2.dto.ANXValue;

/** Data object representing Account Info from ANX */
public final class ANXAccountInfo {

  private final String login;
  private final String index;
  private final String id;
  private final List<String> rights;
  private final String language;
  private final String created;
  private final String lastLogin;
  private final Map<String, ANXWallet> wallets;
  private final ANXValue monthlyVolume;
  private final BigDecimal tradeFee;

  /**
   * Constructor
   *
   * @param login
   * @param index
   * @param id
   * @param rights
   * @param language
   * @param created
   * @param lastLogin
   * @param wallets
   * @param monthlyVolume
   * @param tradeFee
   */
  public ANXAccountInfo(
      @JsonProperty("Login") String login,
      @JsonProperty("Index") String index,
      @JsonProperty("Id") String id,
      @JsonProperty("Rights") List<String> rights,
      @JsonProperty("Language") String language,
      @JsonProperty("Created") String created,
      @JsonProperty("Last_Login") String lastLogin,
      @JsonProperty("Wallets") Map<String, ANXWallet> wallets,
      @JsonProperty("Monthly_Volume") ANXValue monthlyVolume,
      @JsonProperty("Trade_Fee") BigDecimal tradeFee) {

    this.login = login;
    this.index = index;
    this.id = id;
    this.rights = rights;
    this.language = language;
    this.created = created;
    this.lastLogin = lastLogin;
    this.wallets = wallets;
    this.monthlyVolume = monthlyVolume;
    this.tradeFee = tradeFee;
  }

  public String getLogin() {

    return login;
  }

  public String getIndex() {

    return index;
  }

  public String getId() {

    return id;
  }

  public List<String> getRights() {

    return rights;
  }

  public String getLanguage() {

    return language;
  }

  public String getCreated() {

    return created;
  }

  public String getLastLogin() {

    return lastLogin;
  }

  public Map<String, ANXWallet> getWallets() {

    return wallets;
  }

  public ANXValue getMonthlyVolume() {

    return monthlyVolume;
  }

  public BigDecimal getTradeFee() {

    return tradeFee;
  }

  @Override
  public String toString() {

    return "ANXAccountInfo [login="
        + login
        + ", index="
        + index
        + ", id="
        + id
        + ", rights="
        + rights
        + ", language="
        + language
        + ", created="
        + created
        + ", lastLogin="
        + lastLogin
        + ", wallets="
        + wallets
        + ", monthlyVolume="
        + monthlyVolume
        + ", tradeFee="
        + tradeFee
        + "]";
  }
}

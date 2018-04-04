package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.auth.CoinbaseOAuth;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseMerchant;
import org.knowm.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;

/** @author jamespedwards42 */
@JsonInclude(Include.NON_NULL)
public class CoinbaseUser extends CoinbaseBaseResponse {

  @JsonProperty("user")
  private final CoinbaseUserInfo user;

  private final CoinbaseOAuth oAuth;

  @JsonProperty("client_id")
  private String oAuthClientId;

  private CoinbaseUser(
      @JsonProperty("user") final CoinbaseUserInfo user,
      @JsonProperty("oauth") final CoinbaseOAuth oAuth,
      @JsonProperty("success") final boolean success,
      @JsonProperty("errors") final List<String> errors) {

    super(success, errors);
    this.user = user;
    this.oAuth = oAuth;
  }

  public CoinbaseUser(CoinbaseUserInfo user) {

    super(true);
    this.user = user;
    this.oAuth = null;
  }

  public static CoinbaseUser createNewCoinbaseUser(String email, final String password) {

    return new CoinbaseUser(new CoinbaseUserInfo(email, password, null));
  }

  public static CoinbaseUser createCoinbaseNewUserWithReferrerId(
      String email, final String password, final String referrerId) {

    return new CoinbaseUser(new CoinbaseUserInfo(email, password, referrerId));
  }

  @JsonIgnore
  public String getId() {

    return user.getId();
  }

  @JsonIgnore
  public String getEmail() {

    return user.getEmail();
  }

  public CoinbaseUser updateEmail(String email) {

    user.setEmail(email);
    return this;
  }

  @JsonIgnore
  public String getName() {

    return user.getName();
  }

  public CoinbaseUser updateName(String name) {

    user.setName(name);
    return this;
  }

  @JsonIgnore
  public String getPassword() {

    return user.getPassword();
  }

  @JsonIgnore
  public String getReceiveAddress() {

    return user.getReceiveAddress();
  }

  @JsonIgnore
  public String getReferrerId() {

    return user.getReferrerId();
  }

  @JsonIgnore
  public String getTimeZone() {

    return user.getTimeZone();
  }

  public CoinbaseUser updateTimeZone(String timeZone) {

    user.setTimeZone(timeZone);
    return this;
  }

  @JsonIgnore
  public CoinbaseMoney getBalance() {

    return user.getBalance();
  }

  @JsonIgnore
  public String getNativeCurrency() {

    return user.getNativeCurrency();
  }

  public CoinbaseUser updateNativeCurrency(String nativeCurrency) {

    user.setNativeCurrency(nativeCurrency);
    return this;
  }

  @JsonIgnore
  public CoinbaseBuySellLevel getBuyLevel() {

    return user.getBuyLevel();
  }

  @JsonIgnore
  public CoinbaseBuySellLevel getSellLevel() {

    return user.getSellLevel();
  }

  @JsonIgnore
  public CoinbaseMoney getBuyLimit() {

    return user.getBuyLimit();
  }

  @JsonIgnore
  public CoinbaseMoney getSellLimit() {

    return user.getSellLimit();
  }

  @JsonIgnore
  public String getPin() {

    return user.getPin();
  }

  public CoinbaseUser updatePin(String pin) {

    user.setPin(pin);
    return this;
  }

  @JsonIgnore
  public CoinbaseMerchant getMerchant() {

    return user.getMerchant();
  }

  @JsonIgnore
  public CoinbaseOAuth getOAuth() {

    return oAuth;
  }

  public String getoAuthClientId() {

    return oAuthClientId;
  }

  public CoinbaseUser withoAuthClientId(String oAuthClientId) {

    this.oAuthClientId = oAuthClientId;
    return this;
  }

  @Override
  public String toString() {

    return "CoinbaseUser [user="
        + user
        + ", oAuth="
        + oAuth
        + ", oAuthClientId="
        + oAuthClientId
        + "]";
  }

  @JsonInclude(Include.NON_NULL)
  public static class CoinbaseUserInfo {

    private final String id;

    @JsonProperty("password")
    private final String password;

    private final String receiveAddress;

    @JsonProperty("referrer_id")
    private final String referrerId;

    private final CoinbaseMoney balance;
    private final CoinbaseBuySellLevel buyLevel;
    private final CoinbaseBuySellLevel sellLevel;
    private final CoinbaseMoney buyLimit;
    private final CoinbaseMoney sellLimit;
    private final CoinbaseMerchant merchant;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("time_zone")
    private String timeZone;

    @JsonProperty("native_currency")
    private String nativeCurrency;

    @JsonProperty("pin")
    private String pin;

    private CoinbaseUserInfo(
        @JsonProperty("id") final String id,
        @JsonProperty("email") final String email,
        @JsonProperty("name") final String name,
        @JsonProperty("password") final String password,
        @JsonProperty("receive_address") final String receiveAddress,
        @JsonProperty("referrer_id") final String referrerId,
        @JsonProperty("time_zone") final String timeZone,
        @JsonProperty("balance") @JsonDeserialize(using = CoinbaseMoneyDeserializer.class)
            final CoinbaseMoney balance,
        @JsonProperty("native_currency") final String nativeCurrency,
        @JsonProperty("buy_level") final CoinbaseBuySellLevel buyLevel,
        @JsonProperty("sell_level") final CoinbaseBuySellLevel sellLevel,
        @JsonProperty("buy_limit") @JsonDeserialize(using = CoinbaseMoneyDeserializer.class)
            final CoinbaseMoney buyLimit,
        @JsonProperty("sell_limit") @JsonDeserialize(using = CoinbaseMoneyDeserializer.class)
            final CoinbaseMoney sellLimit,
        @JsonProperty("pin") final String pin,
        @JsonProperty("merchant") final CoinbaseMerchant merchant) {

      this.id = id;
      this.email = email;
      this.name = name;
      this.password = password;
      this.receiveAddress = receiveAddress;
      this.referrerId = referrerId;
      this.timeZone = timeZone;
      this.balance = balance;
      this.nativeCurrency = nativeCurrency;
      this.buyLevel = buyLevel;
      this.sellLevel = sellLevel;
      this.buyLimit = buyLimit;
      this.sellLimit = sellLimit;
      this.pin = pin;
      this.merchant = merchant;
    }

    private CoinbaseUserInfo(String email, final String password, final String referrerId) {

      this.email = email;
      this.password = password;
      this.referrerId = referrerId;
      this.id = null;
      this.name = null;
      this.receiveAddress = null;
      this.timeZone = null;
      this.balance = null;
      this.nativeCurrency = null;
      this.buyLevel = null;
      this.sellLevel = null;
      this.buyLimit = null;
      this.sellLimit = null;
      this.pin = null;
      this.merchant = null;
    }

    @JsonIgnore
    public String getId() {

      return id;
    }

    public String getEmail() {

      return email;
    }

    private void setEmail(String email) {

      this.email = email;
    }

    public String getName() {

      return name;
    }

    private void setName(String name) {

      this.name = name;
    }

    public String getPassword() {

      return password;
    }

    @JsonIgnore
    public String getReceiveAddress() {

      return receiveAddress;
    }

    public String getReferrerId() {

      return referrerId;
    }

    public String getTimeZone() {

      return timeZone;
    }

    private void setTimeZone(String timeZone) {

      this.timeZone = timeZone;
    }

    @JsonIgnore
    public CoinbaseMoney getBalance() {

      return balance;
    }

    public String getNativeCurrency() {

      return nativeCurrency;
    }

    private void setNativeCurrency(String nativeCurrency) {

      this.nativeCurrency = nativeCurrency;
    }

    @JsonIgnore
    public CoinbaseBuySellLevel getBuyLevel() {

      return buyLevel;
    }

    @JsonIgnore
    public CoinbaseBuySellLevel getSellLevel() {

      return sellLevel;
    }

    @JsonIgnore
    public CoinbaseMoney getBuyLimit() {

      return buyLimit;
    }

    @JsonIgnore
    public CoinbaseMoney getSellLimit() {

      return sellLimit;
    }

    public String getPin() {

      return pin;
    }

    private void setPin(String pin) {

      this.pin = pin;
    }

    @JsonIgnore
    public CoinbaseMerchant getMerchant() {

      return merchant;
    }

    @Override
    public String toString() {

      return "CoinbaseUserInfo [id="
          + id
          + ", email="
          + email
          + ", name="
          + name
          + ", password="
          + password
          + ", receiveAddress="
          + receiveAddress
          + ", referrerId="
          + referrerId
          + ", timeZone="
          + timeZone
          + ", balance="
          + balance
          + ", nativeCurrency="
          + nativeCurrency
          + ", buyLevel="
          + buyLevel
          + ", sellLevel="
          + sellLevel
          + ", buyLimit="
          + buyLimit
          + ", sellLimit="
          + sellLimit
          + ", pin="
          + pin
          + ", merchant="
          + merchant
          + "]";
    }
  }
}

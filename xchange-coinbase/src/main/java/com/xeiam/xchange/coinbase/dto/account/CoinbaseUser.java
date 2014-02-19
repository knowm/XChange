/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinbase.dto.account;

import java.util.List;

import org.joda.money.BigMoney;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.dto.auth.CoinbaseOAuth;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseMerchant;
import com.xeiam.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;

/**
 * @author jamespedwards42
 */
public class CoinbaseUser extends CoinbaseBaseResponse {

  @JsonProperty("user")
  private final CoinbaseUserInfo user;
  private final CoinbaseOAuth oAuth;
  @JsonProperty("client_id")
  private String oAuthClientId;

  private CoinbaseUser(@JsonProperty("user") final CoinbaseUserInfo user, @JsonProperty("oauth") final CoinbaseOAuth oAuth, @JsonProperty("success") final boolean success,
      @JsonProperty("errors") final List<String> errors) {

    super(success, errors);
    this.user = user;
    this.oAuth = oAuth;
  }

  public CoinbaseUser(final CoinbaseUserInfo user) {

    super(true);
    this.user = user;
    this.oAuth = null;
  }

  public static CoinbaseUser createNewCoinbaseUser(final String email, final String password) {

    return new CoinbaseUser(new CoinbaseUserInfo(email, password, null));
  }

  public static CoinbaseUser createCoinbaseNewUserWithReferrerId(final String email, final String password, final String referrerId) {

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

  public CoinbaseUser updateEmail(final String email) {
    
    user.setEmail(email);
    return this;
  }

  @JsonIgnore
  public String getName() {

    return user.getName();
  }

  public CoinbaseUser updateName(final String name) {
    
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

  public CoinbaseUser updateTimeZone(final String timeZone) {
    
    user.setTimeZone(timeZone);
    return this;
  }
  
  @JsonIgnore
  public BigMoney getBalance() {

    return user.getBalance();
  }

  @JsonIgnore
  public String getNativeCurrency() {

    return user.getNativeCurrency();
  }

  public CoinbaseUser updateNativeCurrency(final String nativeCurrency) {
    
    user.setNativeCurrency(nativeCurrency);
    return this;
  }

  @JsonIgnore
  public int getBuyLevel() {

    return user.getBuyLevel();
  }

  @JsonIgnore
  public int getSellLevel() {

    return user.getSellLevel();
  }

  @JsonIgnore
  public BigMoney getBuyLimit() {

    return user.getBuyLimit();
  }

  @JsonIgnore
  public BigMoney getSellLimit() {

    return user.getSellLimit();
  }

  @JsonIgnore
  public String getPin() {

    return user.getPin();
  }

  public CoinbaseUser updatePin(final String pin) {
    
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

  public CoinbaseUser withoAuthClientId(final String oAuthClientId) {

    this.oAuthClientId = oAuthClientId;
    return this;
  }

  @Override
  public String toString() {

    return "CoinbaseUser [user=" + user + ", oAuth=" + oAuth + ", oAuthClientId=" + oAuthClientId + "]";
  }

  public static class CoinbaseUserInfo {

    private final String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private final String password;
    private final String receiveAddress;
    @JsonProperty("referrer_id")
    private final String referrerId;
    @JsonProperty("time_zone")
    private String timeZone;
    private final BigMoney balance;
    @JsonProperty("native_currency")
    private String nativeCurrency;
    private final Integer buyLevel;
    private final Integer sellLevel;
    private final BigMoney buyLimit;
    private final BigMoney sellLimit;
    @JsonProperty("pin")
    private String pin;
    private final CoinbaseMerchant merchant;

    private CoinbaseUserInfo(@JsonProperty("id") final String id, @JsonProperty("email") final String email, @JsonProperty("name") final String name, @JsonProperty("password") final String password,
        @JsonProperty("receive_address") final String receiveAddress, @JsonProperty("referrer_id") final String referrerId, @JsonProperty("time_zone") final String timeZone,
        @JsonProperty("balance") @JsonDeserialize(using = CoinbaseMoneyDeserializer.class) final BigMoney balance, @JsonProperty("native_currency") final String nativeCurrency,
        @JsonProperty("buy_level") final Integer buyLevel, @JsonProperty("sell_level") final Integer sellLevel,
        @JsonProperty("buy_limit") @JsonDeserialize(using = CoinbaseMoneyDeserializer.class) final BigMoney buyLimit, @JsonProperty("sell_limit") @JsonDeserialize(
            using = CoinbaseMoneyDeserializer.class) final BigMoney sellLimit, @JsonProperty("pin") final String pin, @JsonProperty("marchant") final CoinbaseMerchant merchant) {

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

    private CoinbaseUserInfo(final String email, final String password, final String referrerId) {

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

    private void setEmail(final String email) {
      
      this.email = email;
    }
    
    public String getName() {

      return name;
    }

    private void setName(final String name) {
      
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

    private void setTimeZone(final String timeZone) {
      
      this.timeZone = timeZone;
    }
    
    @JsonIgnore
    public BigMoney getBalance() {

      return balance;
    }

    public String getNativeCurrency() {

      return nativeCurrency;
    }

    private void setNativeCurrency(final String nativeCurrency) {
      
      this.nativeCurrency = nativeCurrency;
    }
    
    @JsonIgnore
    public Integer getBuyLevel() {

      return buyLevel;
    }

    @JsonIgnore
    public Integer getSellLevel() {

      return sellLevel;
    }

    @JsonIgnore
    public BigMoney getBuyLimit() {

      return buyLimit;
    }

    @JsonIgnore
    public BigMoney getSellLimit() {

      return sellLimit;
    }

    public String getPin() {

      return pin;
    }

    private void setPin(final String pin) {
      
      this.pin = pin;
    }
    
    @JsonIgnore
    public CoinbaseMerchant getMerchant() {

      return merchant;
    }

    @Override
    public String toString() {

      return "CoinbaseUserInfo [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", receiveAddress=" + receiveAddress + ", referrerId=" + referrerId + ", timeZone="
          + timeZone + ", balance=" + balance + ", nativeCurrency=" + nativeCurrency + ", buyLevel=" + buyLevel + ", sellLevel=" + sellLevel + ", buyLimit=" + buyLimit + ", sellLimit=" + sellLimit
          + ", pin=" + pin + ", merchant=" + merchant + "]";
    }
  }
}

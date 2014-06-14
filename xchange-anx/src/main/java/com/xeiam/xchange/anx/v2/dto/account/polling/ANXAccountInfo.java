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
package com.xeiam.xchange.anx.v2.dto.account.polling;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.anx.v2.dto.ANXValue;

/**
 * Data object representing Account Info from ANX
 */
public final class ANXAccountInfo {

  private final String login;
  private final String index;
  private final String id;
  private final List<String> rights;
  private final String language;
  private final String created;
  private final String lastLogin;
  private final Wallets wallets;
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
  public ANXAccountInfo(@JsonProperty("Login") String login, @JsonProperty("Index") String index, @JsonProperty("Id") String id, @JsonProperty("Rights") List<String> rights,
      @JsonProperty("Language") String language, @JsonProperty("Created") String created, @JsonProperty("Last_Login") String lastLogin, @JsonProperty("Wallets") Wallets wallets,
      @JsonProperty("Monthly_Volume") ANXValue monthlyVolume, @JsonProperty("Trade_Fee") BigDecimal tradeFee) {

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

  public Wallets getWallets() {

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

    return "ANXAccountInfo [login=" + login + ", index=" + index + ", id=" + id + ", rights=" + rights + ", language=" + language + ", created=" + created + ", lastLogin=" + lastLogin + ", wallets="
        + wallets + ", monthlyVolume=" + monthlyVolume + ", tradeFee=" + tradeFee + "]";
  }

}

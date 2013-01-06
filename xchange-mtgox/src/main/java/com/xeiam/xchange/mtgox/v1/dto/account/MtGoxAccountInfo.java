/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v1.dto.account;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.xeiam.xchange.mtgox.v1.dto.MtGoxValue;
import com.xeiam.xchange.mtgox.v1.dto.trade.Wallets;

/**
 * Data object representing Account Info from Mt Gox
 * 
 * @immutable
 */
public class MtGoxAccountInfo {

  private String login;
  private String index;
  private String id;
  private List<String> rights;
  private String language;
  private String created;
  private String last_Login;
  private Wallets wallets;
  private MtGoxValue monthly_Volume;
  private BigDecimal trade_Fee;

  /**
   * Constructor
   * 
   * @param login
   * @param index
   * @param id
   * @param rights
   * @param language
   * @param created
   * @param last_Login
   * @param wallets
   * @param monthly_Volume
   * @param trade_Fee
   */
  public MtGoxAccountInfo(@JsonProperty("Login") String login, @JsonProperty("Index") String index, @JsonProperty("Id") String id, @JsonProperty("Rights") List<String> rights,
      @JsonProperty("Language") String language, @JsonProperty("Created") String created, @JsonProperty("Last_Login") String last_Login, @JsonProperty("Wallets") Wallets wallets,
      @JsonProperty("Monthly_Volume") MtGoxValue monthly_Volume, @JsonProperty("Trade_Fee") BigDecimal trade_Fee) {

    this.login = login;
    this.index = index;
    this.id = id;
    this.rights = rights;
    this.language = language;
    this.created = created;
    this.last_Login = last_Login;
    this.wallets = wallets;
    this.monthly_Volume = monthly_Volume;
    this.trade_Fee = trade_Fee;
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

  public String getLast_Login() {

    return last_Login;
  }

  public Wallets getWallets() {

    return wallets;
  }

  public MtGoxValue getMonthly_Volume() {

    return monthly_Volume;
  }

  public BigDecimal getTrade_Fee() {

    return trade_Fee;
  }

  @Override
  public String toString() {

    return "MtGoxAccountInfo [login=" + login + ", index=" + index + ", rights=" + rights + ", language=" + language + ", created=" + created + ", last_Login=" + last_Login + ", wallets=" + wallets
        + ", trade_Fee=" + trade_Fee + "]";
  }

}

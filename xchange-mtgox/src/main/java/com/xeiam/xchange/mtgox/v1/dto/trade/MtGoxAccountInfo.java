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
package com.xeiam.xchange.mtgox.v1.dto.trade;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.xeiam.xchange.mtgox.v1.dto.MtGoxValue;

/**
 * Data object representing Account Info from Mt Gox
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
  private MtGoxValue monthly_Volume = new MtGoxValue();
  private double trade_Fee;

  @JsonProperty("Login")
  public String getLogin() {

    return login;
  }

  public void setLogin(String login) {

    this.login = login;
  }

  @JsonProperty("Index")
  public String getIndex() {

    return index;
  }

  public void setIndex(String index) {

    this.index = index;
  }

  @JsonProperty("Id")
  public String getId() {

    return id;
  }

  public void setId(String id) {

    this.id = id;
  }

  @JsonProperty("Rights")
  public List<String> getRights() {

    return rights;
  }

  public void setRights(List<String> rights) {

    this.rights = rights;
  }

  @JsonProperty("Language")
  public String getLanguage() {

    return language;
  }

  public void setLanguage(String language) {

    this.language = language;
  }

  @JsonProperty("Created")
  public String getCreated() {

    return created;
  }

  public void setCreated(String created) {

    this.created = created;
  }

  @JsonProperty("Last_Login")
  public String getLast_Login() {

    return last_Login;
  }

  public void setLast_Login(String last_Login) {

    this.last_Login = last_Login;
  }

  @JsonProperty("Wallets")
  public Wallets getWallets() {

    return wallets;
  }

  public void setWallets(Wallets wallets) {

    this.wallets = wallets;
  }

  @JsonProperty("Monthly_Volume")
  public MtGoxValue getMonthly_Volume() {

    return monthly_Volume;
  }

  public void setMonthly_Volume(MtGoxValue monthly_Volume) {

    this.monthly_Volume = monthly_Volume;
  }

  @JsonProperty("Trade_Fee")
  public double getTrade_Fee() {

    return trade_Fee;
  }

  public void setTrade_Fee(double trade_Fee) {

    this.trade_Fee = trade_Fee;
  }

  @Override
  public String toString() {

    return "MtGoxAccountInfo [login=" + login + ", index=" + index + ", rights=" + rights + ", language=" + language + ", created=" + created + ", last_Login=" + last_Login + ", wallets=" + wallets + ", trade_Fee="
        + trade_Fee + "]";
  }

}

package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class AskParams {

  private String tokenBuy = "";
  private String buySymbol = "";
  private java.math.BigInteger buyPrecision;
  private String amountBuy = "";
  private String tokenSell = "";
  private String sellSymbol = "";
  private java.math.BigInteger sellPrecision;
  private String amountSell = "";
  private String expires = "0";
  private java.math.BigInteger nonce;
  private String user = "";

  /** */
  public AskParams tokenBuy(String tokenBuy) {
    this.tokenBuy = tokenBuy;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("tokenBuy")
  public String getTokenBuy() {
    return tokenBuy;
  }

  public void setTokenBuy(String tokenBuy) {
    this.tokenBuy = tokenBuy;
  }

  /** */
  public AskParams buySymbol(String buySymbol) {
    this.buySymbol = buySymbol;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("buySymbol")
  public String getBuySymbol() {
    return buySymbol;
  }

  public void setBuySymbol(String buySymbol) {
    this.buySymbol = buySymbol;
  }

  /** */
  public AskParams buyPrecision(java.math.BigInteger buyPrecision) {
    this.buyPrecision = buyPrecision;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("buyPrecision")
  public java.math.BigInteger getBuyPrecision() {
    return buyPrecision;
  }

  public void setBuyPrecision(java.math.BigInteger buyPrecision) {
    this.buyPrecision = buyPrecision;
  }

  /** */
  public AskParams amountBuy(String amountBuy) {
    this.amountBuy = amountBuy;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("amountBuy")
  public String getAmountBuy() {
    return amountBuy;
  }

  public void setAmountBuy(String amountBuy) {
    this.amountBuy = amountBuy;
  }

  /** */
  public AskParams tokenSell(String tokenSell) {
    this.tokenSell = tokenSell;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("tokenSell")
  public String getTokenSell() {
    return tokenSell;
  }

  public void setTokenSell(String tokenSell) {
    this.tokenSell = tokenSell;
  }

  /** */
  public AskParams sellSymbol(String sellSymbol) {
    this.sellSymbol = sellSymbol;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("sellSymbol")
  public String getSellSymbol() {
    return sellSymbol;
  }

  public void setSellSymbol(String sellSymbol) {
    this.sellSymbol = sellSymbol;
  }

  /** */
  public AskParams sellPrecision(java.math.BigInteger sellPrecision) {
    this.sellPrecision = sellPrecision;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("sellPrecision")
  public java.math.BigInteger getSellPrecision() {
    return sellPrecision;
  }

  public void setSellPrecision(java.math.BigInteger sellPrecision) {
    this.sellPrecision = sellPrecision;
  }

  /** */
  public AskParams amountSell(String amountSell) {
    this.amountSell = amountSell;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("amountSell")
  public String getAmountSell() {
    return amountSell;
  }

  public void setAmountSell(String amountSell) {
    this.amountSell = amountSell;
  }

  /** */
  public AskParams expires(String expires) {
    this.expires = expires;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("expires")
  public String getExpires() {
    return expires;
  }

  public void setExpires(String expires) {
    this.expires = expires;
  }

  /** */
  public AskParams nonce(java.math.BigInteger nonce) {
    this.nonce = nonce;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("nonce")
  public java.math.BigInteger getNonce() {
    return nonce;
  }

  public void setNonce(java.math.BigInteger nonce) {
    this.nonce = nonce;
  }

  /** */
  public AskParams user(String user) {
    this.user = user;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("user")
  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AskParams askParams = (AskParams) o;
    return Objects.equals(tokenBuy, askParams.tokenBuy)
        && Objects.equals(buySymbol, askParams.buySymbol)
        && Objects.equals(buyPrecision, askParams.buyPrecision)
        && Objects.equals(amountBuy, askParams.amountBuy)
        && Objects.equals(tokenSell, askParams.tokenSell)
        && Objects.equals(sellSymbol, askParams.sellSymbol)
        && Objects.equals(sellPrecision, askParams.sellPrecision)
        && Objects.equals(amountSell, askParams.amountSell)
        && Objects.equals(expires, askParams.expires)
        && Objects.equals(nonce, askParams.nonce)
        && Objects.equals(user, askParams.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        tokenBuy,
        buySymbol,
        buyPrecision,
        amountBuy,
        tokenSell,
        sellSymbol,
        sellPrecision,
        amountSell,
        expires,
        nonce,
        user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AskParams {\n");

    sb.append("    tokenBuy: ").append(toIndentedString(tokenBuy)).append("\n");
    sb.append("    buySymbol: ").append(toIndentedString(buySymbol)).append("\n");
    sb.append("    buyPrecision: ").append(toIndentedString(buyPrecision)).append("\n");
    sb.append("    amountBuy: ").append(toIndentedString(amountBuy)).append("\n");
    sb.append("    tokenSell: ").append(toIndentedString(tokenSell)).append("\n");
    sb.append("    sellSymbol: ").append(toIndentedString(sellSymbol)).append("\n");
    sb.append("    sellPrecision: ").append(toIndentedString(sellPrecision)).append("\n");
    sb.append("    amountSell: ").append(toIndentedString(amountSell)).append("\n");
    sb.append("    expires: ").append(toIndentedString(expires)).append("\n");
    sb.append("    nonce: ").append(toIndentedString(nonce)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

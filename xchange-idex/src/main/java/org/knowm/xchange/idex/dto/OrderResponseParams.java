package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class OrderResponseParams {

  private String tokenBuy;
  private java.math.BigInteger buyPrecision;
  private String amountBuy;
  private String tokenSell;
  private java.math.BigInteger sellPrecision;
  private String amountSell;
  private String expires;
  private java.math.BigInteger nonce;
  private String user;

  /** */
  public OrderResponseParams tokenBuy(String tokenBuy) {
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
  public OrderResponseParams buyPrecision(java.math.BigInteger buyPrecision) {
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
  public OrderResponseParams amountBuy(String amountBuy) {
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
  public OrderResponseParams tokenSell(String tokenSell) {
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
  public OrderResponseParams sellPrecision(java.math.BigInteger sellPrecision) {
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
  public OrderResponseParams amountSell(String amountSell) {
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
  public OrderResponseParams expires(String expires) {
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
  public OrderResponseParams nonce(java.math.BigInteger nonce) {
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
  public OrderResponseParams user(String user) {
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
    OrderResponseParams orderResponseParams = (OrderResponseParams) o;
    return Objects.equals(tokenBuy, orderResponseParams.tokenBuy)
        && Objects.equals(buyPrecision, orderResponseParams.buyPrecision)
        && Objects.equals(amountBuy, orderResponseParams.amountBuy)
        && Objects.equals(tokenSell, orderResponseParams.tokenSell)
        && Objects.equals(sellPrecision, orderResponseParams.sellPrecision)
        && Objects.equals(amountSell, orderResponseParams.amountSell)
        && Objects.equals(expires, orderResponseParams.expires)
        && Objects.equals(nonce, orderResponseParams.nonce)
        && Objects.equals(user, orderResponseParams.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        tokenBuy,
        buyPrecision,
        amountBuy,
        tokenSell,
        sellPrecision,
        amountSell,
        expires,
        nonce,
        user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderResponseParams {\n");

    sb.append("    tokenBuy: ").append(toIndentedString(tokenBuy)).append("\n");
    sb.append("    buyPrecision: ").append(toIndentedString(buyPrecision)).append("\n");
    sb.append("    amountBuy: ").append(toIndentedString(amountBuy)).append("\n");
    sb.append("    tokenSell: ").append(toIndentedString(tokenSell)).append("\n");
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

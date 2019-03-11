package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class TradeHistoryItem {

  private String date = "";
  private String amount = "";
  private IdexBuySell type;
  private String total = "";
  private String price = "";
  private String orderHash = "";
  private String uuid = "";
  private String buyerFee = "";
  private String sellerFee = "";
  private String gasFee = "";
  private java.math.BigInteger timestamp;
  private String maker = "";
  private String taker = "";
  private String transactionHash = "";

  /** */
  public TradeHistoryItem date(String date) {
    this.date = date;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("date")
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  /** */
  public TradeHistoryItem amount(String amount) {
    this.amount = amount;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  /** */
  public TradeHistoryItem type(IdexBuySell type) {
    this.type = type;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("type")
  public IdexBuySell getType() {
    return type;
  }

  public void setType(IdexBuySell type) {
    this.type = type;
  }

  /** */
  public TradeHistoryItem total(String total) {
    this.total = total;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("total")
  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  /** */
  public TradeHistoryItem price(String price) {
    this.price = price;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("price")
  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  /** */
  public TradeHistoryItem orderHash(String orderHash) {
    this.orderHash = orderHash;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("orderHash")
  public String getOrderHash() {
    return orderHash;
  }

  public void setOrderHash(String orderHash) {
    this.orderHash = orderHash;
  }

  /** */
  public TradeHistoryItem uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("uuid")
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /** */
  public TradeHistoryItem buyerFee(String buyerFee) {
    this.buyerFee = buyerFee;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("buyerFee")
  public String getBuyerFee() {
    return buyerFee;
  }

  public void setBuyerFee(String buyerFee) {
    this.buyerFee = buyerFee;
  }

  /** */
  public TradeHistoryItem sellerFee(String sellerFee) {
    this.sellerFee = sellerFee;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("sellerFee")
  public String getSellerFee() {
    return sellerFee;
  }

  public void setSellerFee(String sellerFee) {
    this.sellerFee = sellerFee;
  }

  /** */
  public TradeHistoryItem gasFee(String gasFee) {
    this.gasFee = gasFee;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("gasFee")
  public String getGasFee() {
    return gasFee;
  }

  public void setGasFee(String gasFee) {
    this.gasFee = gasFee;
  }

  /** */
  public TradeHistoryItem timestamp(java.math.BigInteger timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("timestamp")
  public java.math.BigInteger getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(java.math.BigInteger timestamp) {
    this.timestamp = timestamp;
  }

  /** */
  public TradeHistoryItem maker(String maker) {
    this.maker = maker;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("maker")
  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  /** */
  public TradeHistoryItem taker(String taker) {
    this.taker = taker;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("taker")
  public String getTaker() {
    return taker;
  }

  public void setTaker(String taker) {
    this.taker = taker;
  }

  /** */
  public TradeHistoryItem transactionHash(String transactionHash) {
    this.transactionHash = transactionHash;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("transactionHash")
  public String getTransactionHash() {
    return transactionHash;
  }

  public void setTransactionHash(String transactionHash) {
    this.transactionHash = transactionHash;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TradeHistoryItem tradeHistoryItem = (TradeHistoryItem) o;
    return Objects.equals(date, tradeHistoryItem.date)
        && Objects.equals(amount, tradeHistoryItem.amount)
        && Objects.equals(type, tradeHistoryItem.type)
        && Objects.equals(total, tradeHistoryItem.total)
        && Objects.equals(price, tradeHistoryItem.price)
        && Objects.equals(orderHash, tradeHistoryItem.orderHash)
        && Objects.equals(uuid, tradeHistoryItem.uuid)
        && Objects.equals(buyerFee, tradeHistoryItem.buyerFee)
        && Objects.equals(sellerFee, tradeHistoryItem.sellerFee)
        && Objects.equals(gasFee, tradeHistoryItem.gasFee)
        && Objects.equals(timestamp, tradeHistoryItem.timestamp)
        && Objects.equals(maker, tradeHistoryItem.maker)
        && Objects.equals(taker, tradeHistoryItem.taker)
        && Objects.equals(transactionHash, tradeHistoryItem.transactionHash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        date,
        amount,
        type,
        total,
        price,
        orderHash,
        uuid,
        buyerFee,
        sellerFee,
        gasFee,
        timestamp,
        maker,
        taker,
        transactionHash);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TradeHistoryItem {\n");

    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    orderHash: ").append(toIndentedString(orderHash)).append("\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    buyerFee: ").append(toIndentedString(buyerFee)).append("\n");
    sb.append("    sellerFee: ").append(toIndentedString(sellerFee)).append("\n");
    sb.append("    gasFee: ").append(toIndentedString(gasFee)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    maker: ").append(toIndentedString(maker)).append("\n");
    sb.append("    taker: ").append(toIndentedString(taker)).append("\n");
    sb.append("    transactionHash: ").append(toIndentedString(transactionHash)).append("\n");
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

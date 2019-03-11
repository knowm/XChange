package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class TradeHistoryReq {

  private String market;
  private String address;
  private String start;
  private String end;

  /**
   * (string) - If specified, will return an array of trade objects for the market, if omitted, will
   * return an object of arrays of trade objects keyed by each market
   */
  public TradeHistoryReq market(String market) {
    this.market = market;
    return this;
  }

  @ApiModelProperty(
      "(string) - If specified, will return an array of trade objects for the market, if omitted, will return an object of arrays of trade objects keyed by each market")
  @JsonProperty("market")
  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  /**
   * (address string) - If specified, return value will only include trades that involve the address
   * as the maker or taker. Note: if specified the type property of the trade objects will refer to
   * the action on the market taken relative to the user, not relative to the market. This behavior
   * is designed to mimic the My Trades section of the IDEX appication, also to mimic the behavior
   * of the private returnTradeHistory API call on Poloniex
   */
  public TradeHistoryReq address(String address) {
    this.address = address;
    return this;
  }

  @ApiModelProperty(
      "(address string) - If specified, return value will only include trades that involve the address as the maker or taker. Note: if specified the type property of the trade objects will refer to the action on the market taken relative to the user, not relative to the market. This behavior is designed to mimic the My Trades section of the IDEX appication, also to mimic the behavior of the private returnTradeHistory API call on Poloniex")
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * (number) - The inclusive UNIX timestamp (seconds since epoch, not ms) marking the earliest
   * trade that will be returned in the response, if omitted will default to 0
   */
  public TradeHistoryReq start(String start) {
    this.start = start;
    return this;
  }

  @ApiModelProperty(
      "(number) - The inclusive UNIX timestamp (seconds since epoch, not ms) marking the earliest trade that will be returned in the response, if omitted will default to 0")
  @JsonProperty("start")
  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  /**
   * (number) - The inclusive UNIX timestamp marking the latest trade that will be returned in the
   * response. If omitted will default to the current timestamp
   */
  public TradeHistoryReq end(String end) {
    this.end = end;
    return this;
  }

  @ApiModelProperty(
      "(number) - The inclusive UNIX timestamp marking the latest trade that will be returned in the response. If omitted will default to the current timestamp")
  @JsonProperty("end")
  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TradeHistoryReq tradeHistoryReq = (TradeHistoryReq) o;
    return Objects.equals(market, tradeHistoryReq.market)
        && Objects.equals(address, tradeHistoryReq.address)
        && Objects.equals(start, tradeHistoryReq.start)
        && Objects.equals(end, tradeHistoryReq.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(market, address, start, end);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TradeHistoryReq {\n");

    sb.append("    market: ").append(toIndentedString(market)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
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

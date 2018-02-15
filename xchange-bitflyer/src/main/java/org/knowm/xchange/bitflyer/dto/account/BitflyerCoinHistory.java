package org.knowm.xchange.bitflyer.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Object representing json returned from <code>GET /v1/me/getcoinins</code> or <code>GET /v1/me/getcoinouts</code>
 *  
 *  <p>Example getcoinins</p>
 *  <code><pre>{
 *    "id": 100,
 *    "order_id": "CDP20151227-024141-055555",
 *    "currency_code": "BTC",
 *    "amount": 0.00002,
 *    "address": "1WriteySQufKZ2pVuM1oMhPrTtTVFq35j",
 *    "tx_hash": "9f92ee65a176bb9545f7becb8706c50d07d4cee5ffca34d8be3ef11d411405ae",
 *    "status": "COMPLETED",
 *    "event_date": "2015-11-27T08:59:20.301"
 *  }</pre></code>
 *  
 *  <p>Example getcoinouts</p>
 *  <code><pre>{
 *     "id": 500,
 *     "order_id": "CWD20151224-014040-077777",
 *     "currency_code": "BTC",
 *     "amount": 0.1234,
 *     "address": "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa",
 *     "tx_hash": "724c07dfd4044abcb390b0412c3e707dd5c4f373f0a52b3bd295ce32b478c60a",
 *     "fee": 0.0005,
 *     "additional_fee": 0.0001,
 *     "status": "COMPLETED",
 *     "event_date": "2015-12-24T01:40:40.397"
 *   }</pre></code>

 * @author bryant_harris
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerCoinHistory extends BitflyerBaseHistoryResponse {  
  @JsonProperty("address")
  String address;
        
  @JsonProperty("fee")
  BigDecimal fee;
        
  @JsonProperty("additional_fee")
  BigDecimal additionalFee;
        
  @JsonProperty("tx_hash")
  String txHash;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
 }
        
  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public BigDecimal getAdditionalFee() {
    return additionalFee;
  }

  public void setAdditionalFee(BigDecimal additionalFee) {
    this.additionalFee = additionalFee;
  }

  public String getTxHash() {
    return txHash;
  }

  public void setTxHash(String txHash) {
    this.txHash = txHash;
  }

  @Override
  public String toString() {
    return "BitflyerCoinIn [id=" + id + ", orderID=" + orderID + ", currencyCode=" + currencyCode + ", amount="
        + amount + ", address=" + address + ", fee=" + fee + ", additionalFee=" + additionalFee + ", txHash="
        + txHash + ", status=" + status + ", eventDate=" + eventDate + "]";
  }
}

package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Object representing json returned from <code>GET /v1/me/getaddresses</code>
 *  
 * <p>Example</p>
 * [
 *   {
 *     "type": "NORMAL",
 *     "currency_code": "BTC",
 *     "address": "3AYrDq8zhF82NJ2ZaLwBMPmaNziaKPaxa7"
 *   },
 *   {
 *     "type": "NORMAL",
 *     "currency_code": "ETH",
 *     "address": "0x7fbB2CC24a3C0cd3789a44e9073381Ca6470853f"
 *   }
 * ]
 * @author bryant_harris
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "type",
                       "currency_code",
                       "address"
                   })
public class BitflyerAddress {
  @JsonProperty("type")
  private String type;
  @JsonProperty("currency_code")
  private String currencyCode;
  @JsonProperty("address")
  private String address;
    
  public String getType() {
    return type;
  }
    
  public void setType(String type) {
    this.type = type;
  }
    
  public String getCurrencyCode() {
    return currencyCode;
  }
    
  public void setCurrencyCode(String currency_code) {
    this.currencyCode = currency_code;
  }
    
  public String getAddress() {
    return address;
  }
    
  public void setAddress(String address) {
    this.address = address;
  }
    
  @Override
  public String toString() {
    return "BitflyerAddress [type=" + type + ", currency_code=" + currencyCode + ", address=" + address + "]";
  }
}

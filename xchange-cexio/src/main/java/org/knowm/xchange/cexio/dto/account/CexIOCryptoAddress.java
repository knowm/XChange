package org.knowm.xchange.cexio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response to get_address call. (From Cex.io documentation) Returns JSON dictionary representing
 * order:
 *
 * <ul>
 *   <li>e - get_address
 *   <li>ok - ok or error
 *   <li>data - Crypto address
 * </ul>
 *
 * @author bryant_harris
 */
public class CexIOCryptoAddress {
  String e;
  String ok;
  String data;
  String error;

  public CexIOCryptoAddress(
      @JsonProperty("e") String e,
      @JsonProperty("ok") String ok,
      @JsonProperty("currency") String data,
      @JsonProperty("error") String error) {

    this.e = e;
    this.ok = ok;
    this.data = data;
    this.error = error;
  }

  public String getE() {
    return e;
  }

  public String getOk() {
    return ok;
  }

  public String getData() {
    return data;
  }

  public String getError() {
    return error;
  }
}

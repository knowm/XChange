package org.knowm.xchange.bitfinex.v1.dto.account;

public class BitfinexTradingFeesRequest extends BitfinexEmptyRequest {

  /**
   * Constructor
   *
   * @param nonce
   */
  public BitfinexTradingFeesRequest(String nonce) {
    super(nonce, "/v1/account_infos");
  }
}

package org.knowm.xchange.btcmarkets.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see https://github.com/BTCMarkets/API/wiki/Fund-Transfer-API
 *     {"amount":10000000,"address":"1EJKj147QmEzywLnLpuxSr6SoPr1p62VBX","currency":"BTC"}
 */
public class BTCMarketsWithdrawCryptoRequest {
  @JsonProperty public final long amount; // 0.1 = 10000000

  @JsonProperty public final String address;

  @JsonProperty public final String currency;

  public BTCMarketsWithdrawCryptoRequest(long amount, String address, String currency) {
    this.amount = amount;
    this.address = address;
    this.currency = currency;
  }
}

package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitfinexBalanceHistoryRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("currency")
  protected String currency;

  @JsonProperty("since")
  protected Date since;

  @JsonProperty("until")
  protected Date until;

  @JsonProperty("limit")
  protected int limit;

  @JsonProperty("wallet")
  protected String wallet;

  /**
   * Constructor
   *
   * @param nonce
   * @param currency
   * @param wallet
   * @param since
   * @param until
   * @param limit
   */
  public BitfinexBalanceHistoryRequest(
      String nonce, String currency, Long since, Long until, int limit, String wallet) {

    this.request = "/v1/history";
    this.nonce = nonce;
    this.currency = currency;
    this.wallet = wallet;
    this.since = since == null ? null : new Date(since);
    this.until = until == null ? null : new Date(until);
    this.limit = limit;
  }
}

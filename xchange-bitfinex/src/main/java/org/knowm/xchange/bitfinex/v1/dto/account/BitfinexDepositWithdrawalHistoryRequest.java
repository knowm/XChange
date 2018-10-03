package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/** http://docs.bitfinex.com/#deposit-withdrawal-history */
public class BitfinexDepositWithdrawalHistoryRequest {
  @JsonProperty("request")
  private final String request;

  @JsonProperty("nonce")
  private final String nonce;

  /** The currency to look for. */
  @JsonProperty("currency")
  private final String currency;

  /**
   * Optional. The method of the deposit/withdrawal (can be “bitcoin”, “litecoin”, “darkcoin”,
   * “wire”).
   */
  @JsonProperty("method")
  @JsonInclude(Include.NON_NULL)
  private final String method;

  /** Optional. Return only the history after this timestamp. */
  @JsonProperty("since")
  @JsonInclude(Include.NON_NULL)
  private final String since;

  /** Optional. Return only the history before this timestamp. */
  @JsonProperty("until")
  @JsonInclude(Include.NON_NULL)
  private final String until;

  /** Optional. Limit the number of entries to return. Default is 500. */
  @JsonProperty("limit")
  @JsonInclude(Include.NON_NULL)
  private final Integer limit;

  public BitfinexDepositWithdrawalHistoryRequest(
      String nonce, String currency, String method, Date since, Date until, Integer limit) {
    this.request = "/v1/history/movements";
    this.nonce = String.valueOf(nonce);
    this.currency = currency;
    this.method = method;
    this.since = since == null ? null : String.valueOf(since.getTime() / 1000);
    this.until = until == null ? null : String.valueOf(until.getTime() / 1000);
    this.limit = limit;
  }
}

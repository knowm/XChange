package org.knowm.xchange.krakenfutures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Panchen */
@Getter
public class KrakenFuturesAccounts extends KrakenFuturesResult {

  private final Map<String, KrakenFuturesAccountInfo> accounts;
  private final Date serverTime;
  public KrakenFuturesAccounts(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("error") String error,
      @JsonProperty("accounts") Map<String, KrakenFuturesAccountInfo> accounts) {

    super(result, error);
    this.serverTime = serverTime;
    this.accounts = accounts;
  }
}

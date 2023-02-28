package org.knowm.xchange.krakenfutures.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

import lombok.Getter;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

/** @author Panchen */
@Getter
public class KrakenFuturesAccount extends KrakenFuturesResult {

  private final KrakenFuturesAccountInfo accountInfo;
  private final Date serverTime;

  public KrakenFuturesAccount(
      @JsonProperty("result") String result,
      @JsonProperty("serverTime") Date serverTime,
      @JsonProperty("error") String error,
      @JsonProperty("account") KrakenFuturesAccountInfo accountInfo) {

    super(result, error);
    this.serverTime =serverTime;
    this.accountInfo = accountInfo;
  }
}

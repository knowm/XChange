package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OkCoinFuturesUserInfoFixed extends OkCoinErrorResult {
  private final Map<Currency, OkcoinFuturesFundsFixed> info;

  public OkCoinFuturesUserInfoFixed(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("info") Map<Currency, OkcoinFuturesFundsFixed> info) {

    super(result, errorCode);
    this.info = info;
  }

  public Map<Currency, OkcoinFuturesFundsFixed> getInfo() {
    return info;
  }

  public OkcoinFuturesFundsFixed getFunds(Currency currency) {
    return info.get(currency);
  }
}

package org.knowm.xchange.okcoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OkCoinFuturesUserInfoCross extends OkCoinErrorResult {

  private final Map<Currency, OkcoinFuturesFundsCross> info;

  public OkCoinFuturesUserInfoCross(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("info") Map<Currency, OkcoinFuturesFundsCross> info) {

    super(result, errorCode);
    this.info = info;
  }

  public Map<Currency, OkcoinFuturesFundsCross> getInfo() {
    return info;
  }

  public OkcoinFuturesFundsCross getFunds(Currency currency) {
    return info.get(currency);
  }
}

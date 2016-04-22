package org.knowm.xchange.anx.v2.dto.meta;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;

public class ANXMetaData extends ExchangeMetaData {
  public Map<CurrencyPair, ANXMarketMetaData> currencyPair;
  public BigDecimal makerTradingFee;
  public BigDecimal takerTradingFee;

  public ANXMetaData(@JsonProperty("currencyPair") Map<CurrencyPair, ANXMarketMetaData> currencyPairs,
      @JsonProperty("currency") Map<Currency, CurrencyMetaData> currency, @JsonProperty("publicRateLimits") Set<RateLimit> publicRateLimits,
      @JsonProperty("privateRateLimits") Set<RateLimit> privateRateLimits, @JsonProperty("shareRateLimits") Boolean shareRateLimits,
      @JsonProperty("makerTradingFee") BigDecimal makerTradingFee, @JsonProperty("takerTradingFee") BigDecimal takerTradingFee) {
    super((Map) currencyPairs, currency, publicRateLimits, privateRateLimits, shareRateLimits);

    this.currencyPair = currencyPairs;
    this.makerTradingFee = makerTradingFee;
    this.takerTradingFee = takerTradingFee;
  }
}

package org.knowm.xchange.dsx.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Mikhail Wall
 */

public class DSXMetaData extends ExchangeMetaData {

  @JsonProperty
  public int publicInfoCacheSeconds;

  @JsonProperty
  public int amountScale;

  public DSXMetaData(@JsonProperty("currency_pairs") Map<CurrencyPair, CurrencyPairMetaData> currencyPairs,
                     @JsonProperty("currencies") Map<Currency, CurrencyMetaData> currencies, @JsonProperty("public_rate_limits") RateLimit[] publicRateLimits,
                     @JsonProperty("private_rate_limits") RateLimit[] privateRateLimits, @JsonProperty("share_rate_limits") Boolean shareRateLimits) {
    super(currencyPairs, currencies, publicRateLimits, privateRateLimits, shareRateLimits);
  }
}

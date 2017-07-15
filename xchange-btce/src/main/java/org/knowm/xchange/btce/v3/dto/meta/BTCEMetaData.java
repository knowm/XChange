package org.knowm.xchange.btce.v3.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

import java.util.Map;

public class BTCEMetaData extends ExchangeMetaData {

  /**
   * The number of seconds the public data is cached for.
   */
  public int publicInfoCacheSeconds;

  public int amountScale;

  public BTCEMetaData(@JsonProperty("currency_pairs") Map<CurrencyPair, CurrencyPairMetaData> currencyPairs,
                      @JsonProperty("currencies") Map<Currency, CurrencyMetaData> currency,
                      @JsonProperty("publicInfoCacheSeconds") int publicInfoCacheSeconds, @JsonProperty("amountScale") int amountScale) {
    super(currencyPairs, currency, null, null, null);
    this.publicInfoCacheSeconds = publicInfoCacheSeconds;
    this.amountScale = amountScale;
  }
}

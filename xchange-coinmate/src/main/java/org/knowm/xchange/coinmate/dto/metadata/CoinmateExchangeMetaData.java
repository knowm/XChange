package org.knowm.xchange.coinmate.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinmate.CoinmateAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.instrument.Instrument;

import java.util.Map;

public class CoinmateExchangeMetaData extends ExchangeMetaData {

  public CoinmateExchangeMetaData(
      @JsonProperty("currency_pairs") Map<Instrument, CoinmateInstrumentMetaData> instruments,
      @JsonProperty("currencies") Map<Currency, CurrencyMetaData> currency,
      @JsonProperty("public_rate_limits") RateLimit[] publicRateLimits,
      @JsonProperty("private_rate_limits") RateLimit[] privateRateLimits,
      @JsonProperty("share_rate_limits") Boolean shareRateLimits) {

    super(CoinmateAdapters.adaptInstrumentMetadataMap(instruments), currency, publicRateLimits, privateRateLimits, shareRateLimits);
  }
}

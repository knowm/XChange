package org.knowm.xchange.dsx.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.instrument.Instrument;

/** @author Mikhail Wall */
public class DSXMetaData extends ExchangeMetaData {

  @JsonProperty public int publicInfoCacheSeconds;

  @JsonProperty public int amountScale;

  public DSXMetaData(
      @JsonProperty("instruments") Map<Instrument, InstrumentMetaData> instruments,
      @JsonProperty("currencies") Map<Currency, CurrencyMetaData> currencies,
      @JsonProperty("public_rate_limits") RateLimit[] publicRateLimits,
      @JsonProperty("private_rate_limits") RateLimit[] privateRateLimits,
      @JsonProperty("share_rate_limits") Boolean shareRateLimits) {
    super(instruments, currencies, publicRateLimits, privateRateLimits, shareRateLimits);
  }
}

package org.knowm.xchange.bybit;

import java.util.Map;
import lombok.Value;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.meta.RateLimit;
import org.knowm.xchange.instrument.Instrument;

@Value
public class BybitExchangeMetadata extends ExchangeMetaData {

  Map<String, CurrencyPair> currencyPairBySymbol;

  public BybitExchangeMetadata(Map<Instrument, InstrumentMetaData> instruments,
      Map<Currency, CurrencyMetaData> currency, RateLimit[] publicRateLimits,
      RateLimit[] privateRateLimits, Boolean shareRateLimits, Map<String, CurrencyPair> currencyPairBySymbol) {
    super(instruments, currency, publicRateLimits, privateRateLimits, shareRateLimits);
    this.currencyPairBySymbol = currencyPairBySymbol;
  }
}

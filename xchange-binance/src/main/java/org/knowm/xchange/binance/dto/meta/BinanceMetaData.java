package org.knowm.xchange.binance.dto.meta;

import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.RateLimit;

import com.fasterxml.jackson.annotation.JsonProperty;


public class BinanceMetaData extends ExchangeMetaData {

    public BinanceMetaData(@JsonProperty("currency_pairs") Map<CurrencyPair, CurrencyPairMetaData> currencyPairs,
            @JsonProperty("currencies") Map<Currency, CurrencyMetaData> currencies,
            @JsonProperty("public_rate_limits") RateLimit[] publicRateLimits,
            @JsonProperty("private_rate_limits") RateLimit[] privateRateLimits,
            @JsonProperty("share_rate_limits") Boolean shareRateLimits) {
        super(currencyPairs, currencies, publicRateLimits, privateRateLimits, shareRateLimits);
    }
}

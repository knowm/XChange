package com.xeiam.xchange.utils.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.xeiam.xchange.currency.CurrencyPair;

public class CurrencyPairDeserializer extends JsonDeserializer<CurrencyPair> {

  @Override
  public CurrencyPair deserialize(JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jsonParser.getCodec();
    final JsonNode node = oc.readTree(jsonParser);
    final String currencyPairString = node.asText();

    return getCurrencyPairFromString(currencyPairString);
  }

  public static CurrencyPair getCurrencyPairFromString(String currencyPairString) {

    if (currencyPairString == null || currencyPairString.isEmpty()) {
      return null;
    }

    /*
     * Preserve case if exchange is sending mixed-case, otherwise toUpperCase()
     */
    final boolean isMixedCase = currencyPairString.matches(".*[a-z]+.*") && currencyPairString.matches(".*[A-Z]+.*");
    if (!isMixedCase) {
      currencyPairString = currencyPairString.toUpperCase();
    }

    /*
     * Assume all symbols are alphanumeric; anything else is a separator
     */
    final String symbols[] = currencyPairString.split("[^a-zA-Z0-9]");
    if (symbols.length == 2) {
      return new CurrencyPair(symbols[0], symbols[1]);
    }

    /*
     * Last-ditch effort to obtain the correct CurrencyPair (eg: "BTCUSD") XXX: What about a "DOGEBTC" or "BCBTC" string??
     */
    final String tradeCurrency = currencyPairString.substring(0, 3);
    final String priceCurrency = currencyPairString.substring(currencyPairString.length() - 3);

    return new CurrencyPair(tradeCurrency, priceCurrency);
  }
}

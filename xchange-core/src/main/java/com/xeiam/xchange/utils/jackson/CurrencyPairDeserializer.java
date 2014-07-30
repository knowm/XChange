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
  public CurrencyPair deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jsonParser.getCodec();
    final JsonNode node = oc.readTree(jsonParser);
    final String currencyPairString = node.asText();

    return getCurrencyPairFromString(currencyPairString);
  }

  public static CurrencyPair getCurrencyPairFromString(String currencyPairString) {

    if (currencyPairString == null || currencyPairString.isEmpty())
      return null;

    currencyPairString = currencyPairString.toUpperCase();
    final String tradeCurrency = currencyPairString.substring(0, 3);
    final String priceCurrency = currencyPairString.substring(currencyPairString.length() - 3);
    return new CurrencyPair(tradeCurrency, priceCurrency);
  }
}

package com.xeiam.xchange.coinbase.dto.serialization;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;

/**
 * @author jamespedwards42
 */
public class CoinbaseMoneyDeserializer extends JsonDeserializer<CoinbaseMoney> {

  @Override
  public CoinbaseMoney deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jp.getCodec();
    final JsonNode node = oc.readTree(jp);

    return getCoinbaseMoneyFromNode(node);
  }

  public static CoinbaseMoney getCoinbaseMoneyFromNode(final JsonNode node) {

    final String amount = node.path("amount").asText();
    final String currency = node.path("currency").asText();

    return new CoinbaseMoney(currency, new BigDecimal(amount));
  }

}

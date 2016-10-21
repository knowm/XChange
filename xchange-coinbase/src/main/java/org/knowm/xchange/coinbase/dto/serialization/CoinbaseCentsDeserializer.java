package org.knowm.xchange.coinbase.dto.serialization;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author jamespedwards42
 */
public class CoinbaseCentsDeserializer extends JsonDeserializer<CoinbaseMoney> {

  @Override
  public CoinbaseMoney deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jp.getCodec();
    final JsonNode node = oc.readTree(jp);

    return getCoinbaseMoneyFromCents(node);
  }

  public static CoinbaseMoney getCoinbaseMoneyFromCents(JsonNode node) {

    final String amount = node.path("cents").asText();
    final String currency = node.path("currency_iso").asText();
    final int numDecimals = (currency.equalsIgnoreCase("BTC")) ? 8 : 2;

    return new CoinbaseMoney(currency, new BigDecimal(amount).movePointLeft(numDecimals));
  }
}

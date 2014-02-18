package com.xeiam.xchange.coinbase.dto.serialization;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CoinbaseCentsDeserializer extends JsonDeserializer<BigMoney> {

  @Override
  public BigMoney deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jp.getCodec();
    final JsonNode node = oc.readTree(jp);

    return getBigMoneyFromCents(node);
  }

  public static BigMoney getBigMoneyFromCents(final JsonNode node) {

    final String amount = node.path("cents").asText();
    final String currency = node.path("currency_iso").asText();
    final CurrencyUnit currencyUnit = CurrencyUnit.of(currency);
    final int numDecimals = (currency.equalsIgnoreCase("BTC")) ? 8 : currencyUnit.getDecimalPlaces();

    return BigMoney.of(currencyUnit, new BigDecimal(amount).movePointLeft(numDecimals));
  }
}

package com.xeiam.xchange.coinbase.dto;

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

public class CoinbaseMoneyDeserializer extends JsonDeserializer<BigMoney> {

  @Override
  public BigMoney deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jp.getCodec();
    final JsonNode node = oc.readTree(jp);
    
    return getBigMoneyFromNode(node);
  }
  
  public static BigMoney getBigMoneyFromNode(final JsonNode node) {
    
    final String amount = node.path("amount").asText();
    final String currency = node.path("currency").asText();
    final CurrencyUnit currencyUnit = CurrencyUnit.of(currency);
    
    return BigMoney.of(currencyUnit, new BigDecimal(amount));
  }

}

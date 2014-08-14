package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicOrder.CryptoTradePublicOrderDeserializer;

@JsonDeserialize(using = CryptoTradePublicOrderDeserializer.class)
public class CryptoTradePublicOrder {

  private final BigDecimal amount;
  private final BigDecimal price;

  private CryptoTradePublicOrder(BigDecimal amount, BigDecimal price) {

    this.amount = amount;
    this.price = price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getPrice() {

    return price;
  }

  @Override
  public String toString() {

    return "CryptoTradePublicOrder [amount=" + amount + ", price=" + price + "]";
  }

  static class CryptoTradePublicOrderDeserializer extends JsonDeserializer<CryptoTradePublicOrder> {

    @Override
    public CryptoTradePublicOrder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final String priceString = node.path(0).asText();
      final String amountString = node.path(1).asText();

      return new CryptoTradePublicOrder(new BigDecimal(amountString), new BigDecimal(priceString));
    }
  }
}

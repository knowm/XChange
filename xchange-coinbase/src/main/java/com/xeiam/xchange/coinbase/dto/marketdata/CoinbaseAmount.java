package com.xeiam.xchange.coinbase.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.money.BigMoney;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount.CoibaseAmountDeserializer;
import com.xeiam.xchange.currency.MoneyUtils;

@JsonDeserialize(using = CoibaseAmountDeserializer.class)
public class CoinbaseAmount {

  private final BigMoney amount;

  public CoinbaseAmount(final BigMoney amount) {

    this.amount = amount;
  }

  public BigMoney getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "CoinbaseAmount [amount=" + amount + "]";
  }

  static class CoibaseAmountDeserializer extends JsonDeserializer<CoinbaseAmount> {

    @Override
    public CoinbaseAmount deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      return new CoinbaseAmount(getBigMoneyFromNode(node));
    }

    private BigMoney getBigMoneyFromNode(JsonNode node) {

      String amount = node.path("amount").asText();
      String currency = node.path("currency").asText();

      return MoneyUtils.parseMoney(currency, new BigDecimal(amount));
    }
  }
}
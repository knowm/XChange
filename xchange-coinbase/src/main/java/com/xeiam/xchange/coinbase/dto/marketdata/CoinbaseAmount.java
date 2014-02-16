package com.xeiam.xchange.coinbase.dto.marketdata;

import java.io.IOException;

import org.joda.money.BigMoney;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.coinbase.dto.CoinbaseMoneyDeserializer;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount.CoibaseAmountDeserializer;

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
      return new CoinbaseAmount(CoinbaseMoneyDeserializer.getBigMoneyFromNode(node));
    }
  }
}
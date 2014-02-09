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
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseRate.CoibaseRateDeserializer;
import com.xeiam.xchange.currency.MoneyUtils;

@JsonDeserialize(using = CoibaseRateDeserializer.class)
public class CoinbaseRate {

  private final BigMoney rate;
  
  public CoinbaseRate(final BigMoney rate) {
    
    this.rate = rate;
  }
  
  public BigMoney getRate() {

    return rate;
  }

  @Override
  public String toString() {

    return "CoinbaseRate [rate=" + rate + "]";
  }

  static class CoibaseRateDeserializer extends JsonDeserializer<CoinbaseRate> {

    @Override
    public CoinbaseRate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      return new CoinbaseRate(getBigMoneyFromNode(node));
    }
    
    public BigMoney getBigMoneyFromNode(JsonNode node) {
      
      String amount = node.path("amount").asText();
      String currency = node.path("currency").asText();
      
      return MoneyUtils.parseMoney(currency, new BigDecimal(amount));
    }
  }
}
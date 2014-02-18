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
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice.CoibasePriceDeserializer;
import com.xeiam.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;

@JsonDeserialize(using = CoibasePriceDeserializer.class)
public class CoinbasePrice {

  private final BigMoney coinbaseFee;
  private final BigMoney bankFee;
  private final BigMoney total;
  private final BigMoney subTotal;
  
  private CoinbasePrice(final BigMoney coinbaseFee, final BigMoney bankFee, final BigMoney total, final BigMoney subTotal) {
    
    this.coinbaseFee = coinbaseFee;
    this.bankFee = bankFee;
    this.total = total;
    this.subTotal = subTotal;
  }
  
  
  public BigMoney getCoinbaseFee() {

    return coinbaseFee;
  }


  public BigMoney getBankFee() {

    return bankFee;
  }


  public BigMoney getTotal() {

    return total;
  }


  public BigMoney getSubTotal() {

    return subTotal;
  }


  @Override
  public String toString() {

    return "CoinbasePrice [coinbaseFee=" + coinbaseFee + ", bankFee=" + bankFee + ", total=" + total + ", subTotal=" + subTotal + "]";
  }

  static class CoibasePriceDeserializer extends JsonDeserializer<CoinbasePrice> {

    @Override
    public CoinbasePrice deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final BigMoney subTotal = CoinbaseMoneyDeserializer.getBigMoneyFromNode(node.path("subtotal"));
      final JsonNode feesNode = node.path("fees");
      final BigMoney coinbaseFee = CoinbaseMoneyDeserializer.getBigMoneyFromNode(feesNode.path(0).path("coinbase"));
      final BigMoney bankFee = CoinbaseMoneyDeserializer.getBigMoneyFromNode(feesNode.path(1).path("bank"));
      final BigMoney total = CoinbaseMoneyDeserializer.getBigMoneyFromNode(node.path("total"));
      return new CoinbasePrice(coinbaseFee, bankFee, total, subTotal);
    }
  }
}

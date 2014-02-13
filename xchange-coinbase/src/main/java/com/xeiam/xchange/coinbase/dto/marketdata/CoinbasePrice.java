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
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice.CoibasePriceDeserializer;
import com.xeiam.xchange.currency.MoneyUtils;


@JsonDeserialize(using = CoibasePriceDeserializer.class)
public class CoinbasePrice {

  private final BigMoney coinbaseFee;
  private final BigMoney bankFee;
  private final BigMoney total;
  private final BigMoney subTotal;
  
  public CoinbasePrice(final BigMoney coinbaseFee, final BigMoney bankFee, final BigMoney total, final BigMoney subTotal) {
    
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

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      BigMoney subTotal = getBigMoneyFromNode(node.path("subtotal"));
      JsonNode feesNode = node.path("fees");
      BigMoney coinbaseFee = getBigMoneyFromNode(feesNode.path(0).path("coinbase"));
      BigMoney bankFee = getBigMoneyFromNode(feesNode.path(1).path("bank"));
      BigMoney total = getBigMoneyFromNode(node.path("total"));
      return new CoinbasePrice(coinbaseFee, bankFee, total, subTotal);
    }
    
    public BigMoney getBigMoneyFromNode(JsonNode node) {
      
      String amount = node.path("amount").asText();
      String currency = node.path("currency").asText();
      
      return MoneyUtils.parseMoney(currency, new BigDecimal(amount));
    }
  }
}

package org.knowm.xchange.coinbase.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbasePrice.CoibasePriceDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoibasePriceDeserializer.class)
public class CoinbasePrice {

  private final CoinbaseMoney coinbaseFee;
  private final CoinbaseMoney bankFee;
  private final CoinbaseMoney total;
  private final CoinbaseMoney subTotal;

  private CoinbasePrice(
      CoinbaseMoney coinbaseFee,
      final CoinbaseMoney bankFee,
      final CoinbaseMoney total,
      final CoinbaseMoney subTotal) {

    this.coinbaseFee = coinbaseFee;
    this.bankFee = bankFee;
    this.total = total;
    this.subTotal = subTotal;
  }

  public CoinbaseMoney getCoinbaseFee() {

    return coinbaseFee;
  }

  public CoinbaseMoney getBankFee() {

    return bankFee;
  }

  public CoinbaseMoney getTotal() {

    return total;
  }

  public CoinbaseMoney getSubTotal() {

    return subTotal;
  }

  @Override
  public String toString() {

    return "CoinbasePrice [coinbaseFee="
        + coinbaseFee
        + ", bankFee="
        + bankFee
        + ", total="
        + total
        + ", subTotal="
        + subTotal
        + "]";
  }

  static class CoibasePriceDeserializer extends JsonDeserializer<CoinbasePrice> {

    @Override
    public CoinbasePrice deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      final CoinbaseMoney subTotal =
          CoinbaseMoneyDeserializer.getCoinbaseMoneyFromNode(node.path("subtotal"));
      final JsonNode feesNode = node.path("fees");
      final CoinbaseMoney coinbaseFee =
          CoinbaseMoneyDeserializer.getCoinbaseMoneyFromNode(feesNode.path(0).path("coinbase"));
      final CoinbaseMoney bankFee =
          CoinbaseMoneyDeserializer.getCoinbaseMoneyFromNode(feesNode.path(1).path("bank"));
      final CoinbaseMoney total =
          CoinbaseMoneyDeserializer.getCoinbaseMoneyFromNode(node.path("total"));
      return new CoinbasePrice(coinbaseFee, bankFee, total, subTotal);
    }
  }
}

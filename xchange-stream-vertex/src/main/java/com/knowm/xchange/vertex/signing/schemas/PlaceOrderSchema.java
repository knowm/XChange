package com.knowm.xchange.vertex.signing.schemas;

import com.knowm.xchange.vertex.signing.EIP712Domain;
import com.knowm.xchange.vertex.signing.EIP712Schema;
import com.knowm.xchange.vertex.signing.EIP712Type;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.Map.of;

public class PlaceOrderSchema extends EIP712Schema {


  private PlaceOrderSchema(EIP712Domain domain, Map<String, Object> message) {
    super(of("Order", List.of(
            new EIP712Type("sender", "bytes32"),
            new EIP712Type("priceX18", "int128"),
            new EIP712Type("amount", "int128"),
            new EIP712Type("expiration", "uint64"),
            new EIP712Type("nonce", "uint64")
        )),
        "Order",
        domain,
        message);
  }

  public static PlaceOrderSchema build(long chainId, String verifyingContract, Long nonce, String sender, BigInteger expiration, BigInteger quantityAsInt, BigInteger priceAsInt) {
    EIP712Domain domain = getDomain(chainId, verifyingContract);

    Map<String, Object> fields = new LinkedHashMap<>();
    fields.put("sender", sender);
    fields.put("priceX18", priceAsInt);
    fields.put("amount", quantityAsInt);
    fields.put("expiration", expiration);
    fields.put("nonce", nonce);

    return new PlaceOrderSchema(domain, fields);
  }

}

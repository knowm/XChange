package com.knowm.xchange.vertex.signing.schemas;

import com.knowm.xchange.vertex.signing.EIP712Domain;
import com.knowm.xchange.vertex.signing.EIP712Schema;
import com.knowm.xchange.vertex.signing.EIP712Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.Map.of;

public class CancelOrdersSchema extends EIP712Schema {

    /*

    {
  Cancellation: [
    { name: 'sender', type: 'bytes32' },
    { name: 'productIds', type: 'uint32[]' },
    { name: 'digests', type: 'bytes32[]' },
    { name: 'nonce', type: 'uint64' },
  ],
}
     */

  private CancelOrdersSchema(EIP712Domain domain, Map<String, Object> message) {
    super(of("Cancellation", List.of(
            new EIP712Type("sender", "bytes32"),
            new EIP712Type("productIds", "uint32[]"),
            new EIP712Type("digests", "bytes32[]"),
            new EIP712Type("nonce", "uint64")
        )),
        "Cancellation",
        domain,
        message);
  }

  public static CancelOrdersSchema build(long chainId, String verifyingContract, Long nonce, String sender, long[] productIds, String[] digests) {

    EIP712Domain domain = getDomain(chainId, verifyingContract);

    Map<String, Object> fields = new LinkedHashMap<>();
    fields.put("sender", sender);
    fields.put("productIds", productIds);
    fields.put("digests", digests);
    fields.put("nonce", nonce);

    return new CancelOrdersSchema(domain, fields);
  }
}

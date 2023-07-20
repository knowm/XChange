package com.knowm.xchange.vertex.signing.schemas;

import com.knowm.xchange.vertex.signing.EIP712Domain;
import com.knowm.xchange.vertex.signing.EIP712Schema;
import com.knowm.xchange.vertex.signing.EIP712Type;
import java.util.List;
import java.util.Map;

public class CancelProductOrdersSchema extends EIP712Schema {

    /*

     'CancellationProducts': [
            {'name': 'sender', 'type': 'bytes32'},
            {'name': 'productIds', 'type': 'uint32[]'},
            {'name': 'nonce', 'type': 'uint64'},
        ],
     */

  private CancelProductOrdersSchema(EIP712Domain domain, Map<String, Object> message) {
    super(Map.of(
            "CancellationProducts", List.of(
                new EIP712Type("sender", "bytes32"),
                new EIP712Type("productIds", "uint32[]"),
                new EIP712Type("nonce", "uint64")
            )),
        "CancellationProducts",
        domain,
        message);
  }

  public static CancelProductOrdersSchema build(long chainId, String endpointContract, Long aLong, String sender, long[] productIds) {
    EIP712Domain domain = getDomain(chainId, endpointContract);
    Map<String, Object> message = Map.of(
        "sender", sender,
        "productIds", productIds,
        "nonce", aLong);
    return new CancelProductOrdersSchema(domain, message);
  }
}

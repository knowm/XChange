package org.knowm.xchange.lgo.dto.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LgoEncryptedOrder {

  private final String keyId;
  private final String order;
  private final LgoOrderSignature signature;
  private final long reference;

  public LgoEncryptedOrder(
      String keyId, String order, LgoOrderSignature signature, long reference) {
    this.keyId = keyId;
    this.order = order;
    this.signature = signature;
    this.reference = reference;
  }

  public String getKeyId() {
    return keyId;
  }

  public String getOrder() {
    return order;
  }

  public LgoOrderSignature getSignature() {
    return signature;
  }

  public long getReference() {
    return reference;
  }
}

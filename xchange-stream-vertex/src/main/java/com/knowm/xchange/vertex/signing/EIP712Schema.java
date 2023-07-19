package com.knowm.xchange.vertex.signing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EIP712Schema {

  public static final List<EIP712Type> DOMAIN_TYPE = List.of(
      new EIP712Type("name", "string"),
      new EIP712Type("version", "string"),
      new EIP712Type("chainId", "uint256"),
      new EIP712Type("verifyingContract", "address")
  );

  private final Map<String, List<EIP712Type>> types;

  private final String primaryType;

  private final EIP712Domain domain;

  private final HashMap<String, Object> message;

  public EIP712Schema(Map<String, List<EIP712Type>> types, String primaryType, EIP712Domain domain, Map<String, Object> message) {
    this.types = new TreeMap<>(types);
    this.types.put("EIP712Domain", DOMAIN_TYPE);
    this.primaryType = primaryType;
    this.message = new HashMap<>(message);
    this.domain = domain;
  }

  protected static EIP712Domain getDomain(long chainId, String verifyingContract) {
    return new EIP712Domain("Vertex", "0.0.1", chainId, verifyingContract);
  }

  public Map<String, List<EIP712Type>> getTypes() {
    return types;
  }

  public String getPrimaryType() {
    return primaryType;
  }

  public EIP712Domain getDomain() {
    return domain;
  }

  public HashMap<String, Object> getMessage() {
    return message;
  }
}

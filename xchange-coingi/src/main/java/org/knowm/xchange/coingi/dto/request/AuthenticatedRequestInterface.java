package org.knowm.xchange.coingi.dto.request;

public interface AuthenticatedRequestInterface {
  String getToken();

  AuthenticatedRequest setToken(String token);

  Long getNonce();

  AuthenticatedRequest setNonce(Long nonce);

  String getSignature();

  AuthenticatedRequest setSignature(String signature);
}

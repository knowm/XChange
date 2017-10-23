package org.knowm.xchange.quoine.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.SynchronizedValueFactory;

public class QuoineSignatureDigest implements ParamsDigest {

  private final JWTCreator.Builder builder;
  private final String tokenID;
  private final byte[] userSecret;
  private final SynchronizedValueFactory<Long> nonceFactory;

  public QuoineSignatureDigest(String tokenID, String userSecret, SynchronizedValueFactory<Long> nonceFactory) {
    this.tokenID = tokenID;
    this.userSecret = userSecret.getBytes();
    this.nonceFactory = nonceFactory;

    this.builder = JWT.create();
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String path = "/" + restInvocation.getMethodPath();

    String queryString = restInvocation.getQueryString();
    if (queryString != null && queryString.length() > 0)
      path += "?" + restInvocation.getQueryString();

    return builder.withClaim("path", path)
        .withClaim("nonce", String.valueOf(nonceFactory.createValue()))
        .withClaim("token_id", tokenID)
        .sign(Algorithm.HMAC256(userSecret));
  }
}

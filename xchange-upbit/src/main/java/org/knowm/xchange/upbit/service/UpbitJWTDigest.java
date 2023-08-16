package org.knowm.xchange.upbit.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import jakarta.ws.rs.QueryParam;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class UpbitJWTDigest implements ParamsDigest {
  private String accessKey;
  private String secretKey;

  private UpbitJWTDigest(String accessKey, String secretKey) throws IllegalArgumentException {
    this.accessKey = accessKey;
    this.secretKey = secretKey;
  }

  public static UpbitJWTDigest createInstance(String accessKey, String secretKey) {
    return new UpbitJWTDigest(accessKey, secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String queryString = "";
    if (restInvocation.getParamsMap().get(QueryParam.class) != null
        && !restInvocation.getParamsMap().get(QueryParam.class).isEmpty()) {
      queryString = String.valueOf(restInvocation.getParamsMap().get(QueryParam.class));
    } else if (restInvocation.getRequestBody() != null
        && !restInvocation.getRequestBody().isEmpty()) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(restInvocation.getRequestBody(), Map.class);
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
          String key = (String) it.next();
          String value = map.get(key);
          queryString += "&" + key + "=" + value;
        }
        queryString = queryString.substring(1);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    JWTCreator.Builder builder = JWT.create();
    builder.withClaim("access_key", accessKey).withClaim("nonce", UUID.randomUUID().toString());
    if (queryString.length() > 0) builder.withClaim("query", queryString);
    String jwtToken = builder.sign(algorithm);
    return "Bearer " + jwtToken;
  }
}
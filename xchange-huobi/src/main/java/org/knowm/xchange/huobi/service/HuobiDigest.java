package org.knowm.xchange.huobi.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class HuobiDigest extends BaseParamsDigest {

  private HuobiDigest(String secretKey) {
    super(secretKey, HMAC_SHA_256);
  }

  static HuobiDigest createInstance(String secretKey) {
    return secretKey == null ? null : new HuobiDigest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String httpMethod = restInvocation.getHttpMethod();
    String host = getHost(restInvocation.getBaseUrl());
    String method = "/" + restInvocation.getMethodPath();
    String query =
        Stream.of(
                restInvocation.getParamsMap().get(FormParam.class),
                restInvocation.getParamsMap().get(QueryParam.class))
            .map(Params::asHttpHeaders)
            .map(Map::entrySet)
            .flatMap(Collection::stream)
            .filter(e -> !"Signature".equals(e.getKey()))
            .sorted(Map.Entry.comparingByKey())
            .map(e -> e.getKey() + "=" + encodeValue(e.getValue()))
            .collect(Collectors.joining("&"));
    String toSign = String.format("%s\n%s\n%s\n%s", httpMethod, host, method, query);
    Mac mac = getMac();
    String signature =
        Base64.getEncoder()
            .encodeToString(mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8)))
            .trim();
    return signature;
  }

  private String getHost(String url) {
    URI uri;
    try {
      uri = new URI(url);
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e.getMessage());
    }
    return uri.getHost();
  }

  private String encodeValue(String value) {
    String ret;
    try {
      ret = URLEncoder.encode(value, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e.getMessage());
    }
    return ret;
  }
}

package org.knowm.xchange.kuna.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class KunaDigest extends BaseParamsDigest {

  public KunaDigest(String secretKey) {
    super(secretKey, HMAC_SHA_256);
  }

  public static KunaDigest createInstance(String secretKey) {
    return secretKey == null ? null : new KunaDigest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String httpMethod = restInvocation.getHttpMethod();
    String method = "/api/v2/" + restInvocation.getMethodPath();
    String query =
        Stream.of(
                restInvocation.getParamsMap().get(FormParam.class),
                restInvocation.getParamsMap().get(QueryParam.class))
            .map(Params::asHttpHeaders)
            .map(Map::entrySet)
            .flatMap(Collection::stream)
            .filter(e -> !"signature".equals(e.getKey()))
            .sorted(Map.Entry.comparingByKey())
            .map(e -> e.getKey() + "=" + encodeValue(e.getValue()))
            .collect(Collectors.joining("&"));
    String toSign = String.format("%s|%s|%s", httpMethod, method, query);
    Mac mac = getMac();
    try {
      mac.update(toSign.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new RuntimeException();
    }
    return DigestUtils.bytesToHex(mac.doFinal()).trim();
  }

  public String digestParams(String httpMethod, String method, Map<String, String> params) {
    String query =
        params
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .map(e -> e.getKey() + "=" + encodeValue(e.getValue()))
            .collect(Collectors.joining("&"));
    String toSign = String.format("%s|%s|%s", httpMethod, method, query);
    Mac mac = getMac();
    try {
      mac.update(toSign.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new RuntimeException();
    }
    return DigestUtils.bytesToHex(mac.doFinal()).trim();
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

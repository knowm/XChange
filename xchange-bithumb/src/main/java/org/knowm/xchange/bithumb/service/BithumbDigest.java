package org.knowm.xchange.bithumb.service;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class BithumbDigest extends BaseParamsDigest {

  private BithumbDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BithumbDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BithumbDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    final String path = restInvocation.getPath();
    final Map<String, String> httpHeadersFromParams = restInvocation.getHttpHeadersFromParams();
    final String requestBody;
    if (restInvocation.getParamsMap().containsKey(FormParam.class)) {
      requestBody =
          restInvocation
              .getParamsMap()
              .getOrDefault(FormParam.class, Params.of())
              .asHttpHeaders()
              .entrySet()
              .stream()
              .map(
                  e -> {
                    final String value =
                        "endpoint".equals(e.getKey()) ? urlEncode(path) : e.getValue();
                    return e.getKey() + "=" + value;
                  })
              .collect(Collectors.joining("&"));
    } else {
      requestBody = StringUtils.EMPTY;
    }

    final String input =
        String.join(";", path, requestBody, httpHeadersFromParams.get("Api-Nonce"));

    final Mac mac = getMac();
    mac.update(input.getBytes(StandardCharsets.UTF_8));
    final String printBase64Binary = bytesToHex(mac.doFinal());
    return Base64.getEncoder().encodeToString(printBase64Binary.getBytes());
  }

  private String urlEncode(String path) {
    try {
      return URLEncoder.encode(path, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }
}

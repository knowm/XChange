package org.knowm.xchange.fcoin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.xml.bind.DatatypeConverter;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class FCoinDigest extends BaseParamsDigest {

  private String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64 the secret key to sign requests
   */
  private FCoinDigest(byte[] secretKeyBase64) {

    super(Base64.getUrlEncoder().withoutPadding().encodeToString(secretKeyBase64), HMAC_SHA_1);
  }

  private FCoinDigest(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_1);
    this.apiKey = apiKey;
  }

  public static FCoinDigest createInstance(String secretKeyBase64) {

    if (secretKeyBase64 != null) {
      return new FCoinDigest(Base64.getUrlDecoder().decode(secretKeyBase64.getBytes()));
    }
    return null;
  }

  public static FCoinDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new FCoinDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String method = restInvocation.getHttpMethod();
    String uri = restInvocation.getInvocationUrl();
    Map<Class<? extends Annotation>, Params> params = restInvocation.getParamsMap();
    if (params.containsKey(QueryParam.class)) {
      uri = params.get(QueryParam.class).applyToPath(uri);
    }
    String bodyStr = restInvocation.getRequestBody();
    ObjectMapper mapper = new ObjectMapper();
    StringBuilder bodyQuery = new StringBuilder();
    try {
      JsonNode body = mapper.readTree(bodyStr);
      List<String> fields = Lists.newArrayList(body.fieldNames());
      fields.sort(String::compareTo);
      for (String field : fields) {
        bodyQuery.append(field).append("=").append(body.get(field).asText()).append("&");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (bodyQuery.lastIndexOf("&") > -1) {
      bodyQuery.deleteCharAt(bodyQuery.lastIndexOf("&"));
    }
    String timestamp =
        restInvocation.getParamValue(HeaderParam.class, "FC-ACCESS-TIMESTAMP").toString();
    String payload = method + uri + timestamp + bodyQuery;
    return DatatypeConverter.printBase64Binary(
        getMac().doFinal(DatatypeConverter.printBase64Binary(payload.getBytes()).getBytes()));
  }
}

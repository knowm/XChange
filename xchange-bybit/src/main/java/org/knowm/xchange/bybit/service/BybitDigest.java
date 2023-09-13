package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.QueryParam;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import lombok.SneakyThrows;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BybitDigest extends BaseParamsDigest {

  public static final String X_BAPI_API_KEY = "X-BAPI-API-KEY";
  public static final String X_BAPI_SIGN = "X-BAPI-SIGN";
  public static final String X_BAPI_TIMESTAMP = "X-BAPI-TIMESTAMP";

  public BybitDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static ParamsDigest createInstance(String secretKeyBase64) {
    return new BybitDigest(secretKeyBase64);
  }

  @SneakyThrows
  @Override
  public String digestParams(RestInvocation restInvocation) {
    Map<String, String> headers = getHeaders(restInvocation);
    Map<String, String> params = getInputParams(restInvocation);
    Map<String, String> sortedParams = new TreeMap<>(params);

    // timestamp + API key + (recv_window) + (queryString | jsonBodyString)
    String plainText = getPlainText(restInvocation, sortedParams);
    String input = headers.get(X_BAPI_TIMESTAMP) + headers.get(X_BAPI_API_KEY) + plainText;

    Mac mac = getMac();
    mac.update(input.getBytes(StandardCharsets.UTF_8));
    return bytesToHex(mac.doFinal());
  }

  private static String getPlainText(
      RestInvocation restInvocation, Map<String, String> sortedParams)
      throws JsonProcessingException {
    if ("GET".equals(restInvocation.getHttpMethod())) {
      Params p = Params.of();
      sortedParams.forEach(p::add);
      return p.asQueryString();
    }
    if ("POST".equals(restInvocation.getHttpMethod())) {
      return new ObjectMapper().writeValueAsString(sortedParams);
    }
    throw new NotYetImplementedForExchangeException(
        "Only GET and POST are supported for plain text");
  }

  private Map<String, String> getHeaders(RestInvocation restInvocation) {
    return restInvocation.getParamsMap().get(HeaderParam.class).asHttpHeaders();
  }

  private Map<String, String> getInputParams(RestInvocation restInvocation) {
    if ("GET".equals(restInvocation.getHttpMethod())) {
      return restInvocation.getParamsMap().get(QueryParam.class).asHttpHeaders();
    }
    if ("POST".equals(restInvocation.getHttpMethod())) {
      return restInvocation.getParamsMap().get(FormParam.class).asHttpHeaders();
    }
    throw new NotYetImplementedForExchangeException("Only GET and POST are supported in digest");
  }
}

package org.knowm.xchange.mexc.service;

import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

public class MEXCDigest extends BaseParamsDigest {

  private static final String SIGNATURE = "Signature";
  private static final String API_KEY = "ApiKey";
  private static final String REQ_TIME = "Request-Time";

  public MEXCDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static ParamsDigest createInstance(String secretKey) {
    return new MEXCDigest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Params p = Params.of();
    Map<String, String> params = getInputParams(restInvocation);
    params.remove(SIGNATURE);
    String apiKey = params.remove(API_KEY);
    String reqTime = params.remove(REQ_TIME);
    Map<String, String> sortedParams = new TreeMap<>(params);
    sortedParams.forEach(p::add);
    String input = apiKey + reqTime + p.asQueryString();
    Mac mac = getMac();
    mac.update(input.getBytes(StandardCharsets.UTF_8));
    return bytesToHex(mac.doFinal());
  }

  private Map<String, String> getInputParams(RestInvocation restInvocation) {
    if ("GET".equals(restInvocation.getHttpMethod())) {
      return restInvocation.getParamsMap().get(HeaderParam.class).asHttpHeaders();
    }
    throw new NotYetImplementedForExchangeException("Only GET and POST are supported in digest");
  }

}

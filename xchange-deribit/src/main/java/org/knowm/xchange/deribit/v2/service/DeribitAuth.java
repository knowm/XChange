package org.knowm.xchange.deribit.v2.service;

import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class DeribitAuth extends BaseParamsDigest {
  private final String clientId;

  public DeribitAuth(String clientId, String clientSecret) {
    super(clientSecret, HMAC_SHA_256);
    this.clientId = clientId;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String timestamp = "" + System.currentTimeMillis();
    String nonce = timestamp;
    String uri = restInvocation.getPath() + "?" + restInvocation.getQueryString();
    String body = restInvocation.getRequestBody();
    String httpMethod = restInvocation.getHttpMethod();
    // ${Timestamp}\n${Nonce}\n${HttpMethod}\n${URI}\n${Body}\n
    String toSign =
        Stream.of(timestamp, nonce, httpMethod, uri, body)
            .collect(Collectors.joining("\n", "", "\n"));

    String signature;
    try {
      signature = DigestUtils.bytesToHex(getMac().doFinal(toSign.getBytes("UTF-8"))).toLowerCase();
    } catch (IllegalStateException | UnsupportedEncodingException e) {
      throw new ExchangeException("Could not sign request", e);
    }
    // deri-hmac-sha256 id=ClientId,ts=Timestamp,sig=Signature,nonce=Nonce
    return "deri-hmac-sha256 id="
        + clientId
        + ",ts="
        + timestamp
        + ",sig="
        + signature
        + ",nonce="
        + nonce;
  }
}

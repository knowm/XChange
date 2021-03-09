package org.knowm.xchange.lgo.service;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lgo.Lgo;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoEnv.SignatureService;
import org.knowm.xchange.lgo.dto.order.LgoOrderSignature;
import si.mazi.rescu.HttpMethod;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public interface LgoSignatureService extends ParamsDigest {

  static LgoSignatureService createInstance(ExchangeSpecification specification) {
    LgoEnv.SignatureService implementation =
        (SignatureService)
            specification
                .getExchangeSpecificParameters()
                .getOrDefault(LgoEnv.SIGNATURE_SERVICE, SignatureService.LOCAL_RSA);
    switch (implementation) {
      case LOCAL_RSA:
        return new LgoSignatureServiceLocalRsa(
            specification.getApiKey(), specification.getSecretKey());
      case PASSTHROUGHS:
        return new LgoSignatureServicePassthroughs(
            specification.getUserName(), specification.getApiKey(), specification.getSecretKey());
      default:
        throw new ExchangeException("Unknown signature service implementation " + implementation);
    }
  }

  @Override
  default String digestParams(RestInvocation restInvocation) {
    String rawUrl = restInvocation.getInvocationUrl();
    String timestamp = restInvocation.getHttpHeadersFromParams().getOrDefault(Lgo.X_LGO_DATE, "");
    if (needsBodySignature(restInvocation)) {
      return digestSignedUrlAndBodyHeader(rawUrl, timestamp, restInvocation.getRequestBody());
    }
    return digestSignedUrlHeader(rawUrl, timestamp);
  }

  default boolean needsBodySignature(RestInvocation restInvocation) {
    return restInvocation.getPath().equals("/v1/live/orders")
        && restInvocation.getHttpMethod().equals(HttpMethod.POST.name());
  }

  String digestSignedUrlHeader(String urlToSign, String timestamp);

  String digestSignedUrlAndBodyHeader(String urlToSign, String timestamp, String body);

  LgoOrderSignature signOrder(String encryptedOrder);
}

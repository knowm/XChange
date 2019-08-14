package org.knowm.xchange.lgo.service;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lgo.Lgo;
import org.knowm.xchange.lgo.LgoEnv;
import org.knowm.xchange.lgo.LgoEnv.SignatureService;
import org.knowm.xchange.lgo.dto.order.LgoOrderSignature;
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
    return digestHeader(rawUrl, timestamp);
  }

  String digestHeader(String urlToSign, String timestamp);

  LgoOrderSignature signOrder(String encryptedOrder);
}

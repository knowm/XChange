package org.knowm.xchange.okcoin;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class OkexDigestV3 extends BaseParamsDigest {

  private static final String UTF_8 = "UTF-8";

  public OkexDigestV3(String secretKey) {
    super(secretKey, HMAC_SHA_256);
  }

  @Override
  public String digestParams(RestInvocation ri) {
    String timestamp = ri.getHttpHeadersFromParams().get(OkexV3.OK_ACCESS_TIMESTAMP);
    String toSign =
        timestamp
            + ri.getHttpMethod()
            + ri.getPath()
            + (ri.getQueryString() != null && !ri.getQueryString().isEmpty()
                ? "?" + ri.getQueryString()
                : "")
            + (ri.getRequestBody() == null ? "" : ri.getRequestBody());
    byte[] signSHA256;
    try {
      signSHA256 = getMac().doFinal(toSign.getBytes(UTF_8));
    } catch (IllegalStateException | UnsupportedEncodingException e) {
      throw new ExchangeException("Could not sign the request", e);
    }
    return Base64.getEncoder().encodeToString(signSHA256);
  }
}

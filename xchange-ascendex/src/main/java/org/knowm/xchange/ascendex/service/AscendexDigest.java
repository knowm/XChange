package org.knowm.xchange.ascendex.service;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import java.util.Base64;

public class AscendexDigest extends BaseParamsDigest {

  public AscendexDigest(String secretKeyBase64) throws IllegalArgumentException {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static AscendexDigest createInstance(String secretKeyBase64) {
    if (secretKeyBase64 != null) {
      return new AscendexDigest(secretKeyBase64);
    } else return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String message;

    if (restInvocation.getPath().contains("cash")) {
      message =
          restInvocation.getHttpHeadersFromParams().get("x-auth-timestamp")
              + restInvocation
                  .getPath()
                  .substring(restInvocation.getPath().lastIndexOf("cash") + 5);
    } else if (restInvocation.getPath().contains("margin")) {
      message =
          restInvocation.getHttpHeadersFromParams().get("x-auth-timestamp")
              + restInvocation
                  .getPath()
                  .substring(restInvocation.getPath().lastIndexOf("margin") + 7);
    } else {
      message =
          restInvocation.getHttpHeadersFromParams().get("x-auth-timestamp")
              + restInvocation.getPath().substring(restInvocation.getPath().lastIndexOf("/") + 1);
    }

    Mac mac256 = getMac();
    mac256.update(message.getBytes());

    return Base64.getEncoder().encodeToString(mac256.doFinal()).trim();
  }
}

package org.knowm.xchange.globitex.service;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import javax.crypto.Mac;
import jakarta.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class GlobitexDigest extends BaseParamsDigest {

  private GlobitexDigest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static GlobitexDigest createInstance(String secretKeyBase64) {
    if (secretKeyBase64 != null) {
      return new GlobitexDigest(secretKeyBase64.getBytes());
    } else return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac512 = getMac();
    mac512.update(
        (restInvocation.getParamValue(HeaderParam.class, "X-API-Key").toString() + "&").getBytes());
    mac512.update(restInvocation.getParamValue(HeaderParam.class, "X-Nonce").toString().getBytes());
    mac512.update((restInvocation.getPath().getBytes()));
    if (!(restInvocation.getQueryString().isEmpty() && restInvocation.getRequestBody().isEmpty())) {
      mac512.update(("?" + restInvocation.getQueryString()).getBytes());
      mac512.update((restInvocation.getRequestBody()).getBytes());
    }

    return bytesToHex(mac512.doFinal()).toLowerCase();
  }
}
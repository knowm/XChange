package org.knowm.xchange.ftx.service;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import jakarta.ws.rs.HeaderParam;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class FtxDigest extends BaseParamsDigest {

  private FtxDigest(byte[] secretKey) {

    super(secretKey, HMAC_SHA_256);
  }

  public static FtxDigest createInstance(String secretKey) {

    if (secretKey != null) {
      return new FtxDigest(secretKey.getBytes());
    } else return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String message =
        restInvocation.getParamValue(HeaderParam.class, "FTX-TS").toString()
            + restInvocation.getHttpMethod().toUpperCase()
            + restInvocation.getPath();

    if (!restInvocation.getQueryString().isEmpty()) {
      message += "?" + restInvocation.getQueryString();
    }

    if (restInvocation.getHttpMethod().equals("POST")
        || (restInvocation.getPath().contains("/orders")
                && restInvocation.getHttpMethod().equals("DELETE"))
            && restInvocation.getRequestBody() != null) {
      message += restInvocation.getRequestBody();
    }

    Mac mac256 = getMac();

    try {
      mac256.update(message.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new ExchangeException("Digest encoding exception", e);
    }

    return DigestUtils.bytesToHex(mac256.doFinal()).toLowerCase();
  }
}

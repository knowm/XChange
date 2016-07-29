package org.knowm.xchange.quoine.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class QuoineSignatureDigest extends BaseParamsDigest {

  private final String userID;
  private final String COMMA_SEPARATOR = ",";

  public QuoineSignatureDigest(String userID, String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_1);
    this.userID = userID;
  }

  public ParamsDigest getContentMD5Digester() {
    return new QuoineContentMD5Digest();
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String contentMD5 = getContentMD5(restInvocation.getRequestBody());
    String date = restInvocation.getParamValue(HeaderParam.class, "Date").toString();
    String nonce = restInvocation.getParamValue(HeaderParam.class, "NONCE").toString();

    String data = new StringBuilder(256) // most lengths are ~128, lets avoid resizing
        .append("application/json,").append(contentMD5).append(COMMA_SEPARATOR).append(restInvocation.getPath()).append(COMMA_SEPARATOR).append(date)
        .append(COMMA_SEPARATOR).append(nonce).toString();

    Mac mac = getMac();
    byte[] hash = mac.doFinal(data.getBytes());

    return new StringBuilder(64).append("APIAuth ").append(userID).append(":").append(Base64.encodeBytes(hash)).toString();
  }

  private String getContentMD5(String content) {
    if (content == null || "".equals(content)) {
      return "";
    }
    String digest = null;
    try {
      byte[] bytesOfMessage = content.getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("MD5");
      digest = Base64.encodeBytes(md.digest(bytesOfMessage));
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return digest;
  }

  private class QuoineContentMD5Digest implements ParamsDigest {

    @Override
    public String digestParams(RestInvocation restInvocation) {

      return getContentMD5(restInvocation.getRequestBody());
    }
  }
}

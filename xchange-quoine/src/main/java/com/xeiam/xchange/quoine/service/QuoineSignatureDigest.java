package com.xeiam.xchange.quoine.service;

import com.xeiam.xchange.service.BaseParamsDigest;
import net.iharder.Base64;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class QuoineSignatureDigest extends BaseParamsDigest {

  private final String userID;

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
    String data = "application/json," + contentMD5 + "," + restInvocation.getPath() + "," + date + "," + nonce;

    Mac mac = getMac();
    byte[] hash = mac.doFinal(data.getBytes());

    return "APIAuth " + userID + ":" + Base64.encodeBytes(hash);
  }

  private String getContentMD5(String content) {

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

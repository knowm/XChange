package org.knowm.xchange.dragonex.service;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Formatter;
import javax.crypto.Mac;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/** https://github.com/Dragonexio/OpenApi/blob/master/docs/English/0.way_of_invocation.md */
public class DragonDigest extends BaseParamsDigest {

  private final String api;

  public DragonDigest(String api, String secret) {
    super(secret, HMAC_SHA_1);
    this.api = api;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String date = restInvocation.getHttpHeadersFromParams().get("date");
    String sha1 = sha1(restInvocation.getRequestBody());
    String s =
        restInvocation.getHttpMethod()
            + "\n"
            + sha1
            + "\n"
            + MediaType.APPLICATION_JSON
            + "\n"
            + date
            + "\n"
            + "/"
            + restInvocation.getPath();

    Mac mac = getMac();
    mac.update(s.getBytes());
    return api + ":" + Base64.getEncoder().encodeToString(mac.doFinal());
  }

  public static String sha1(String str) {
    if (str == null) {
      return "";
    }
    String sha1 = "";
    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(str.getBytes("UTF-8"));
      sha1 = byteToHex(crypt.digest());
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
    return sha1;
  }

  private static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }
}

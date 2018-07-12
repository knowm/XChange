package org.knowm.xchange.coindirect.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoindirectHawkDigest extends BaseParamsDigest {
  private String apiKey;
  private String apiSecret;

  private CoindirectHawkDigest(String apiKey, String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);

    this.apiKey = apiKey;
    this.apiSecret = secretKeyBase64;
  }

  public static CoindirectHawkDigest createInstance(String apiKey, String secretKey) {
    return secretKey == null || apiKey == null ? null : new CoindirectHawkDigest(apiKey, secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    long timestamp = Math.round(System.currentTimeMillis() / 1000);

    try {
      URL url = new URL(restInvocation.getInvocationUrl());

      String nonce = UUID.randomUUID().toString().substring(0, 8);
      String method = restInvocation.getHttpMethod().toUpperCase();
      String path = url.getPath();
      String query = url.getQuery();
      String host = url.getHost();
      int port = url.getPort();

      if (port == -1) {
        port = url.getDefaultPort();
      }

      StringBuilder hawkHeader = new StringBuilder();
      hawkHeader.append("hawk.1.header\n");
      hawkHeader.append(timestamp);
      hawkHeader.append("\n");
      hawkHeader.append(nonce);
      hawkHeader.append("\n");
      hawkHeader.append(method);
      hawkHeader.append("\n");
      hawkHeader.append(path);
      if (query != null) {
        hawkHeader.append("?");
        hawkHeader.append(query);
      }
      hawkHeader.append("\n");
      hawkHeader.append(host);
      hawkHeader.append("\n");
      hawkHeader.append(port);
      hawkHeader.append("\n");
      // body
      hawkHeader.append("\n");
      // app data
      hawkHeader.append("\n");

      String mac = generateHash(apiSecret, hawkHeader.toString());

      String authorization =
          "Hawk id=\""
              + apiKey
              + "\", ts=\""
              + timestamp
              + "\", nonce=\""
              + nonce
              + "\", mac=\""
              + mac
              + "\"";

      return authorization;
    } catch (MalformedURLException ignored) {

    }
    return null;
  }

  private String generateHash(String key, String data) {
    Mac sha256_HMAC = null;
    String result = null;

    try {
      byte[] byteKey = key.getBytes("UTF-8");
      final String HMAC_SHA256 = "HmacSHA256";
      sha256_HMAC = Mac.getInstance(HMAC_SHA256);
      SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA256);
      sha256_HMAC.init(keySpec);
      byte[] mac_data = sha256_HMAC.doFinal(data.getBytes());
      return Base64.getEncoder().encodeToString(mac_data);
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
    }
    return "";
  }
}

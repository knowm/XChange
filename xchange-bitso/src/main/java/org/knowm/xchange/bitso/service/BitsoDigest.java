package org.knowm.xchange.bitso.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Date;
import javax.crypto.Mac;
import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BitsoDigest extends BaseParamsDigest {

  private final String clientId;

  private final String apiKey;
  ObjectMapper objectMapper = new ObjectMapper();

  private BitsoDigest(String secretKeyHex, String clientId, String apiKey) {
    super(secretKeyHex, HMAC_SHA_256);
    this.clientId = clientId;
    this.apiKey = apiKey;
  }

  public static BitsoDigest createInstance(String secretKey, String userName, String apiKey) {
    return secretKey == null ? null : new BitsoDigest(secretKey, userName, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Mac mac256 = getMac();
    Long nonce = new Date().getTime();
    String signature = nonce + restInvocation.getHttpMethod() + "/" + restInvocation.getPath();
    String body = "";

    if (!restInvocation.getUnannanotatedParams().isEmpty()) {
      for (Object rest : restInvocation.getUnannanotatedParams()) {
        try {
          body = objectMapper.writeValueAsString(rest);
          body.replace("\":\"", "\": \"");
          System.out.println(body);

        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
      }
    }

    if (!ObjectUtils.isEmpty(body)) {
      signature = signature + body;
    }

    mac256.update(signature.getBytes());
    byte[] arrayOfByte = mac256.doFinal();
    BigInteger localBigInteger = new BigInteger(1, arrayOfByte);

    String finalValue =
        String.format("%0" + (arrayOfByte.length << 1) + "x", new Object[] {localBigInteger});
    String value = "Bitso " + apiKey + ":" + nonce + ":" + finalValue;

    System.out.println("Method : " + restInvocation.getHttpMethod());
    System.out.println("Path : /" + restInvocation.getPath());
    System.out.println("Body : " + restInvocation.getUnannanotatedParams());
    System.out.println("Bitso Digest is    " + value);
    return value;
  }

  public String digestParams(String method, String path, Object body) {
    Mac mac256 = getMac();
    Long nonce = new Date().getTime();
    String signature = nonce + method + path;
    //    String signature="1608625271081GET/v3/balance/";
    String mainBody = "";
    if (!ObjectUtils.isEmpty(body)) {
      try {
        if (body instanceof String) {
          mainBody = body.toString() + "/";
        } else {
          mainBody = objectMapper.writeValueAsString(body);
        }
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    if (!ObjectUtils.isEmpty(body)) {
      signature = signature + mainBody;
    }
    try {
      mac256.update(signature.getBytes("UTF-8"));
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    byte[] arrayOfByte = mac256.doFinal();
    BigInteger localBigInteger = new BigInteger(1, arrayOfByte);

    String finalValue =
        String.format("%0" + (arrayOfByte.length << 1) + "x", new Object[] {localBigInteger});
    String value = "Bitso " + apiKey + ":" + nonce + ":" + finalValue;

    System.out.println("Method : " + method);
    System.out.println("Path : /" + path);
    System.out.println("Body : " + body);
    System.out.println("Bitso Digest is    " + value);
    return value;
  }
}

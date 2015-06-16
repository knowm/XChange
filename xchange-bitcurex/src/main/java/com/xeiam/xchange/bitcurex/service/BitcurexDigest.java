package com.xeiam.xchange.bitcurex.service;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;
import com.xeiam.xchange.utils.Base64;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BitcurexDigest extends BaseParamsDigest {

    private final Field invocationUrlField;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BitcurexDigest(String secretKeyBase64, String apiKey) {
      super(secretKeyBase64, HMAC_SHA_512);
      try {
          invocationUrlField = RestInvocation.class.getDeclaredField("invocationUrl");
          invocationUrlField.setAccessible(true);
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }

  public static BitcurexDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitcurexDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Long nonce = (Long)restInvocation.getParamValue(QueryParam.class, "nonce");
    String market = (String)restInvocation.getParamValue(PathParam.class, "market");
    String requestBody = restInvocation.getRequestBody();

    List<String> msgParams = new ArrayList<String>();
    if (market != null && !market.isEmpty()) {
      msgParams.add("market=" + market);
    }
    if (nonce != null && nonce > 0) {
      msgParams.add("nonce=" + nonce);
    }
    if (requestBody != null && !requestBody.isEmpty()) {
      msgParams.add(requestBody);
    }

    StringBuilder message = new StringBuilder();

    Iterator<String> iterator = msgParams.iterator();
    while (iterator.hasNext()) {
      String msgParam = iterator.next();
      message.append(msgParam);
      if (iterator.hasNext()) {
        message.append("&");
      }
    }

    Mac sha512 = getMac();
    sha512.update(message.toString().getBytes());

    String format = String.format("%0128x", new BigInteger(1, sha512.doFinal()));
    String invocationUrl = restInvocation.getInvocationUrl();
    String newInvocationUrl = invocationUrl.replace("hash", format);
    try {
        invocationUrlField.set(restInvocation, newInvocationUrl);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    System.out.println(message.toString());

    System.out.println(restInvocation.getInvocationUrl());


    return format;
  }
}

package com.xeiam.xchange.bitcurex.service;

import java.io.IOException;

import javax.crypto.Mac;
import javax.xml.bind.DatatypeConverter;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;
import com.xeiam.xchange.utils.Base64;

public class BitcurexDigest extends BaseParamsDigest {

  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @param clientId
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BitcurexDigest(String secretKeyBase64, String apiKey) throws IOException {

    super(DatatypeConverter.parseBase64Binary(secretKeyBase64), HMAC_SHA_512);
  }

  public static BitcurexDigest createInstance(String secretKeyBase64, String apiKey) throws IOException {

    return secretKeyBase64 == null ? null : new BitcurexDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String digest;
    byte[] signature;

    Mac sha512 = getMac();

    sha512.update(restInvocation.getRequestBody().getBytes());

    signature = sha512.doFinal();
    digest = Base64.encodeBytes(signature);

    return digest;
  }
}

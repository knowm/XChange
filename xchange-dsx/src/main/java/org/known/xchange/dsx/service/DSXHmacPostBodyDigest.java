package org.known.xchange.dsx.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import javax.xml.bind.DatatypeConverter;

import org.knowm.xchange.service.BaseParamsDigest;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import net.iharder.Base64;

import si.mazi.rescu.RestInvocation;

/**
 * @author Mikhail Wall
 */

public class DSXHmacPostBodyDigest extends BaseParamsDigest {

  private DSXHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static DSXHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new DSXHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    try {
      String postBody = restInvocation.getRequestBody();
      Mac mac = getMac();
      mac.update(postBody.getBytes("UTF-8"));
      //mac.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
      //byte[] res = mac.doFinal();
      return DatatypeConverter.printBase64Binary(mac.doFinal());
      //System.out.println(DatatypeConverter.printBase64Binary(mac.doFinal()));
      //return String.format("%064x", new BigInteger(1, mac.doFinal()));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Illegal encoding, check the code.", e);
    }
//
//    String digest;
//    byte[] signature;
//
//    Mac sha512 = getMac();
//
//    sha512.update(restInvocation.getRequestBody().getBytes());
//
//    signature = sha512.doFinal();
//    digest = Base64.encodeBytes(signature);
//
//    return digest;
  }
}

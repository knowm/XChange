package org.knowm.xchange.cryptofacilities.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.RestInvocation;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private CryptoFacilitiesDigest(byte[] secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static CryptoFacilitiesDigest createInstance(String secretKeyBase64) {

    try {
      if (secretKeyBase64 != null)
        return new CryptoFacilitiesDigest(Base64.decode(secretKeyBase64.getBytes()));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not decode Base 64 string", e);
    }
    return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    MessageDigest sha256;
    try {
      sha256 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }

    String decodedQuery = null;

    try {
      decodedQuery = URLDecoder.decode(restInvocation.getQueryString(), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Unsupported query encoding", e);
    }

    sha256.update(decodedQuery.getBytes());
    sha256.update(restInvocation.getParamValue(HeaderParam.class, "Nonce").toString().getBytes());
    sha256.update((restInvocation.getPath()).getBytes());

    Mac mac512 = getMac();
    mac512.update(sha256.digest());

    return Base64.encodeBytes(mac512.doFinal()).trim();
  }
}

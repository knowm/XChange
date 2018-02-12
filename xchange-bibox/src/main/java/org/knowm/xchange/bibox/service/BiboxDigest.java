package org.knowm.xchange.bibox.service;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.FormParam;

import org.apache.commons.codec.binary.Hex;
import org.knowm.xchange.bibox.BiboxAuthenticated;
import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class BiboxDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKey
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BiboxDigest(String secretKey) {

    super(secretKey, HMAC_MD5);
  }

  public static BiboxDigest createInstance(String secretKey) {

    return secretKey == null ? null : new BiboxDigest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String cmds = (String) restInvocation.getParamValue(FormParam.class, BiboxAuthenticated.FORM_CMDS);
    try {
      return new String(Hex.encodeHex(getMac().doFinal(cmds.getBytes("UTF-8"))));
    } catch (IllegalStateException | UnsupportedEncodingException e1) {
      throw new RuntimeException(e1.getMessage());
    }
  }
}

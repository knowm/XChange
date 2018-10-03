package org.knowm.xchange.kucoin.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import org.knowm.xchange.kucoin.KucoinAuthenticated;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class KucoinDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private KucoinDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static KucoinDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new KucoinDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    // just write down their example code without thinking too much
    // https://kucoinapidocs.docs.apiary.io/#introduction/authentication/signature-calculation
    String endpoint = "/" + restInvocation.getPath(); // needs leading slash
    String queryString =
        restInvocation
            .getParamsMap()
            .get(QueryParam.class)
            .asHttpHeaders()
            .entrySet()
            .stream()
            .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining("&"));
    Long nonce =
        (Long) restInvocation.getParamValue(HeaderParam.class, KucoinAuthenticated.HEADER_NONCE);
    String strForSign = endpoint + "/" + nonce + "/" + queryString;
    Mac mac = getMac();
    try {
      mac.update(Base64.getEncoder().encode(strForSign.getBytes("UTF-8")));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return DigestUtils.bytesToHex(mac.doFinal()).toLowerCase();
  }
}

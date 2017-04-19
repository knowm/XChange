package org.knowm.xchange.btcchina.service;

import javax.crypto.Mac;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.btcchina.BTCChinaUtils;
import org.knowm.xchange.btcchina.dto.BTCChinaRequest;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.BasicAuthCredentials;
import si.mazi.rescu.RestInvocation;

/**
 * @author David Yam
 */
public class BTCChinaDigest extends BaseParamsDigest {

  private final Logger log = LoggerFactory.getLogger(BTCChinaDigest.class);

  private final String exchangeAccessKey;

  /**
   * Constructor
   *
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BTCChinaDigest(String exchangeAccessKey, String exchangeSecretKey) {

    super(exchangeSecretKey, HMAC_SHA_1);
    this.exchangeAccessKey = exchangeAccessKey;
  }

  public static BTCChinaDigest createInstance(String exchangeAccessKey, String exchangeSecretKey) {

    return exchangeSecretKey == null ? null : new BTCChinaDigest(exchangeAccessKey, exchangeSecretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String tonce = restInvocation.getHttpHeadersFromParams().get("Json-Rpc-Tonce");

    BTCChinaRequest request = null;
    for (Object param : restInvocation.getUnannanotatedParams()) {
      if (param instanceof BTCChinaRequest) {
        request = (BTCChinaRequest) param;
      }
    }

    if (request == null) {
      throw new IllegalArgumentException("No BTCChinaRequest found.");
    }

    final long id = request.getId();
    final String method = request.getMethod();
    final String params = stripParams(request.getParams());

    String signature = String.format("tonce=%s&accesskey=%s&requestmethod=%s&id=%d&method=%s&params=%s", tonce, exchangeAccessKey, "post", id, method,
        params);
    log.debug("signature message: {}", signature);

    Mac mac = getMac();
    byte[] hash = mac.doFinal(signature.getBytes());

    BasicAuthCredentials auth = new BasicAuthCredentials(exchangeAccessKey, BTCChinaUtils.bytesToHex(hash));

    return auth.digestParams(restInvocation);
  }

  /**
   * Strip the {@code params} for signature message.
   *
   * @param params the value of {@link BTCChinaRequest#getParams()}.
   * @return the params string for signature message.
   * @see the note in <a href="http://btcchina.org/api-trade-documentation-en#faq">FAQ</a> 4.2(USING OPENONLY AS TRUE EXAMPLE)
   */
  private String stripParams(String params) {

    final String[] original = params.substring(1, params.length() - 1).split(",");
    final String[] stripped = new String[original.length];

    for (int i = 0; i < original.length; i++) {
      final String param = original[i];

      if (param.startsWith("\"") && param.endsWith("\"")) {
        // string
        stripped[i] = param.substring(1, param.length() - 1);
      } else if (param.equals("true")) {
        // boolean: true
        stripped[i] = "1";
      } else if (param.equals("false")) {
        // boolean: false
        stripped[i] = StringUtils.EMPTY;
      } else if (param.equals("null")) {
        stripped[i] = StringUtils.EMPTY;
      } else {
        // number, etc.
        stripped[i] = param;
      }

    }
    return StringUtils.join(stripped, ",");
  }

}
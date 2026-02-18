package org.knowm.xchange.binance.service;

import jakarta.ws.rs.QueryParam;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BinanceED25519Digest implements ParamsDigest {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceED25519Digest.class);
  private final Charset charSet = StandardCharsets.UTF_8;
  private Signer signer;

  private BinanceED25519Digest(String secretKeyBase64) {
    try {
      byte[] decodePrivateKey = Base64.getDecoder().decode(secretKeyBase64.getBytes(charSet));
      PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodePrivateKey);
      PrivateKeyInfo instancePrivate = PrivateKeyInfo.getInstance(pkcs8EncodedKeySpec.getEncoded());
      AsymmetricKeyParameter keyPrivate = PrivateKeyFactory.createKey(instancePrivate);
      signer = new Ed25519Signer();
      signer.init(true, keyPrivate);
    } catch (IOException e) {
      LOG.error("", e);
    }
  }

  public static BinanceED25519Digest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new BinanceED25519Digest(secretKeyBase64);
  }

  /**
   * @return the query string except of the "signature" parameter
   */
  private static String getQuery(RestInvocation restInvocation) {
    final Params p = Params.of();
    restInvocation.getParamsMap().get(QueryParam.class).asHttpHeaders().entrySet().stream()
        .filter(e -> !BinanceAuthenticated.SIGNATURE.equals(e.getKey()))
        .forEach(e -> p.add(e.getKey(), e.getValue()));
    return p.asQueryString();
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    final String input;

    switch (restInvocation.getHttpMethod()) {
      case "GET":
      case "DELETE":
        input = getQuery(restInvocation);
        break;
      case "POST":
        input = getQuery(restInvocation) + restInvocation.getRequestBody();
        break;
      case "PUT":
        input = getQuery(restInvocation) + restInvocation.getRequestBody();
        break;
      default:
        throw new RuntimeException("Not support http method: " + restInvocation.getHttpMethod());
    }
    try {
      var payloadBytes = input.getBytes(charSet);
      signer.update(payloadBytes, 0, payloadBytes.length);
      byte[] printBase64Binary = signer.generateSignature();
      return new String(Base64.getEncoder().encode(printBase64Binary));
    } catch (CryptoException e) {
      LOG.error("", e);
    }
    return null;
  }
}

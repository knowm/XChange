package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import org.knowm.xchange.deribit.v2.Deribit;
import org.knowm.xchange.deribit.v2.DeribitAuthenticated;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.GrantType;
import org.knowm.xchange.deribit.v2.dto.account.DeribitAuthentication;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class DeribitBaseService extends BaseExchangeService<DeribitExchange>
    implements BaseService {

  protected final Deribit deribit;
  protected final DeribitAuthenticated deribitAuthenticated;
  @Getter protected final DeribitAuth deribitAuth;
  private DeribitAuthentication deribitAuthentication;

  /**
   * Constructor
   *
   * @param exchange
   */
  public DeribitBaseService(DeribitExchange exchange) {

    super(exchange);
    deribit =
        RestProxyFactory.createProxy(
            Deribit.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());

    deribitAuthenticated =
        RestProxyFactory.createProxy(
            DeribitAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    deribitAuth =
        DeribitAuth.createDeribitAuth(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getNonceFactory());
  }

  protected ParamsDigest getDeribitAuthentication() throws DeribitException, IOException {

    DeribitAuthentication auth = deribitAuthentication;
    if (auth != null && auth.valid()) {
      return auth;
    }

    synchronized (this) {
      auth = deribitAuthentication;
      if (auth != null && auth.valid()) {
        return auth;
      }
      auth =
          authOverClientSignature(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getExchangeSpecification().getSecretKey());
      deribitAuthentication = auth;
    }
    return auth;
  }

  private DeribitAuthentication authOverClientCredentials(String clientId, String clientSecret)
      throws DeribitException, IOException {
    return deribit
        .auth(
            GrantType.client_credentials,
            null,
            null,
            clientId,
            clientSecret,
            null,
            null,
            null,
            null,
            null,
            null)
        .getResult();
  }

  private DeribitAuthentication authOverClientSignature(String clientId, String clientSecret)
      throws DeribitException, IOException {
    if (clientId == null || clientId.isEmpty()) {
      throw new ExchangeSecurityException("API key must not be empty.");
    }
    if (clientSecret == null || clientSecret.isEmpty()) {
      throw new ExchangeException("API secret must not be empty.");
    }

    Mac mac;
    try {
      mac = Mac.getInstance(BaseParamsDigest.HMAC_SHA_256);
      final SecretKey secretKey =
          new SecretKeySpec(clientSecret.getBytes("UTF-8"), BaseParamsDigest.HMAC_SHA_256);
      mac.init(secretKey);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new ExchangeException("Invalid API secret", e);
    }

    String timestamp = "" + System.currentTimeMillis();
    String nonce = timestamp;
    String data = "";
    String toSign = timestamp + "\n" + nonce + "\n" + data;

    String signature = DigestUtils.bytesToHex(mac.doFinal(toSign.getBytes("UTF-8"))).toLowerCase();
    return deribit
        .auth(
            GrantType.client_signature,
            null,
            null,
            clientId,
            null,
            null,
            timestamp,
            signature,
            nonce,
            null,
            null)
        .getResult();
  }
}

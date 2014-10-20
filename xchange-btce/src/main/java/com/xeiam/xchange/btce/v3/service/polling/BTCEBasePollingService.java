package com.xeiam.xchange.btce.v3.service.polling;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCE;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;
import com.xeiam.xchange.btce.v3.service.BTCEHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import javax.net.ssl.*;

/**
 * @author Matija Mazi
 */
public class BTCEBasePollingService<T extends BTCE> extends BaseExchangeService implements BasePollingService {

  private final Logger logger = LoggerFactory.getLogger(BTCEBasePollingService.class);

  public final Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();

  private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
  // counter for the nonce
  private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

  protected final String apiKey;
  protected final T btce;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEBasePollingService(Class<T> btceType, ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.btce = RestProxyFactory.createProxy(btceType, exchangeSpecification.getSslUri(), createSSLConfig());
    this.apiKey = exchangeSpecification.getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchangeSpecification.getSecretKey());
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    if (currencyPairs.isEmpty()) {
      currencyPairs.addAll(BTCEAdapters.adaptCurrencyPairs(btce.getInfo().getPairs().keySet()));
    }

    return currencyPairs;
  }

  protected int nextNonce() {

    int nextNonce = lastNonce.incrementAndGet();
    logger.debug("nextNonce in BTCEBaseService: " + nextNonce);

    return nextNonce;
  }

  protected void checkResult(BTCEReturn<?> info) {

    if (!info.isSuccess()) {
      throw new ExchangeException("BTCE returned an error: " + info.getError());
    }
    else if (info.getReturnValue() == null) {
      throw new ExchangeException("Didn't recieve any return value. Message: " + info.getError());
    }
    else if (info.getError() != null) {
      throw new ExchangeException("Got error message: " + info.getError());
    }
  }

    private static SSLSocketFactory createSSLSocketFactory(String certFile) {
        if (certFile == null) {
            return null;
        }
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream in = BTCEBasePollingService.class.getResourceAsStream(certFile);

            InputStream caInput = new BufferedInputStream(in);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            return context.getSocketFactory();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected ClientConfig createSSLConfig() {

        ClientConfig config = new ClientConfig();
        config.setSslSocketFactory(createSSLSocketFactory("/btce.cert"));
        config.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslSession) {
                return exchangeSpecification.getHost().equals(s);
            }
        });
        return config;
    }
}

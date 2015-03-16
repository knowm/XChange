package com.xeiam.xchange.btce.v3.service.polling;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;
import com.xeiam.xchange.btce.v3.service.BTCEHmacPostBodyDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.FundsExceededException;
import com.xeiam.xchange.exceptions.NonceException;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import javax.net.ssl.*;

public class BTCEBasePollingService extends BaseExchangeService implements BasePollingService {

  //  protected static final String PREFIX = "btce";
  //  protected static final String KEY_ORDER_SIZE_SCALE_DEFAULT = PREFIX + SUF_ORDER_SIZE_SCALE_DEFAULT;

  private static final String ERR_MSG_NONCE = "invalid nonce parameter; on key:";
  private static final String ERR_MSG_FUNDS = "It is not enough ";

  protected final String apiKey;
  protected final BTCEAuthenticated btce;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEBasePollingService(Exchange exchange) {

    super(exchange);

    this.btce = RestProxyFactory.createProxy(BTCEAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), createSSLConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();

    currencyPairs.addAll(BTCEAdapters.adaptCurrencyPairs(btce.getInfo().getPairs().keySet()));

    return currencyPairs;
  }

  protected void checkResult(BTCEReturn<?> result) {
    String error = result.getError();

    if (!result.isSuccess()) {
      if (error != null) {
        if (error.startsWith(ERR_MSG_NONCE)) {
          throw new NonceException(error);
        } else if (error.startsWith(ERR_MSG_FUNDS)) {
          throw new FundsExceededException(error);
        }
      }
      throw new ExchangeException(error);
    }

    else if (result.getReturnValue() == null) {
      throw new ExchangeException("Didn't receive any return value. Message: " + error);
    }

    else if (error != null) {
      throw new ExchangeException(error);
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
                return exchange.getExchangeSpecification().getHost().equals(s);
            }
        });
        return config;
    }
}

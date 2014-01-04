package com.xeiam.xchange;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AuthHelper {

  public static void trustAllCerts() throws Exception {

    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

      @Override
      public java.security.cert.X509Certificate[] getAcceptedIssuers() {

        return null;
      }

      @Override
      public void checkClientTrusted(X509Certificate[] certs, String authType) {

      }

      @Override
      public void checkServerTrusted(X509Certificate[] certs, String authType) {

      }
    } };

    // Install the all-trusting trust manager
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {

      @Override
      public boolean verify(String hostname, SSLSession session) {

        return true;
      }
    };

    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

  }
}
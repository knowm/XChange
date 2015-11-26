package com.xeiam.xchange.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/**
 * For servers which use a certificate differing from their hostname.
 */
public class IncorrectHostnameVerifier implements HostnameVerifier {

  private final HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

  private final String hostname;
  private final String principalName;

  /**
   * Constructor
   *
   * @param hostname hostname used to access the service
   * @param principalName RFC 2253 name on the certificate
   */
  public IncorrectHostnameVerifier(String hostname, String principalName) {

    this.hostname = hostname;
    this.principalName = principalName;
  }

  @Override
  public boolean verify(String hostname, SSLSession session) {

    try {

      String principalName = session.getPeerPrincipal().getName();

      if (hostname.equals(this.hostname) && principalName.equals(this.principalName))
        return true;

    } catch (SSLPeerUnverifiedException e) { }

    return defaultHostnameVerifier.verify(hostname, session);
  }
}

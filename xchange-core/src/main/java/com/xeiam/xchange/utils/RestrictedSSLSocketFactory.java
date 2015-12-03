package com.xeiam.xchange.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * An SSLSocketFactory that may disable some protocols or ciphers in the generated sockets
 */
public class RestrictedSSLSocketFactory extends SSLSocketFactory {

  private final SSLSocketFactory defaultSSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

  private final Collection<String> disabledProtocols;
  private final Collection<String> disabledCiphers;

  /**
   * Constructor
   *
   * @param disabledProtocols If not null, SSL protocols that will be disabled
   * @param disabledCiphers If not null, SSL ciphersuites that will be disabled
   */
  public RestrictedSSLSocketFactory(String[] disabledProtocols, String[] disabledCiphers) {

    if (disabledProtocols != null)
      this.disabledProtocols = new CopyOnWriteArraySet<String>(Arrays.asList(disabledProtocols));
    else
      this.disabledProtocols = null;

    if (disabledCiphers != null)
      this.disabledCiphers = new CopyOnWriteArraySet<String>(Arrays.asList(disabledCiphers));
    else
      this.disabledCiphers = null;
  }

  private static String[] filter(Collection<String> disabled, String[] original, String[] supported) throws IOException {

    Set<String> filtered = new CopyOnWriteArraySet<String>(Arrays.asList(original));
    filtered.removeAll(disabled);

    if (filtered.isEmpty()) {
      filtered.addAll(Arrays.asList(supported));
      filtered.removeAll(disabled);
    }

    if (filtered.isEmpty())
      throw new IOException("No supported SSL attributed enabled.  " + Arrays.toString(original) + " provided, "
          + disabled.toString() + " disabled, " + Arrays.toString(supported) + " supported, result: " + filtered.toString());

    return filtered.toArray(new String[filtered.size()]);
  }

  private SSLSocket fixupSocket(Socket socket) throws IOException {

    SSLSocket sslSocket = (SSLSocket) socket;

    if (disabledProtocols != null && !disabledProtocols.isEmpty())
      sslSocket.setEnabledProtocols(filter(disabledProtocols, sslSocket.getEnabledProtocols(), sslSocket.getSupportedProtocols()));

    if (disabledCiphers != null && !disabledCiphers.isEmpty())
      sslSocket.setEnabledCipherSuites(filter(disabledCiphers, sslSocket.getEnabledCipherSuites(), sslSocket.getSupportedCipherSuites()));

    return sslSocket;
  }

  @Override public String[] getDefaultCipherSuites() {
    return defaultSSLSocketFactory.getDefaultCipherSuites();
  }

  @Override public String[] getSupportedCipherSuites() {
    return defaultSSLSocketFactory.getSupportedCipherSuites();
  }

  @Override public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
    return fixupSocket(defaultSSLSocketFactory.createSocket(s, host, port, autoClose));
  }

  @Override public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
    return fixupSocket(defaultSSLSocketFactory.createSocket(host, port));
  }

  @Override public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
    return fixupSocket(defaultSSLSocketFactory.createSocket(host, port, localHost, localPort));
  }

  @Override public Socket createSocket(InetAddress host, int port) throws IOException {
    return fixupSocket(defaultSSLSocketFactory.createSocket(host, port));
  }

  @Override public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
    return fixupSocket(defaultSSLSocketFactory.createSocket(address, port, localAddress, localPort));
  }
}

package org.knowm.xchange.exx.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/** 模拟httpPost方式发送数据 */
public class RestHttpClient {
  private static final String HTTPS_PREFIX = "https";

  private static MyX509TrustManager xtm = new MyX509TrustManager();
  private static MyHostnameVerifier hnv = new MyHostnameVerifier();

  /**
   * 获取response String类型响应数据
   *
   * @param is
   * @param charset
   * @return
   * @throws IOException
   */
  private static String getResponseStringData(InputStream is, String charset) throws IOException {
    ByteArrayOutputStream respDataStream = new ByteArrayOutputStream();
    String result;
    try {
      byte[] b = new byte[8192];
      int len;
      while (true) {
        len = is.read(b);
        if (len <= 0) {
          is.close();
          break;
        }
        respDataStream.write(b, 0, len);
      }
      result = respDataStream.toString(charset);
    } finally {
      respDataStream.close();
    }
    return result;
  }

  /**
   * 发送http请求，支持http和https方式
   *
   * @param serverUrl 请求地址
   * @param realData 需要发送的数据
   * @return
   */
  public static String post(
      String serverUrl, String realData, int connectTimeout, int readTimeout, String charset)
      throws NoSuchAlgorithmException, KeyManagementException, IOException {
    if (serverUrl == null || "".equals(serverUrl.trim())) {
      throw new NullPointerException("请求地址为空!");
    }
    if (realData == null || "".equals(realData.trim())) {
      throw new NullPointerException("请求数据为空!");
    }
    URLConnection conn;
    // 分别调用http或https请求
    if (serverUrl.startsWith(HTTPS_PREFIX)) {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      X509TrustManager[] xtmArray = new X509TrustManager[] {xtm};
      sslContext.init(null, xtmArray, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
      HttpsURLConnection.setDefaultHostnameVerifier(hnv);
      HttpsURLConnection httpsUrlConn = (HttpsURLConnection) (new URL(serverUrl)).openConnection();
      httpsUrlConn.setRequestMethod("POST");
      conn = httpsUrlConn;
    } else {
      URL url = new URL(serverUrl);
      conn = url.openConnection();
    }
    conn.setConnectTimeout(connectTimeout * 1000);
    conn.setReadTimeout(readTimeout * 1000);
    conn.setRequestProperty("Content-Type", "application/json;charset=" + charset);

    conn.setDoOutput(true);
    conn.setDoInput(true);

    OutputStream out = conn.getOutputStream();
    try {
      out.write(realData.getBytes(charset));
      out.flush();
    } finally {
      out.close();
    }

    InputStream is = conn.getInputStream();
    // 返回收到的响应数据
    return getResponseStringData(is, charset);
  }
}

class MyX509TrustManager implements X509TrustManager {

  public void checkClientTrusted(X509Certificate[] chain, String authType) {}

  public void checkServerTrusted(X509Certificate[] chain, String authType) {}

  public X509Certificate[] getAcceptedIssuers() {
    return null;
  }
}

class MyHostnameVerifier implements HostnameVerifier {

  public boolean verify(String hostname, SSLSession session) {
    return true;
  }
}

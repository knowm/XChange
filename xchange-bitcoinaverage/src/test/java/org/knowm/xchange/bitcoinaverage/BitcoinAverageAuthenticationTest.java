package org.knowm.xchange.bitcoinaverage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assume;
import org.junit.Test;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

public class BitcoinAverageAuthenticationTest {

  private static final String BITCOINAVERAGE_SECRET_KEY = "BITCOINAVERAGE_SECRET_KEY";
  private static final String BITCOINAVERAGE_PUBLIC_KEY = "BITCOINAVERAGE_PUBLIC_KEY";

  @Test
  public void testTickerAdapter() throws Exception {

    String secretKey = System.getenv(BITCOINAVERAGE_SECRET_KEY);
    String publicKey = System.getenv(BITCOINAVERAGE_PUBLIC_KEY);
    Assume.assumeTrue(secretKey != null && publicKey != null);

    String signature = getSignature(secretKey, publicKey);

    String url = "https://apiv2.bitcoinaverage.com/indices/global/ticker/all";
    URL urlObj = new URL(url);
    HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("X-Signature", signature);

    // read all the lines of the response into response StringBuffer
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = bufferedReader.readLine()) != null) {
      response.append(inputLine);
    }
    bufferedReader.close();

    // if you don't want to use Gson, you can just print the plain response
    System.out.println(response.toString());
  }

  private String getSignature(String secretKey, String publicKey) throws Exception {

    long timestamp = System.currentTimeMillis() / 1000L;
    String payload = timestamp + "." + publicKey;

    Mac sha256_Mac = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    sha256_Mac.init(secretKeySpec);
    String hashHex =  bytesToHex(sha256_Mac.doFinal(payload.getBytes())).toLowerCase();
    String signature = payload + "." + hashHex;
    return signature;
  }

}

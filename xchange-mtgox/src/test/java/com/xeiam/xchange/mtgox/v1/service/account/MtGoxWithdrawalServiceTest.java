package com.xeiam.xchange.mtgox.v1.service.account;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.utils.HttpURLConnectionUtils;

public class MtGoxWithdrawalServiceTest {

  // TODO Implement this
  @Test
  public void testWithdraw_ExpectSuccess() throws Exception {

    // TODO Provide a framework to configure the withdrawal request HttpURLConnection

    // Configure to use the example JSON objects
    final HttpURLConnection mockHttpURLConnection = HttpURLConnectionUtils.configureMockHttpURLConnectionForGet("/marketdata/example-ticker.json");

    // Provide a mocked out HttpURLConnection
    MtGoxWithdrawalRequest withdrawalRequest = new MtGoxWithdrawalRequest() {

      HttpURLConnection getHttpURLConnection(String urlString) throws IOException {

        return mockHttpURLConnection;
      }
    };

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> httpHeaders = new HashMap<String, String>();

    // MtGoxPollingAccountService testObject = new MtGoxPollingAccountService();

    // TODO Flesh this out

    /*
     * WithdrawalResponse actualResponse = testObject.newWithdrawalRequest() .withMoney(MoneyUtils.parseFiat("USD 10.50")) .withdraw(); assertEquals(actualResponse.get)
     */

  }
}

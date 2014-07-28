/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.anx.v2.account.polling;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWithdrawalResponse;

/**
 * Test ANXWithdrawalResponse JSON parsing
 */
public class WithdrawalResponseJSONTest {

  @Ignore
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WithdrawalResponseJSONTest.class.getResourceAsStream("/v2/account/example-withdrawal-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    ANXWithdrawalResponse anxWithdrawalResponse = mapper.readValue(is, ANXWithdrawalResponse.class);

    System.out.println(anxWithdrawalResponse.toString());

    // Verify that the example data was unmarshalled correctly
    // assertThat(anxWithdrawalResponse.getTransactionId()).isEqualTo("9921d2c5abecfd3604e921888b32e48256c914156cc76c4c8eca1ad2709b48e6");
  }
}
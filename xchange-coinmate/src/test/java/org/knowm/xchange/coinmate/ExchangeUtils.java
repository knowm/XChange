/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
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
package org.knowm.xchange.coinmate;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeUtils {

  private static final Logger logger = LoggerFactory.getLogger(ExchangeUtils.class);

  /**
   * Create a Coinmate exchange using the keys provided in a exchangeConfiguration.json file on the
   * classpath. See the sampleExchangeConfiguration.json file for format of required file.
   *
   * @return Create exchange or null if .json file was not on classpath.
   */
  public static Exchange createExchangeFromJsonConfiguration() {

    ExchangeSpecification exSpec = new ExchangeSpecification(CoinmateExchange.class);
    ObjectMapper mapper = new ObjectMapper();
    InputStream is =
        ExchangeUtils.class.getClassLoader().getResourceAsStream("exchangeConfiguration.json");

    if (is == null) {
      logger.warn("No exchangeConfiguration.json file found. Returning null exchange.");
      return null;
    }
    try {
      ExchangeConfiguration conf = mapper.readValue(is, ExchangeConfiguration.class);
      logger.debug(conf.toString());

      if (conf.publicApiKey != null) {
        exSpec.setApiKey(conf.publicApiKey);
      }
      if (conf.privateApiKey != null) {
        exSpec.setSecretKey(conf.privateApiKey);
      }
      if (conf.clientId != null) {
        exSpec.setUserName(conf.clientId);
      }
    } catch (Exception e) {
      logger.warn(
          "An exception occured while loading the exchangeConfiguration.json file from the classpath. "
              + "Returning null exchange.",
          e);
      return null;
    }

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}

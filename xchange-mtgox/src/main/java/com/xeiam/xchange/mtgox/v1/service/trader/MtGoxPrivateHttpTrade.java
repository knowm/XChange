/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v1.service.trader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CachedDataSession;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.HttpException;
import com.xeiam.xchange.dto.trade.AccountInfo;
import com.xeiam.xchange.dto.trade.SynchronousTrade;
import com.xeiam.xchange.mtgox.v1.MtGoxProperties;
import com.xeiam.xchange.utils.CryptoUtils;
import com.xeiam.xchange.utils.HttpUtils;

public class MtGoxPrivateHttpTrade extends SynchronousTrade implements CachedDataSession {

  /**
   * Provides logging for this class
   */
  private static final Logger log = LoggerFactory.getLogger(MtGoxPrivateHttpTrade.class);

  @Override
  public AccountInfo getExchangeAccountInfo(String key, String secret) {

    try {
      // request data
      // String url = "https://mtgox.com/api/0/info.php"; // version 0
      String url = "https://mtgox.com/api/1/generic/private/info"; // version 1

      // String postBody = URLEncoder.encode("nonce", HttpUtils.CHARSET_UTF_8) + "=" + URLEncoder.encode("1345634563", HttpUtils.CHARSET_UTF_8);
      String postBody = "nonce=" + URLEncoder.encode(CryptoUtils.getNumericalNonce(), HttpUtils.CHARSET_UTF_8);
      log.debug("postBody= " + postBody);

      Map<String, String> headerKeyValues = new HashMap<String, String>();
      log.debug("Rest-Key= " + key);
      log.debug("encoded Rest-Key= " + URLEncoder.encode(key, HttpUtils.CHARSET_UTF_8));
      headerKeyValues.put("Rest-Key", URLEncoder.encode(key, HttpUtils.CHARSET_UTF_8));
      // headerKeyValues.put("Rest-Key", key);
      // headerKeyValues.put("Rest-Sign", CryptoUtils.computeSignature("HmacSHA512", postBody, CryptoUtils.getBase64DecodedString(secret)));
      log.debug("Rest-Sign= " + CryptoUtils.computeSignature("HmacSHA512", postBody, secret));
      log.debug("encoded Rest-Sign= " + URLEncoder.encode(CryptoUtils.computeSignature("HmacSHA512", postBody, secret), HttpUtils.CHARSET_UTF_8));
      // headerKeyValues.put("Rest-Sign", CryptoUtils.computeSignature("HmacSHA512", postBody, secret.getBytes()));
      headerKeyValues.put("Rest-Sign", URLEncoder.encode(CryptoUtils.computeSignature("HmacSHA512", postBody, secret), HttpUtils.CHARSET_UTF_8));
      String accountInfoJSON = HttpUtils.getJSON(url, postBody, headerKeyValues);

      log.debug(accountInfoJSON);
    } catch (GeneralSecurityException e) {
      throw new ExchangeException("Problem generating secure HTTP request (General Security)", e);
    } catch (UnsupportedEncodingException e) {
      throw new ExchangeException("Problem generating secure HTTP request  (Unsupported Encoding)", e);
    } catch (HttpException e) {
      throw new ExchangeException("Problem getting server response (Http error)", e);
    } catch (IOException e) {
      throw new ExchangeException("Problem generating Account Info (IO)", e);
    } catch (NumberFormatException e) {
      throw new ExchangeException("Problem generating Account Info (number formatting)", e);
    }
    return null;
  }

  /**
   * <p>
   * According to Mt.Gox API docs (https://en.bitcoin.it/wiki/MtGox/API), data is cached for 10 seconds.
   * </p>
   */
  @Override
  public int getRefreshRate() {
    return MtGoxProperties.REFRESH_RATE;
  }

}

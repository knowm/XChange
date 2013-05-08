/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v2.service.trade.streaming;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.joda.money.BigMoney;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.utils.Base64;

/**
 * @author timmolter
 */
public class SocketMsgFactory {

  private final String apiKey;
  private final String apiSecret;

  /**
   * Constructor
   * 
   * @param apiKey
   * @param apiSecret
   */
  public SocketMsgFactory(String apiKey, String apiSecret) {

    if (apiKey == null || apiSecret == null || apiKey.length() == 0 || apiSecret.length() == 0) {
      throw new IllegalArgumentException("mtgox api key and/or secret is missing");
    }

    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  public String unsubscribeToChannel(String channel) throws JsonProcessingException {

    HashMap<String, String> map = new HashMap<String, String>(2);
    map.put("op", "unsubscribe");
    map.put("channel", channel);

    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(map);

  }

  public String subscribeWithChannel(String channel) throws JsonProcessingException {

    HashMap<String, String> map = new HashMap<String, String>(2);
    map.put("op", "mtgox.subscribe");
    map.put("channel", channel);

    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(map);
  }

  public String subscribeWithType(String type) throws JsonProcessingException {

    HashMap<String, String> map = new HashMap<String, String>(2);
    map.put("op", "mtgox.subscribe");
    map.put("type", type);

    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(map);
  }

  public String subscribeWithKey(String key) throws JsonProcessingException {

    HashMap<String, String> map = new HashMap<String, String>(2);
    map.put("op", "mtgox.subscribe");
    map.put("key", key);

    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(map);
  }

  public String cancelOrder(String oid) throws UnsupportedEncodingException, JsonProcessingException {

    HashMap<String, String> params = new HashMap<String, String>(1);
    params.put("oid", oid);

    String reqId = String.format("order_cancel:%s", oid);
    return signedCall("order/cancel", params, reqId);
  }

  public String addOrder(Order.OrderType orderType, BigMoney price, BigDecimal amount) throws UnsupportedEncodingException, JsonProcessingException {

    String typeStr = Order.OrderType.ASK == orderType ? "ask" : "bid";
    String priceStr = MtGoxUtils.getPriceString(price);
    String amountStr = MtGoxUtils.getAmountString(amount);

    HashMap<String, String> params = new HashMap<String, String>(3);
    params.put("type", typeStr);
    params.put("amount_int", amountStr);

    // if price <= 0, it's a market order
    if (price.isGreaterThan(BigMoney.zero(price.getCurrencyUnit()))) {
      params.put("price_int", priceStr);
    }

    String reqId = String.format("order_add:%s:%s:%s", typeStr, priceStr, amountStr);
    return signedCall("order/add", params, reqId);
  }

  public String idKey() throws JsonProcessingException, UnsupportedEncodingException {

    return signedCall("private/idkey", new HashMap<String, String>(), "idkey");
  }

  public String privateOrders() throws UnsupportedEncodingException, JsonProcessingException {

    return signedCall("private/orders", new HashMap<String, String>(), "orders");
  }

  public String privateInfo() throws UnsupportedEncodingException, JsonProcessingException {

    return signedCall("private/info", new HashMap<String, String>(), "info");
  }

  private String signedCall(String endPoint, Map<String, String> params, String reqId) throws JsonProcessingException, UnsupportedEncodingException {

    long nonce = MtGoxUtils.getNonce();

    HashMap<String, Object> call = new HashMap<String, Object>(6);
    call.put("id", reqId);
    call.put("call", endPoint);
    call.put("nonce", nonce);
    call.put("params", params);
    call.put("currency", "USD");
    call.put("item", "BTC");

    ObjectMapper mapper = new ObjectMapper();
    String callString = mapper.writeValueAsString(call);
    String signedCall = null;

    try {
      byte[] bsecret = Base64.decode(this.apiSecret);
      SecretKeySpec spec = new SecretKeySpec(bsecret, "HmacSHA512");
      Mac mac = Mac.getInstance("HmacSHA512");
      mac.init(spec);

      byte[] bsig = mac.doFinal(callString.getBytes());
      byte[] keyB = fromHexString(this.apiKey.replaceAll("-", ""));
      byte[] callB = callString.getBytes();

      byte[] c = new byte[bsig.length + keyB.length + callB.length];
      System.arraycopy(keyB, 0, c, 0, keyB.length);
      System.arraycopy(bsig, 0, c, keyB.length, bsig.length);
      System.arraycopy(callB, 0, c, keyB.length + bsig.length, callB.length);

      signedCall = Base64.encodeBytes(c);

    } catch (Exception e) {
      System.out.println("e!: " + e);

    }

    HashMap<String, String> msg = new HashMap<String, String>(4);
    msg.put("op", "call");
    msg.put("call", signedCall);
    msg.put("id", reqId);
    msg.put("context", "mtgox.com");

    mapper = new ObjectMapper();
    return mapper.writeValueAsString(msg);
  }

  private static byte[] fromHexString(String hex) {

    ByteArrayOutputStream bas = new ByteArrayOutputStream();
    for (int i = 0; i < hex.length(); i += 2) {
      int b = Integer.parseInt(hex.substring(i, i + 2), 16);
      bas.write(b);
    }

    return bas.toByteArray();
  }

}

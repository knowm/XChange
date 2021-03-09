package org.knowm.xchange.coindcx.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.Date;
import javax.crypto.Mac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.coindcx.Coindcx;
import org.knowm.xchange.coindcx.dto.CoindcxNewOrderRequest;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoindcxHmacPostBodyDigest extends BaseParamsDigest {

  ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private CoindcxHmacPostBodyDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static CoindcxHmacPostBodyDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new CoindcxHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    Mac mac = getMac();
    mac.update(Base64.getEncoder().encodeToString(postBody.getBytes()).getBytes());

    return String.format("%096x", new BigInteger(1, mac.doFinal()));
  }

  public String digestParams(Object body) {
    Mac mac256 = getMac();
    System.out.println("mac256");
    System.out.println(mac256.toString());
    String mainBody = "";
    if (!ObjectUtils.isEmpty(body)) {
      try {
          mainBody = objectMapper.writeValueAsString(body);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    System.out.println(mainBody);
    try {
      mac256.update(mainBody.getBytes());
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
    byte[] arrayOfByte = mac256.doFinal();
    BigInteger localBigInteger = new BigInteger(1, arrayOfByte);

    String finalValue =
            String.format("%0" + (arrayOfByte.length << 1) + "x", new Object[] {localBigInteger});

    System.out.println("Body : " + body);
    System.out.println("Digest is    " + finalValue);
    return finalValue;
  }

  public static void main(String[] args) throws JsonProcessingException {
    CoindcxNewOrderRequest coindcxNewOrderRequest=new CoindcxNewOrderRequest("buy","market_order","BTCINR",BigDecimal.ZERO,new BigDecimal(500),1614950680019L);
    CoindcxHmacPostBodyDigest coindcxHmacPostBodyDigest=new CoindcxHmacPostBodyDigest("431a7a0971aac1cf6db49821f618fc3d84e564128dd0157736895e331ab1e418");
    String value=coindcxHmacPostBodyDigest.digestParams(coindcxNewOrderRequest);
    System.out.println(value);
  }
}



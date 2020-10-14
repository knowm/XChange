package org.knowm.xchange.bitbns.dto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.HeaderParam;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class BitbnsHmacPostBodyDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BitbnsHmacPostBodyDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BitbnsHmacPostBodyDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new BitbnsHmacPostBodyDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
	
    System.out.println(restInvocation.getParamsMap());
    si.mazi.rescu.Params params=restInvocation.getParamsMap().get(HeaderParam.class);
    Object payload=params.getParamValue("X-BITBNS-PAYLOAD");
    Mac mac = getMac();
//    mac.update(payload.toString().getBytes());
    String signature=convert(payload.toString());
	System.out.println("-------------------------------------------------------");
	System.out.println(signature);
	System.out.println("-------------------------------------------------------");

    return signature;
  }
  
  public String convert(String payload){
	  Mac sha512Hmac;
      String result=null;
      final String key = "93D78DC61ABC3635093EBFF4B52BCB61";

      try {
          final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
          sha512Hmac = Mac.getInstance(HMAC_SHA_512);
          SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA_512);
          sha512Hmac.init(keySpec);
          byte[] macData = sha512Hmac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

          // Can either base64 encode or put it right into hex
//          result = Base64.getEncoder().encodeToString(macData);
          result = bytesToHex(macData);
      } catch (InvalidKeyException | NoSuchAlgorithmException e) {
          e.printStackTrace();
      } finally {
          // Put any cleanup here
          System.out.println("Done");
      }
      return result;
  }
  
  private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);
  
  public static String bytesToHex(byte[] bytes) {
	    byte[] hexChars = new byte[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars, StandardCharsets.UTF_8);
	}
  
}

package org.knowm.xchange.getbtc.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import com.fasterxml.jackson.databind.ObjectMapper;

/** */
public class RestSignUtil {
	  public static String  getHmacSHA256(Map params, String secretKey) throws Exception {
		  String data = new ObjectMapper().writeValueAsString(params);
		  String signature = null;
			try {
	          Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		      SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
		      sha256_HMAC.init(secret_key);
	 
		      byte[] bytes = sha256_HMAC.doFinal(data.getBytes());
		      StringBuffer signatureData = new StringBuffer();
		      String temp = "";
		      for (byte b : bytes) {
		        temp = Integer.toHexString(b & 0XFF);
		        signatureData.append(temp.length() == 1 ? "0" + temp : temp);
		      }	      
		      signature = signatureData.toString();
			} catch (NoSuchAlgorithmException | InvalidKeyException e) {			
				e.printStackTrace();
			}
			return signature;
	  } 
}

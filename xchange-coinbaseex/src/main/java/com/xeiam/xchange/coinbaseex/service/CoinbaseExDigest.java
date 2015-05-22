package com.xeiam.xchange.coinbaseex.service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.HeaderParam;
import javax.xml.bind.DatatypeConverter;

import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.utils.Base64;

import com.xeiam.xchange.service.BaseParamsDigest;
import com.xeiam.xchange.utils.DigestUtils;


public class CoinbaseExDigest extends BaseParamsDigest {

	private CoinbaseExDigest(String secretKey) {

		super(secretKey, HMAC_SHA_256);
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	public static CoinbaseExDigest createInstance(String secretKey) {

		byte[] decode2 = null;
		byte[] printBase64Binary = null;
		try {
			printBase64Binary = DatatypeConverter.parseBase64Binary(secretKey);
			
			decode2 = Base64.decode(secretKey);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return secretKey == null ? null : new CoinbaseExDigest(new String(decode2));
//		return secretKey == null ? null : new CoinbaseExDigest(secretKey);
	}
//	
//    public static String generateSignature(String secret_key, String timestamp, String method, String endpoint_url, String body) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
//        String prehash = timestamp + method.toUpperCase() + endpoint_url + body;
//        byte[] secretDecoded = Base64.getDecoder().decode(secret_key);
//
//     
//    }

	@Override
	public String digestParams(RestInvocation restInvocation) {

		String message =   
				restInvocation.getParamValue(HeaderParam.class, "CB-ACCESS-TIMESTAMP").toString() +
				restInvocation.getHttpMethod() + 
				"/" + restInvocation.getMethodPath();
		
		if(restInvocation.getRequestBody() != null) {
			message += restInvocation.getRequestBody();
		}
		
		message = "1GET/accounts";

		System.out.println("Message: " + message);

		Mac mac256 = getMac();
		mac256.update(message.getBytes());

		
//		return DatatypeConverter.printBase64Binary(mac256.doFinal(message.getBytes()));
		return Base64.encodeBytes(new BigInteger(mac256.doFinal()).toByteArray());
//		return Base64.encodeBytes(new BigInteger(1, mac256.doFinal()).toByteArray());
//		return Base64.encodeBytes((new BigInteger(mac256.doFinal())).toByteArray());
//		return String.format("%064x", new BigInteger(1, mac256.doFinal()));
	}
}
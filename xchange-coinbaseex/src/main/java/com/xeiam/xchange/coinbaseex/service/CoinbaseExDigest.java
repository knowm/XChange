package com.xeiam.xchange.coinbaseex.service;

import java.io.IOException;
import java.math.BigInteger;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.utils.Base64;

import com.xeiam.xchange.service.BaseParamsDigest;


public class CoinbaseExDigest extends BaseParamsDigest {

	private CoinbaseExDigest(String secretKey) {

		super(secretKey, HMAC_SHA_256);
	}

	public static CoinbaseExDigest createInstance(String secretKey) {

		byte[] decodedSecretKey = null;
		try {			
			decodedSecretKey = Base64.decode(secretKey);
		} catch (IOException e) {
			throw new RuntimeException("Cannot decode secret key");
		}

		return secretKey == null ? null : new CoinbaseExDigest(new String(decodedSecretKey));
	}

	@Override
	public String digestParams(RestInvocation restInvocation) {

		String message =   
				restInvocation.getParamValue(HeaderParam.class, "CB-ACCESS-TIMESTAMP").toString() +
				restInvocation.getHttpMethod() + 
				"/" + restInvocation.getMethodPath();

		if(restInvocation.getRequestBody() != null) {
			message += restInvocation.getRequestBody();
		}

		//		message = "1GET/accounts";

		Mac mac256 = getMac();
		mac256.update(message.getBytes());

		return Base64.encodeBytes(new BigInteger(1, mac256.doFinal()).toByteArray());
	}
}
package com.xeiam.xchange.coinbaseex.service;

import java.io.IOException;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.utils.Base64;

import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.BaseParamsDigest;


public class CoinbaseExDigest extends BaseParamsDigest {

	private CoinbaseExDigest(byte[] secretKey) {

		super(secretKey, HMAC_SHA_256);
	}

	public static CoinbaseExDigest createInstance(String secretKey) {
		if (secretKey == null) {
			return null;
		}
		try {
			return new CoinbaseExDigest(Base64.decode(secretKey));
		} catch (IOException e) {
			throw new ExchangeException("Cannot decode secret key");
		}
	}

	@Override
	public String digestParams(RestInvocation restInvocation) {

		String message =   
				restInvocation.getParamValue(HeaderParam.class, "CB-ACCESS-TIMESTAMP").toString() +
				restInvocation.getHttpMethod() + 
				"/" + restInvocation.getMethodPath() + (restInvocation.getRequestBody() != null ? restInvocation.getRequestBody() : "");

		Mac mac256 = getMac();
		
		try {
			mac256.update(message.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new ExchangeException("Digest encoding exception", e);
		}

		return Base64.encodeBytes(mac256.doFinal());
	}
}
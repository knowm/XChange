/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.itbit.v1.service;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.utils.Base64;


public class ItBitHmacPostBodyDigest implements ParamsDigest {
	private static final String FIELD_SEPARATOR = "\",\"";
	private static final String HMAC_SHA_512 = "HmacSHA512";

	private final Mac mac512;
	private final MessageDigest md;

	private final String apiKey;

	/**
	 * Constructor
	 * 
	 * @param secretKeyBase64
	 * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
	 */
	private ItBitHmacPostBodyDigest(String apiKey, String secretKeyBase64) throws IllegalArgumentException {
		this.apiKey = apiKey;

		try {
			SecretKey secretKey512 = new SecretKeySpec(secretKeyBase64.getBytes()/*.getBytes("UTF-8")*/, HMAC_SHA_512);
			mac512 = Mac.getInstance(HMAC_SHA_512);
			mac512.init(secretKey512);

			md = MessageDigest.getInstance("SHA-256");

		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
		}
	}

	public static ItBitHmacPostBodyDigest createInstance(String apiKey, String secretKeyBase64) throws IllegalArgumentException {
		return secretKeyBase64 == null ? null : new ItBitHmacPostBodyDigest(apiKey, secretKeyBase64);
	}


	@Override
	public String digestParams(RestInvocation RestInvocation) {
		md.reset();
		
		Map<String, String> httpHeaders = RestInvocation.getHttpHeaders();
		String currentNonce = httpHeaders.get("X-Auth-Nonce");
		String currentTimestamp = httpHeaders.get("X-Auth-Timestamp");
		
		// only POST requests will have a non-null request body.
		String requestBody = RestInvocation.getRequestBody();
		if(requestBody == null) {
			requestBody = "";
		} else {
			requestBody = requestBody.replace("\"", "\\\"");
		}
		
		String verb = RestInvocation.getHttpMethod().trim();
		String invocationUrl = RestInvocation.getInvocationUrl().trim();
		String message = new StringBuilder("[\"")
				   .append(verb).append(FIELD_SEPARATOR)
		           .append(invocationUrl).append(FIELD_SEPARATOR)
		           .append(requestBody).append(FIELD_SEPARATOR)
		           .append(currentNonce).append(FIELD_SEPARATOR)
		           .append(currentTimestamp).append("\"]").toString();
		
		md.update((currentNonce + message).getBytes());
		BigInteger hash = new BigInteger( md.digest() );

		mac512.update(invocationUrl.getBytes());
		mac512.update(hash.toByteArray());

		return apiKey + ":" + Base64.encodeBytes((new BigInteger(mac512.doFinal())).toByteArray());
	}
}

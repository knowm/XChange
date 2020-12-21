package org.knowm.xchange.bitso.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.bitso.dto.trade.BitsoPlaceOrder;
import org.knowm.xchange.service.BaseParamsDigest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import si.mazi.rescu.RestInvocation;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class BitsoDigest extends BaseParamsDigest {

	private final String clientId;

	private final String apiKey;
	ObjectMapper objectMapper = new ObjectMapper();

	private BitsoDigest(String secretKeyHex, String clientId, String apiKey) {
		super(secretKeyHex, HMAC_SHA_256);
		this.clientId = clientId;
		this.apiKey = apiKey;
	}

	public static BitsoDigest createInstance(String secretKey, String userName, String apiKey) {
		return secretKey == null ? null : new BitsoDigest(secretKey, userName, apiKey);
	}

	@Override
	public String digestParams(RestInvocation restInvocation) {
		Mac mac256 = getMac();
		Long nonce = new Date().getTime();
		String signature = nonce + restInvocation.getHttpMethod() + "/" + restInvocation.getPath();
		String body = "";

		if (!restInvocation.getUnannanotatedParams().isEmpty()) {
			for (Object rest : restInvocation.getUnannanotatedParams()) {
				try {
					body = objectMapper.writeValueAsString(rest);
					body.replace("\":\"", "\": \"");
					System.out.println(body);

				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}

		if (!ObjectUtils.isEmpty(body)) {
			signature = signature + body;
		}

		System.out.println("Method : " + restInvocation.getHttpMethod());
		System.out.println("Path : /" + restInvocation.getPath());
		System.out.println("Body : " + restInvocation.getUnannanotatedParams());

		mac256.update(signature.getBytes());
		byte[] arrayOfByte = mac256.doFinal();
		BigInteger localBigInteger = new BigInteger(1, arrayOfByte);

		String finalValue = String.format("%0" + (arrayOfByte.length << 1) + "x", new Object[] { localBigInteger });
		String value = "Bitso " + apiKey + ":" + nonce + ":" + finalValue;

		System.out.println(value);
		return value;
	}

	public String digestParams(String method, String path, Object body) {
		Mac mac256 = getMac();
		Long nonce = new Date().getTime();
		String signature = nonce + method + path;
		String mainBody = "";
		if (!ObjectUtils.isEmpty(body)) {
			try {
				if(body instanceof String){
					mainBody = body.toString()+"/";
				}else{
					mainBody = objectMapper.writeValueAsString(body);
				}
				
				System.out.println(mainBody);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		if (!ObjectUtils.isEmpty(body)) {
			signature = signature + mainBody;
		}
		System.out.println(signature);
		mac256.update(signature.getBytes());
		byte[] arrayOfByte = mac256.doFinal();
		BigInteger localBigInteger = new BigInteger(1, arrayOfByte);

		String finalValue = String.format("%0" + (arrayOfByte.length << 1) + "x", new Object[] { localBigInteger });
		System.out.println("final Value");
		System.out.println(finalValue);
		String value = "Bitso " + apiKey + ":" + nonce + ":" + finalValue;

		System.out.println(value);
		return value;
	}

}

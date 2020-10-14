package org.knowm.xchange.bitbns.dto;

import java.util.Base64;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BitbnsPayloadDigest implements ParamsDigest {

	@Override
	public synchronized String digestParams(RestInvocation restInvocation) {

		String postBody = restInvocation.getRequestBody();
		DigestModel digestModel=new DigestModel();
		digestModel.setBody(postBody);
		digestModel.setTimeStamp_nonce(String.valueOf(new Date().getTime()));
//		digestModel.setTimeStamp_nonce("1602616333000");
		String body=null;
		try {
			body=new ObjectMapper().writeValueAsString(digestModel);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------------------------------");
		System.out.println(Base64.getEncoder().encodeToString(body.getBytes()));
		System.out.println("-------------------------------------------------------");
		
		return Base64.getEncoder().encodeToString(body.getBytes());
	}
}


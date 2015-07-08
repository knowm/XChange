package com.xeiam.xchange.gatecoin.service;
import java.math.BigInteger;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import org.apache.commons.codec.binary.Base64;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;
import java.util.Date;



/**
 * @author sumedha
 */
public class GatecoinDigest extends BaseParamsDigest {

    private final String apiKey;
    private final long now;
  /**
   * Constructor
   * 
   * @param secretKeyBase64
   * @param clientId
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private GatecoinDigest(String secretKeyBase64, String apiKey, long timeStamp) {

    super(secretKeyBase64, HMAC_SHA_256);   
    this.apiKey = apiKey;
    this.now = timeStamp;
  }

  public static GatecoinDigest createInstance(String secretKeyBase64, String apiKey,long timeStamp) {

    return secretKeyBase64 == null ? null : new GatecoinDigest(secretKeyBase64, apiKey, timeStamp);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String contentType;
  
    String url = restInvocation.getInvocationUrl();
    String type = restInvocation.getHttpMethod();
    contentType = restInvocation.getReqContentType();
    if(type == "POST")
    {
         contentType = "application/x-www-form-urlencoded";
    }
    else
    {
        contentType = "";
    } 

    String message = type + url + contentType + now;   
    Mac mac256 = getMac();      
    String enc=  Base64.encodeBase64String(mac256.doFinal(message.toLowerCase().getBytes()));     
    return enc;
  }
}
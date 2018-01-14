package org.xchange.coinegg.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.FormParam;

import org.apache.commons.codec.binary.Hex;
import org.xchange.coinegg.CoinEggUtils;

import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoinEggDigest implements ParamsDigest {

  private final String privateKey;
  
  protected CoinEggDigest(String privateKey) {
    this.privateKey = privateKey;
  }
  
  @Override
  public String digestParams(RestInvocation restInvocation) {
    
    Params params = Params.of();
    restInvocation.getParamsMap()
                  .get(FormParam.class)
                  .asHttpHeaders()
                  .entrySet()
                  .stream()
                  .filter(e -> !e.getKey().equalsIgnoreCase("signature"))
                  .forEach(e -> params.add(e.getKey(), e.getValue()));
    
   String queryString = params.asQueryString();
    
   try {
    return createSignature(privateKey, queryString);
   } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
    e.printStackTrace();
   }
   
   return null;
  }
   
  private String createSignature(String key, String data) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
    // Create MD5 Hash Of Private Key
    MessageDigest md5Digest = MessageDigest.getInstance("MD5");
    String md5Key = CoinEggUtils.toHexString(md5Digest.digest(key.getBytes("UTF-8")));
    
    // Create HMAC_SHA256 Instance
    SecretKeySpec secretKey = new SecretKeySpec(md5Key.getBytes("UTF-8"), "HmacSHA256");
    Mac sha256HMAC = Mac.getInstance("HmacSHA256");
    sha256HMAC.init(secretKey);
  
    // Create Signature
    return new String(Hex.encodeHex(sha256HMAC.doFinal(data.getBytes("UTF-8"))));
  }
  
  public static CoinEggDigest createInstance(String privateKey) {
    return new CoinEggDigest(privateKey);
  }
}

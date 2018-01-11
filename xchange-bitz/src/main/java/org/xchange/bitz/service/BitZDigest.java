package org.xchange.bitz.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.FormParam;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BitZDigest implements ParamsDigest {

  private final MessageDigest md5;

  public BitZDigest() throws NoSuchAlgorithmException {
    this.md5 = MessageDigest.getInstance("MD5");
  }
  
  // TODO: Fix Current Signing - Rejected By Exchange
  @Override
  public String digestParams(RestInvocation restInvocation) {
    // Get Parameters
    Map<String, String> params = restInvocation.getParamsMap().get(FormParam.class).asHttpHeaders();
    
    // TODO: Find More Elegant Solution To Remove Sign
    // Order By Key Alphabetically, Concancecate Values
    byte[] unsigned = params.entrySet()
                            .stream()
                            .sorted(Map.Entry.<String, String>comparingByKey())
                            .filter(e -> !e.getKey().equalsIgnoreCase("sign"))
                            .map(e -> e.getValue())
                            .collect(Collectors.joining())
                            .getBytes();
    
    // TODO: Determine Charceter Encoding
    return String.valueOf(md5.digest(unsigned));
  }
   
  // TODO: Handle Exception
  public static BitZDigest createInstance() {
    try {
      return new BitZDigest();
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return null;
  }
}

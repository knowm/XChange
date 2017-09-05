package org.knowm.xchange.jubi.service;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

/**
 * Created by Dzf on 2017/7/8.
 */
public class JubiPostBodyDigest extends BaseParamsDigest {

  private final Comparator<Map.Entry<String, String>> comparator = new Comparator<Map.Entry<String, String>>() {
    @Override
    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
      return o1.getKey().compareTo(o2.getKey());
    }
  };

  private JubiPostBodyDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static JubiPostBodyDigest createInstance(String secretKey) {
    try {
      byte[] secretKeyBytes = secretKey.getBytes(Charset.forName("UTF-8"));
      MessageDigest md = MessageDigest.getInstance("MD5");
      String secretKeyMd5Digest = String.format("%x", new BigInteger(1, md.digest(secretKeyBytes)));
      return new JubiPostBodyDigest(secretKeyMd5Digest);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Problem instantiating message digest.");
    }
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Params params = restInvocation.getParamsMap().get(FormParam.class);
    Map<String, String> nameValueMap = params.asHttpHeaders();
    nameValueMap.remove("signature");
    final List<Map.Entry<String, String>> nameValueList = new ArrayList<>(nameValueMap.entrySet());
    Collections.sort(nameValueList, comparator);
    Params newParams = Params.of();
    for (Map.Entry<String, String> param : nameValueList) {
      newParams.add(param.getKey(), param.getValue());
    }
    String message = newParams.asQueryString();
    Mac mac = getMac();
    mac.update(message.getBytes());
    return String.format("%064x", new BigInteger(1, mac.doFinal()));
  }
}
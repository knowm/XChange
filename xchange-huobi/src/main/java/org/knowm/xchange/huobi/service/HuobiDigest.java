package org.knowm.xchange.huobi.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;

import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class HuobiDigest implements ParamsDigest {

  private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  private final String secretKey;
  private final String secretKeyDigestName;
  private final MessageDigest md;
  private final Comparator<Entry<String, String>> comparator = new Comparator<Map.Entry<String, String>>() {

    @Override
    public int compare(Entry<String, String> o1, Entry<String, String> o2) {

      return o1.getKey().compareTo(o2.getKey());
    }
  };

  public HuobiDigest(String secretKey, String secretKeySignatureName) {

    this.secretKey = secretKey;
    this.secretKeyDigestName = secretKeySignatureName;

    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Problem instantiating message digest.");
    }
  }

  private static char[] encodeHex(byte[] data, char[] toDigits) {

    int l = data.length;
    char[] out = new char[l << 1];
    // two characters form the hex value.
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
      out[j++] = toDigits[0x0F & data[i]];
    }
    return out;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    final Params params = restInvocation.getParamsMap().get(FormParam.class);
    final Map<String, String> nameValueMap = params.asHttpHeaders();
    nameValueMap.remove("sign");
    nameValueMap.put(secretKeyDigestName, secretKey);

    final List<Map.Entry<String, String>> nameValueList = new ArrayList<>(nameValueMap.entrySet());
    Collections.sort(nameValueList, comparator);

    final Params newParams = Params.of();
    for (int i = 0; i < nameValueList.size(); i++) {
      Map.Entry<String, String> param = nameValueList.get(i);
      newParams.add(param.getKey(), param.getValue());
    }

    final String message = newParams.asQueryString();

    try {
      md.reset();

      byte[] digest = md.digest(message.getBytes("UTF-8"));

      return String.valueOf(encodeHex(digest, DIGITS_LOWER));
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Codec error", e);
    }
  }
}
package com.xeiam.xchange.huobi.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import com.xeiam.xchange.utils.DigestUtils;
import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.ws.rs.FormParam;
import javax.xml.bind.DatatypeConverter;

/**
 * @author Matija Mazi
 */
public class HuobiDigest implements ParamsDigest {

  private final MessageDigest md5;
  private final String secretKey;
  private static final String SECRET_KEY_PARAM = "secret_key";
  private static final String SIGNATURE_PARAM = "sign";

  private HuobiDigest(String secretKeyBase64) throws IllegalArgumentException {

    try {
        secretKey = secretKeyBase64;
        md5 = MessageDigest.getInstance("MD5");

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }
  }

    public static HuobiDigest createInstance(String secretKeyBase64) throws IllegalArgumentException {

        return secretKeyBase64 == null ? null : new HuobiDigest(secretKeyBase64);
    }

  @Override
  public String digestParams(RestInvocation restInvocation) {

      Params params = restInvocation.getParamsMap().get(FormParam.class);

      Map<String, String> stringStringMap = new TreeMap<String, String>(params.asHttpHeaders());
      stringStringMap.put(SECRET_KEY_PARAM, secretKey);

      StringBuilder sb = new StringBuilder();
      for (String param : stringStringMap.keySet()) {
          if (stringStringMap.get(param) != null && !SIGNATURE_PARAM.equals(param)) {
              if (sb.length() > 0) {
                  sb.append('&');
              }
              sb.append(param).append('=').append(encode(stringStringMap.get(param), true));
          }
      }

      return DigestUtils.bytesToHex(md5.digest(sb.toString().getBytes())).trim().toLowerCase();
  }

    private String encode(String data, boolean encode) {
        try {
            return encode ? URLEncoder.encode(data, "UTF-8") : data;
        } catch (UnsupportedEncodingException var4) {
            throw new RuntimeException("Illegal encoding, fix the code.", var4);
        }
    }
}
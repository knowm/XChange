package org.knowm.xchange.bitcoinde.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class BitcoindeDigest extends BaseParamsDigest {

  private final String apiKey;
//  private final MessageDigest md;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BitcoindeDigest(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
//    try {
//      md = MessageDigest.getInstance("MD5");
//    } catch (NoSuchAlgorithmException e) {
//      throw new RuntimeException("Problem instantiating message digest.");
//    }
  }

  public static BitcoindeDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitcoindeDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    // Step 1: get url-encoded query string (skip)

//    String queryString = restInvocation.getQueryString();
//    System.out.println("queryString = " + queryString);

    // Step 2: concatenate URL with query string

    String completeURL = restInvocation.getInvocationUrl();
//    System.out.println("completeURL = " + completeURL);

    // Step 3: md5-Hash der POST-Parameter für die HMAC-Daten erstellen

    String mD5Post = "d41d8cd98f00b204e9800998ecf8427e"; // no post params for GET methods

    // Step 4: Konkatinieren der HMAC-Eingabedaten
    // http_method+'#'+uri+'#'+api_key+'#'+nonce+'#'+post_parameter_md5_hashed_url_encoded_query_string
    // ex:  'GET#https://api.bitcoin.de/v1/orders?type=buy&amount=5.3&price=255.5#MY_API_KEY#1234567#d41d8cd98f00b204e9800998ecf8427e'

    String nonce = restInvocation.getHttpHeadersFromParams().get("X-API-NONCE");
    String hmac_data = String.format("GET#%s#%s#%s#%s", completeURL, apiKey, nonce, mD5Post);
//    System.out.println("hmac_data = " + hmac_data);

    // Step 5: Bilden des eigentlichen sha256-HMACs

    Mac mac256 = getMac();
    mac256.update(hmac_data.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal()));

  }
}
package com.xeiam.xchange.bitcointoyou.service;

import javax.crypto.Mac;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;
import net.iharder.Base64;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouDigest extends BaseParamsDigest {

  private final String key;
  private final long nonce;

  /**
   * Constructor
   *
   * @param nonce See {@link com.xeiam.xchange.bitcointoyou.BitcoinToYouUtils#getNonce()}
   */
  private BitcoinToYouDigest(String key, String secret, Long nonce) {

    super(secret, HMAC_SHA_256);
    this.key = key;
    this.nonce = nonce;

  }

  public static BitcoinToYouDigest createInstance(String key, String secret, Long nonce) {

    return secret == null ? null : new BitcoinToYouDigest(key, secret, nonce);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac hmac256 = getMac();

    // ACCESS_NONCE + ACCESS_KEY
    String message = this.nonce + this.key;

    hmac256.update(message.getBytes());

    return Base64.encodeBytes(hmac256.doFinal()).toUpperCase();
  }
}
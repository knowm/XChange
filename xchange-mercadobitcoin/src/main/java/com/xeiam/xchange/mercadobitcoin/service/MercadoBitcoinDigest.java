package com.xeiam.xchange.mercadobitcoin.service;

import com.xeiam.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import java.math.BigInteger;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinDigest extends BaseParamsDigest {

  private final String method;
  private final String pin;
  private final String tonce;

  /**
   * Constructor
   *
   * @param signCode (called "Codigo")
   * @param tonce See {@link com.xeiam.xchange.mercadobitcoin.MercadoBitcoinUtils#getTonce()}
   */
  private MercadoBitcoinDigest(String method, String pin, String signCode, String tonce) {

    super(signCode, HMAC_SHA_512);
    this.method = method;
    this.pin = pin;
    this.tonce = tonce;
  }

  public static MercadoBitcoinDigest createInstance(String method, String pin, String signCode, String tonce) {

    return signCode == null ? null : new MercadoBitcoinDigest(method, pin, signCode, tonce);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac hmac512 = getMac();

    // <method>:<PIN>:<tonce>
    String message = this.method + ":" + this.pin + ":" + this.tonce;

    hmac512.update(message.getBytes());

    return String.format("%0128x", new BigInteger(1, hmac512.doFinal())).toLowerCase();
  }
}
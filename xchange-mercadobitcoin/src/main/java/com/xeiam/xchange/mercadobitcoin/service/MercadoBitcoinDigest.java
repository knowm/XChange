package com.xeiam.xchange.mercadobitcoin.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;

/**
 * @author Felipe Micaroni Lalli
 */
public class MercadoBitcoinDigest extends BaseParamsDigest {

  private final String method;
  private final String pin;
  private final long tonce;

  /**
   * Constructor
   *
   * @param signCode (called "Codigo")
   * @param tonce See {@link com.xeiam.xchange.mercadobitcoin.MercadoBitcoinUtils#getTonce()}
   */
  private MercadoBitcoinDigest(String method, String pin, String signCode, long tonce) {

    super(signCode, HMAC_SHA_512);
    this.method = method;
    this.pin = pin;
    this.tonce = tonce;
  }

  public static MercadoBitcoinDigest createInstance(String method, String pin, String signCode, long tonce) {

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
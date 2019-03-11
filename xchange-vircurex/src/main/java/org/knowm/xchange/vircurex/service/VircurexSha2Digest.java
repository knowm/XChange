package org.knowm.xchange.vircurex.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.knowm.xchange.utils.DigestUtils;

/**
 * This may be used as the value of a @HeaderParam, @QueryParam or @PathParam to create a digest of
 * the post body (composed of @FormParam's). Don't use as the value of a @FormParam, it will
 * probably cause an infinite loop.
 *
 * <p>This may be used for REST APIs where some parameters' values must be digests of other
 * parameters. An example is the MtGox API v1, where the Rest-Sign header parameter must be a digest
 * of the request body (which is composed of @FormParams).
 */
public class VircurexSha2Digest {

  private static final String SHA_256 = "SHA-256";
  private final String secretWord;
  private MessageDigest digest;

  /**
   * Constructor
   *
   * @param aSecretWord
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  public VircurexSha2Digest(
      String aSecretWord,
      String aUserName,
      String aTimeStamp,
      long aNonce,
      String aMethod,
      String anOrderType,
      String anOrderAmount,
      String aTransactionCurrency,
      String aLimitPrice,
      String aTradeableCurrency)
      throws IllegalArgumentException {

    try {
      digest = MessageDigest.getInstance(SHA_256);
      digest.update(
          (aSecretWord
                  + ";"
                  + aUserName
                  + ";"
                  + aTimeStamp
                  + ";"
                  + aNonce
                  + ";"
                  + aMethod
                  + ";"
                  + anOrderType
                  + ";"
                  + anOrderAmount
                  + ";"
                  + aTransactionCurrency
                  + ";"
                  + aLimitPrice
                  + ";"
                  + aTradeableCurrency)
              .getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    secretWord = DigestUtils.bytesToHex(digest.digest());
  }

  public VircurexSha2Digest(
      String aSecretWord, String aUserName, String aTimeStamp, long aNonce, String aMethod) {

    try {
      digest = MessageDigest.getInstance(SHA_256);
      digest.update(
          (aSecretWord + ";" + aUserName + ";" + aTimeStamp + ";" + aNonce + ";" + aMethod)
              .getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    secretWord = DigestUtils.bytesToHex(digest.digest());
  }

  public VircurexSha2Digest(
      String aSecretWord,
      String aUserName,
      String aTimeStamp,
      long aNonce,
      String aMethod,
      String anOrderId) {

    try {
      digest = MessageDigest.getInstance(SHA_256);
      digest.update(
          (aSecretWord
                  + ";"
                  + aUserName
                  + ";"
                  + aTimeStamp
                  + ";"
                  + aNonce
                  + ";"
                  + aMethod
                  + ";"
                  + anOrderId)
              .getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    secretWord = DigestUtils.bytesToHex(digest.digest());
  }

  @Override
  public String toString() {

    return secretWord;
  }
}

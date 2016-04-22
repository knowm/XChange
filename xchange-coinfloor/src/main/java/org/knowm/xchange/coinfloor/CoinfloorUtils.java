package org.knowm.xchange.coinfloor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.util.encoders.Base64;

import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author obsessiveOrange
 */
public class CoinfloorUtils {

  // TODO move this to metadata and coinfloor.json
  public enum CoinfloorCurrency {
    BTC, GBP, EUR, USD, PLN
  }

  private static final ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp224k1");
  private static final ECDomainParameters secp224k1 = new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN(), spec.getH());

  public static byte[] buildClientNonce() {

    byte[] clientNonce = new byte[16];
    new Random().nextBytes(clientNonce);
    return clientNonce;
  }

  public static List<String> buildSignature(long userID, String cookie, String passphrase, String serverNonce, byte[] clientNonce) {

    try {
      final SHA224Digest sha = new SHA224Digest();
      DataOutputStream dos = new DataOutputStream(new OutputStream() {

        @Override
        public void write(int b) {

          sha.update((byte) b);
        }

        @Override
        public void write(byte[] buf, int off, int len) {

          sha.update(buf, off, len);
        }

      });
      dos.writeLong(userID);
      dos.write(passphrase.getBytes(Charset.forName("UTF-8")));
      dos.flush();
      byte[] digest = new byte[28];
      sha.doFinal(digest, 0);
      ECDSASigner signer = new ECDSASigner();
      signer.init(true, new ECPrivateKeyParameters(new BigInteger(1, digest), secp224k1));
      dos.writeLong(userID);
      dos.write(Base64.decode(serverNonce));
      dos.write(clientNonce);
      dos.flush();
      dos.close();
      sha.doFinal(digest, 0);
      BigInteger[] signature = signer.generateSignature(digest);
      return Arrays.asList(bigIntegerToBase64(signature[0]), bigIntegerToBase64(signature[1]));
    } catch (IOException e) {
      throw new ExchangeException("Could not build signature for authentication");
    }
  }

  private static String bigIntegerToBase64(BigInteger bi) {

    byte[] bytes = bi.toByteArray();
    return bytes[0] == 0 ? org.bouncycastle.util.encoders.Base64.toBase64String(bytes, 1, bytes.length - 1)
        : org.bouncycastle.util.encoders.Base64.toBase64String(bytes);
  }

  public static void checkSuccess(Map<String, Object> payload) {

    if (payload.containsKey("error_code")) {
      if (!(payload.get("error_code") instanceof Integer) || (Integer) payload.get("error_code") != 0) {
        throw new ExchangeException("Server returned error " + payload.get("error_code") + ": " + payload.get("error_msg"));
      }
    }
  }

  public static CoinfloorCurrency currencyOf(String currency) {

    if (currency.equals("BTC")) {
      return CoinfloorCurrency.BTC;
    } else if (currency.equals("GBP")) {
      return CoinfloorCurrency.GBP;
    }

    throw new ExchangeException("Currency " + currency + " not supported by coinfloor!");
  }

  public static CoinfloorCurrency getCurrency(int currencyCode) {

    switch (currencyCode) {
    case 0:
      return null;
    case 63488:
      return CoinfloorCurrency.BTC;
    case 64000:
      return CoinfloorCurrency.EUR;
    case 64032:
      return CoinfloorCurrency.GBP;
    case 64128:
      return CoinfloorCurrency.USD;
    case 64936:
      return CoinfloorCurrency.PLN;
    }

    throw new ExchangeException("Currency Code " + currencyCode + " not supported by coinfloor!");
  }

  public static int toCurrencyCode(String currency) {

    return toCurrencyCode(CoinfloorUtils.currencyOf(currency));
  }

  public static int toCurrencyCode(CoinfloorCurrency currency) {

    switch (currency) {
    case BTC:
      return 63488;
    case EUR:
      return 64000;
    case GBP:
      return 64032;
    case USD:
      return 64128;
    case PLN:
      return 64936;
    }

    throw new ExchangeException("Currency " + currency + " not supported by coinfloor!");
  }

  private static int getCurrencyScale(CoinfloorCurrency currency) {

    switch (currency) {
    case BTC:
      return 4;
    case GBP:
    case EUR:
    case USD:
    case PLN:
      return 2;
    }

    throw new ExchangeException("Currency " + currency + " not supported by coinfloor!");
  }

  public static BigDecimal scaleToBigDecimal(String currency, Integer amountToScale) {

    return scaleToBigDecimal(CoinfloorUtils.currencyOf(currency), amountToScale);
  }

  public static BigDecimal scaleToBigDecimal(CoinfloorCurrency currency, Integer amountToScale) {

    return BigDecimal.valueOf(amountToScale, getCurrencyScale(currency));
  }

  public static int scaleToInt(String currency, BigDecimal amountToScale) {

    return scaleToInt(CoinfloorUtils.currencyOf(currency), amountToScale);
  }

  public static int scaleToInt(CoinfloorCurrency currency, BigDecimal amountToScale) {

    return amountToScale.movePointRight(getCurrencyScale(currency)).intValue();
  }

  /**
   * Scale integer price results from API call to BigDecimal for local use.
   *
   * @param amountToScale The integer result recieved from API Call
   * @return BigDecimal representation of integer amount
   */
  public static BigDecimal scalePriceToBigDecimal(String currency, String counterCurrency, Integer amountToScale) {

    return scalePriceToBigDecimal(CoinfloorUtils.currencyOf(currency), CoinfloorUtils.currencyOf(counterCurrency), amountToScale);
  }

  public static BigDecimal scalePriceToBigDecimal(CoinfloorCurrency baseCurrency, CoinfloorCurrency counterCurrency, Integer amountToScale) {

    return BigDecimal.valueOf(amountToScale, getCurrencyScale(counterCurrency) - getCurrencyScale(baseCurrency) + 4);
  }

  /**
   * Scale integer price results from API call to BigDecimal for local use.
   *
   * @param amountToScale The integer result recieved from API Call
   * @return BigDecimal representation of integer amount
   */
  public static int scalePriceToInt(String baseCurrency, String counterCurrency, BigDecimal amountToScale) {

    return scalePriceToInt(CoinfloorUtils.currencyOf(baseCurrency), CoinfloorUtils.currencyOf(counterCurrency), amountToScale);
  }

  public static int scalePriceToInt(CoinfloorCurrency baseCurrency, CoinfloorCurrency counterCurrency, BigDecimal amountToScale) {

    return amountToScale.movePointRight(getCurrencyScale(counterCurrency) - getCurrencyScale(baseCurrency) + 4).intValue();
  }
}

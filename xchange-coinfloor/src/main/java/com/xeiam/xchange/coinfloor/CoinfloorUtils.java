/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.coinfloor;

import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.util.encoders.Base64;

import com.xeiam.xchange.ExchangeException;

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

/**
 * @author obsessiveOrange
 */
public class CoinfloorUtils {

  private static final ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp224k1");
  private static final ECDomainParameters secp224k1 = new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN(), spec.getH());

  public static byte[] buildClientNonce(){
    byte[] clientNonce = new byte[16];
  	new Random().nextBytes(clientNonce);
	return clientNonce;
  }
  
  public static List<String> buildSignature(long userID, String cookie, String passphrase, String serverNonce, byte[] clientNonce){
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
	} catch (IOException e) {throw new ExchangeException("Could not build signature for authentication");}
  }
  
  
  protected static String buildNonceString() {

    long currentTime = System.currentTimeMillis();
    return "::" + currentTime + ":";
  }
 
  private static String bigIntegerToBase64(BigInteger bi) {
	byte[] bytes = bi.toByteArray();
	return bytes[0] == 0 ? org.bouncycastle.util.encoders.Base64.toBase64String(bytes, 1, bytes.length - 1) : org.bouncycastle.util.encoders.Base64.toBase64String(bytes);
  }
  
  public static void checkSuccess(Map<String, Object> payload){
	  if(payload.containsKey("error_code")){
		  if(!(payload.get("error_code") instanceof Integer) || (Integer)payload.get("error_code") != 0){
			  throw new ExchangeException("Server returned error " + payload.get("error_code") + ": " + payload.get("error_msg"));
		  }
	  }
  }

  public static String getCurrency(int currencyCode){
	  if(currencyCode == 0){return null;}
	  else if(currencyCode == 63488){return "BTC";}
	  else if(currencyCode == 64032){return "GBP";}
	  
	  throw new ExchangeException("Currency Code " + currencyCode + " not supported by coinfloor!");
  }
  		
  public static int toCurrencyCode(String currency) {
	  if(currency.equals("BTC")){return 63488;}
	  else if(currency.equals("GBP")){return 64032;}
	  
	  throw new ExchangeException("Currency " + currency + " not supported by coinfloor!");
  }

  public static BigDecimal scaleToBigDecimal(String currency, Integer amountToScale){
	  if(currency.equals("BTC")){return new BigDecimal(amountToScale.toString()).divide(new BigDecimal("10000"));}
	  else if(currency.equals("GBP")){return new BigDecimal(amountToScale.toString()).divide(new BigDecimal("10000"));}
	  
	  throw new ExchangeException("Currency " + currency + " not supported by coinfloor!");
  }
  
  public static int scaleToInt(String currency, BigDecimal amountToScale){
	  if(currency.equals("BTC")){return amountToScale.multiply(new BigDecimal("10000")).intValue();}
	  else if(currency.equals("GBP")){return amountToScale.multiply(new BigDecimal("10000")).intValue();}
	  
	  throw new ExchangeException("Currency " + currency + " not supported by coinfloor!");
  }

  /**
   * Scale integer price results from API call to BigDecimal for local use.
   * @param amountToScale The integer result recieved from API Call
   * @return BigDecimal representation of integer amount
   */
  public static BigDecimal scalePriceToBigDecimal(Integer amountToScale){
	  return new BigDecimal(amountToScale.toString()).divide(new BigDecimal(10000));
  }
  

  /**
   * Scale integer price results from API call to BigDecimal for local use.
   * @param amountToScale The integer result recieved from API Call
   * @return BigDecimal representation of integer amount
   */
  public static int scalePriceToInt(BigDecimal amountToScale){
	  return amountToScale.multiply(new BigDecimal(10000)).intValue();
  }
}

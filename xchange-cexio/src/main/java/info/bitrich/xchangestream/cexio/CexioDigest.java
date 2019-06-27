package info.bitrich.xchangestream.cexio;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CexioDigest extends BaseParamsDigest {

  private static final char[] DIGITS =
      new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  protected CexioDigest(String secretKey) {
    super(secretKey, HMAC_SHA_256);
  }

  static CexioDigest createInstance(String secretKey) {
    return secretKey == null ? null : new CexioDigest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    return null;
  }

  private static char[] encodeHex(byte[] data) {
    int l = data.length;
    char[] out = new char[l << 1];
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
      out[j++] = DIGITS[0x0F & data[i]];
    }
    return out;
  }

  public String createSignature(long timestamp, String apiKey) {
    return new String(encodeHex(getMac().doFinal((timestamp + apiKey).getBytes())));
  }
}

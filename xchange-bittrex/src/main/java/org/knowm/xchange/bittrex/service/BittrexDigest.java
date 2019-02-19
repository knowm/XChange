package org.knowm.xchange.bittrex.service;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BittrexDigest extends BaseParamsDigest {

  private static final Pattern STARTS_WITH_SLASHES = Pattern.compile("(/*)(.*)");
  private static final Pattern ENDS_WITH_SLASHES = Pattern.compile("(.*?)(/*)");

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BittrexDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BittrexDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BittrexDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String invocationUrl = getInvocationUrl(restInvocation);
    Mac mac = getMac();
    mac.update(invocationUrl.getBytes());

    return String.format("%0128x", new BigInteger(1, mac.doFinal()));
  }

  // copy paste methods from si.mazi.rescu.RestInvocation
  static String getInvocationUrl(RestInvocation restInvocation) {
    // HARDCODED API URL. Reason: evade INVALID_SIGNATURE error. Url used as digest payload
    String completeUrl = "https://bittrex.com/api/";
    completeUrl = appendPath(completeUrl, restInvocation.getPath());
    completeUrl = appendIfNotEmpty(completeUrl, restInvocation.getQueryString(), "?");
    return completeUrl;
  }

  static String appendIfNotEmpty(String url, String next, String separator) {
    if (next != null && isNonEmpty(next)) {
      if (!url.endsWith(separator) && !next.startsWith(separator)) {
        url += separator;
      }
      url += next;
    }
    return url;
  }

  static String appendPath(String first, String second) {
    first = nullToEmpty(first);
    second = nullToEmpty(second);

    Matcher firstParsed = ENDS_WITH_SLASHES.matcher(first);
    if (!firstParsed.matches()) {
      throw new RuntimeException(
          "Incorrect regular expression ENDS_WITH_SLASHES, fix the bug in rescu.");
    }
    Matcher secondParsed = STARTS_WITH_SLASHES.matcher(second);
    if (!secondParsed.matches()) {
      throw new RuntimeException(
          "Incorrect regular expression STARTS_WITH_SLASHES, fix the bug in rescu.");
    }

    String firstTrimmed = firstParsed.group(1);
    String secondTrimmed = secondParsed.group(2);

    // Use middle slash when any of the original strings contained adjacent slash, or both trimmed
    // strings were nonempty.
    boolean midSlash =
        isNonEmpty(firstParsed.group(2))
            || isNonEmpty(secondParsed.group(1))
            || (isNonEmpty(firstTrimmed) && isNonEmpty(secondTrimmed));
    return firstTrimmed + (midSlash ? "/" : "") + secondTrimmed;
  }

  private static boolean isNonEmpty(String str) {
    return str.length() > 0;
  }

  private static String nullToEmpty(String str) {
    return str == null ? "" : str;
  }
}

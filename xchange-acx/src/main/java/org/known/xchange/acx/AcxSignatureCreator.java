package org.known.xchange.acx;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class AcxSignatureCreator extends BaseParamsDigest {
  private static final String PLACEHOLDER = "ACX_PLACEHOLDER";
  private static final char[] DIGITS =
      new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
  private final Field invocationUrlField;

  public AcxSignatureCreator(String secretKey) {
    super(secretKey, HMAC_SHA_256);
    try {
      invocationUrlField = RestInvocation.class.getDeclaredField("invocationUrl");
      invocationUrlField.setAccessible(true);
    } catch (NoSuchFieldException e) {
      throw new IllegalStateException("rescu library has been updated");
    }
  }

  private static char[] encodeHex(byte[] data) {
    int l = data.length;
    char[] out = new char[l << 1];
    // two characters form the hex value.
    for (int i = 0, j = 0; i < l; i++) {
      out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
      out[j++] = DIGITS[0x0F & data[i]];
    }
    return out;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String method = restInvocation.getHttpMethod();
    String path = stripParams(restInvocation.getPath());
    String query =
        Stream.of(
                restInvocation.getParamsMap().get(PathParam.class),
                restInvocation.getParamsMap().get(FormParam.class))
            .map(Params::asHttpHeaders)
            .map(Map::entrySet)
            .flatMap(Collection::stream)
            .filter(e -> !"signature".equals(e.getKey()))
            .sorted(Entry.comparingByKey())
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining("&"));
    String toSign = String.format("%s|/api/v2/%s|%s", method, path, query);
    Mac sha256hmac = getMac();
    byte[] signed = sha256hmac.doFinal(toSign.getBytes());
    String signature = new String(encodeHex(signed));
    replaceInvocationUrl(restInvocation, signature);
    return signature;
  }

  private String stripParams(String path) {
    int paramsStart = path.indexOf("?");
    String stripped = paramsStart == -1 ? path : path.substring(0, paramsStart);
    if (stripped.startsWith("/")) {
      stripped = stripped.substring(1);
    }
    return stripped;
  }

  // rescu client doesn't support ParamsDigest and therefore url has to be updated manually,
  // see https://github.com/mmazi/rescu/issues/62
  // TODO: remove the hack once the functionality is provided
  private void replaceInvocationUrl(RestInvocation restInvocation, String signature) {
    String invocationUrl = restInvocation.getInvocationUrl();
    String newInvocationUrl = invocationUrl.replace(PLACEHOLDER, signature);
    try {
      invocationUrlField.set(restInvocation, newInvocationUrl);
    } catch (IllegalArgumentException | IllegalAccessException e) {
      throw new IllegalStateException("rescu library has been updated");
    }
  }

  @Override
  public String toString() {
    return PLACEHOLDER;
  }
}

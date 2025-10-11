package org.knowm.xchange.dase.service;

import jakarta.ws.rs.HeaderParam;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/**
 * HMAC-SHA256 signer for DASE.
 *
 * <p>Message: {timestamp}{METHOD}{PATH+QUERY}{BODY} Output: Base64-encoded signature string.
 */
public class DaseDigest extends BaseParamsDigest {

  private DaseDigest(String base64Secret) {
    super(decodeBase64(base64Secret), HMAC_SHA_256);
  }

  public static DaseDigest createInstance(String base64Secret) {
    return base64Secret == null ? null : new DaseDigest(base64Secret);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    final String timestamp =
        String.valueOf(restInvocation.getParamValue(HeaderParam.class, "ex-api-timestamp"));

    final String method = restInvocation.getHttpMethod().toUpperCase();

    String rawPath = restInvocation.getPath();
    if (rawPath == null) {
      rawPath = "";
    }
    // Normalize to exactly one leading slash to avoid signature mismatches like "//v1/orders"
    final String path = rawPath.startsWith("/") ? rawPath : "/" + rawPath;

    final String query = restInvocation.getQueryString();
    final String pathWithQuery;
    if (query != null && !query.isEmpty()) {
      pathWithQuery = query.startsWith("?") ? (path + query) : (path + "?" + query);
    } else {
      pathWithQuery = path;
    }

    final String body =
        restInvocation.getRequestBody() == null ? "" : restInvocation.getRequestBody();

    final String message = timestamp + method + pathWithQuery + body;

    Mac mac = getMac();
    mac.update(message.getBytes(StandardCharsets.UTF_8));
    byte[] raw = mac.doFinal();
    return Base64.getEncoder().encodeToString(raw);
  }

  /** Helper for direct signing in unit tests. */
  public String sign(String timestamp, String method, String pathWithQuery, String body) {
    final String payload =
        String.valueOf(timestamp)
            + method.toUpperCase()
            + pathWithQuery
            + (body == null ? "" : body);

    Mac mac = getMac();
    mac.update(payload.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(mac.doFinal());
  }
}

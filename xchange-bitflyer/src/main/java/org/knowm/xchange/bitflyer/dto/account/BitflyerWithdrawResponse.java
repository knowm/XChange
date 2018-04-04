package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object representing json returned from <code>GET /v1/me/withdraw</code>
 *
 * <p>Example
 *
 * <pre><code>{
 *   "message_id": "69476620-5056-4003-bcbe-42658a2b041b"
 * }</code></pre>
 *
 * or
 *
 * <pre><code>{
 *   "status": -700,
 *   "error_message": "This account has not yet been authenticated",
 *   "data": null
 * }</code></pre>
 *
 * @author bryant_harris
 */
public class BitflyerWithdrawResponse {
  @JsonProperty("message_id")
  String messageID;

  @JsonProperty("status")
  Long status;

  @JsonProperty("error_message")
  String errorMessage;

  @Override
  public String toString() {
    return "BitflyerWithdrawResponse [messageID="
        + messageID
        + ", status="
        + status
        + ", errorMessage="
        + errorMessage
        + "]";
  }
}

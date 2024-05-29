export function gen_sign(request) {
  const pattern = RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
  const url = request.url.tryGetSubstituted();
  const matches =  url.match(pattern);

  const timestamp = Math.floor(Date.now()).toFixed();
  const query = matches[7] || "";

  const payloadToSign = query.replace("&signature={{signature}}", "").replace("{{timestamp}}", timestamp);

  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha256().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();

  request.variables.set("timestamp", timestamp);
  request.variables.set("signature", sign);
}
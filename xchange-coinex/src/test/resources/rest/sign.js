export function gen_sign(method, request) {
  const pattern = RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
  const url = request.url.tryGetSubstituted();
  const matches =  url.match(pattern);

  const path = matches[5];
  const query = matches[7] || "";
  const body = request.body.tryGetSubstituted() || "";
  const timestamp = Math.floor(Date.now()).toFixed();
  const payloadToSign = `${method}${path}${query}${body}${timestamp}`;
  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha256().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();

  request.variables.set("timestamp", timestamp);
  request.variables.set("sign", sign);
}
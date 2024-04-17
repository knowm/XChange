export function gen_sign(method, request) {
  const pattern = RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
  const url = request.url.tryGetSubstituted();
  const matches =  url.match(pattern);

  const path = matches[5];
  const query = matches[7] || "";
  const hexedHashedBody = crypto.sha512().updateWithText(request.body.tryGetSubstituted() || "").digest().toHex();
  const timestamp = Math.floor(Date.now() / 1000).toFixed();
  const payloadToSign = `${method}\n${path}\n${query}\n${hexedHashedBody}\n${timestamp}`;
  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha512().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();

  request.variables.set("timestamp", timestamp);
  request.variables.set("sign", sign);
}
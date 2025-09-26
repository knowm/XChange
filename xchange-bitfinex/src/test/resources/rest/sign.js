export function gen_sign(request) {
  const pattern = RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
  const url = request.url.tryGetSubstituted();
  const matches =  url.match(pattern);

  const path = "/api" + matches[5];
  const body = request.body.tryGetSubstituted() || '';
  const timestamp = Date.now().toString();

  const payloadToSign = `${path}${timestamp}${body}`;

  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha384().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();

  request.variables.set("nonce", timestamp);
  request.variables.set("signature", sign);
}
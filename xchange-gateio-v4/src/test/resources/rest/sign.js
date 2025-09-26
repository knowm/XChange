export function gen_sign(request) {
  const method = request.method
  const pattern = RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
  const url = request.url.tryGetSubstituted();
  const matches =  url.match(pattern);

  const path = matches[5];
  const query = matches[7] || "";

  // hash of "" if body is empty
  let hexedHashedBody = "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";

  if (request.body.tryGetSubstituted()) {
    hexedHashedBody = crypto.sha512().updateWithText(request.body.tryGetSubstituted()).digest().toHex();
  }

  const timestamp = Math.floor(Date.now() / 1000).toFixed();
  const payloadToSign = `${method}\n${path}\n${query}\n${hexedHashedBody}\n${timestamp}`;
  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha512().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();

  request.variables.set("timestamp", timestamp);
  request.variables.set("sign", sign);
}
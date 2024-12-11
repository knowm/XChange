export function gen_sign(request) {
  const pattern = RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
  const method = request.method;
  const url = request.url.tryGetSubstituted();
  const matches =  url.match(pattern);

  const path = matches[5];

  let query = matches[7] || "";
  if (query !== "") {
    query = "?" + query;
  }

  const body = request.body.tryGetSubstituted() || "";
  const timestamp = Math.floor(Date.now()).toFixed();

  const payloadToSign = `${timestamp}${method}${path}${query}${body}`;
  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha256().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toBase64();

  request.variables.set("timestamp", timestamp);
  request.variables.set("sign", sign);
}
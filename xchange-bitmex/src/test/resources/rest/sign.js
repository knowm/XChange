export function gen_sign(request) {
  const pattern = RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
  const method = request.method;
  const url = request.url.tryGetSubstituted();
  const matches =  url.match(pattern);

  const path = matches[5];

  let query = matches[7] || "";
  if (query !== "") {
    query = "?" + encodeURI(query)
    .replaceAll(":", "%3A")
    .replaceAll(",", "%2C");
  }

  const body = request.body.tryGetSubstituted() || "";
  const timestamp = Math.floor(new Date(new Date().getTime() + 30000)).toFixed();

  const payloadToSign = `${method}${path}${query}${timestamp}${body}`;
  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha256().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();

  request.variables.set("timestamp", timestamp);
  request.variables.set("sign", sign);
}
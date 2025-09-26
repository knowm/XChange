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

  const nonceValue = Date.now().toString();
  const body = request.body.getRaw().replace("{{nonceValue}}", nonceValue) || "";
  const sha = crypto.sha256().updateWithText(`${nonceValue}${body}`).digest().toBase64();

  const apiSecret = request.environment.get("api_secret");
  const sign = crypto.hmac.sha512().withBase64Secret(apiSecret).updateWithText(path).updateWithBase64(sha).digest().toBase64();

  request.variables.set("nonceValue", nonceValue);
  request.variables.set("sign", sign);
}
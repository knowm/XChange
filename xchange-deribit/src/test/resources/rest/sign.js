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

  const timestamp = Date.now().toString();
  const nonceValue = Date.now().toString();

  const payloadToSign = `${timestamp}\n${nonceValue}\n${method}\n${path}${query}\n${body}\n`;

  const apiKey = request.environment.get("api_key");
  const apiSecret = request.environment.get("api_secret");

  const sign = crypto.hmac.sha256().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();

  const authHeader = `deri-hmac-sha256 id=${apiKey},ts=${timestamp},sig=${sign},nonce=${nonceValue}`;

  request.variables.set("auth_header", authHeader);
}
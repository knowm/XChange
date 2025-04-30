export function gen_sign(timestamp) {
  const payloadToSign = `${timestamp}GET/user/verify`;
  const apiSecret = request.environment.get("api_secret");
  return crypto.hmac.sha256().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toBase64();
}
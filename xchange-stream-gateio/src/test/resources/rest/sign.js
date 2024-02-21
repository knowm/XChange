export function gen_sign(channel, event, timestamp) {
  const payloadToSign = `channel=${channel}&event=${event}&time=${timestamp}`;
  const apiSecret = request.environment.get("api_secret");
  return crypto.hmac.sha512().withTextSecret(apiSecret).updateWithText(payloadToSign).digest().toHex();
}
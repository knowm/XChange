package org.knowm.xchange.binance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import org.junit.jupiter.api.Test;

class BinanceEd25519SignerTest {

  private static KeyPair newKeyPair() throws Exception {
    return KeyPairGenerator.getInstance("Ed25519").generateKeyPair();
  }

  @Test
  void signatureVerifiesWithMatchingPublicKey() throws Exception {
    KeyPair keyPair = newKeyPair();
    String base64Pkcs8 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    String payload = "symbol=BTCUSDT&timestamp=1700000000000";

    PrivateKey parsed = BinanceEd25519Signer.parsePrivateKey(base64Pkcs8);
    String signatureBase64 = BinanceEd25519Signer.signBase64(parsed, payload);

    Signature verifier = Signature.getInstance("Ed25519");
    verifier.initVerify(keyPair.getPublic());
    verifier.update(payload.getBytes(StandardCharsets.UTF_8));
    assertThat(verifier.verify(Base64.getDecoder().decode(signatureBase64))).isTrue();
  }

  @Test
  void signingIsDeterministic() throws Exception {
    KeyPair keyPair = newKeyPair();
    String payload = "symbol=BTCUSDT";
    assertThat(BinanceEd25519Signer.signBase64(keyPair.getPrivate(), payload))
        .isEqualTo(BinanceEd25519Signer.signBase64(keyPair.getPrivate(), payload));
  }

  @Test
  void rejectsInvalidKey() {
    assertThatThrownBy(() -> BinanceEd25519Signer.parsePrivateKey("not-a-key"))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(
            () ->
                BinanceEd25519Signer.parsePrivateKey(
                    Base64.getEncoder().encodeToString(new byte[] {1, 2, 3})))
        .isInstanceOf(IllegalArgumentException.class);
  }
}

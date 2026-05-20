package org.knowm.xchange.idex;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.List;

import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.knowm.xchange.idex.IdexSignature.SignatureData;

class IdexSignatureTest {

  /**
   * Well-known private key used in many Ethereum testing examples.
   * Private key: 0x4c0883a69102937d6231471b5dbb6e538eba2ef51f71a7f38fb3a20ae97c8ed6
   * Expected address: 0x2c7536E3605D9C16a7a3D7b1898e529396a65c23
   */
  private static final String TEST_PRIVATE_KEY =
      "0x4c0883a69102937d6231471b5dbb6e538eba2ef51f71a7f38fb3a20ae97c8ed6";

  @Test
  void keccak256_producesCorrectHash() {
    // "hello" → known keccak-256 digest
    byte[] input = "hello".getBytes();
    byte[] hash = IdexSignature.keccak256(input);
    assertEquals(
        "1c8aff950685c2ed4bc3174f3472287b56d9517b9c948127319a09a7a36deac8",
        Hex.toHexString(hash));
  }

  @Test
  void toBytes32_paddingAndTruncation() {
    // Small value should be left-padded to 32 bytes
    BigInteger one = BigInteger.ONE;
    byte[] result = IdexSignature.toBytes32(one);
    assertEquals(32, result.length);
    assertEquals(1, result[31]);
    for (int i = 0; i < 31; i++) {
      assertEquals(0, result[i]);
    }
  }

  @Test
  void generateSignature_returnsCorrectVLength() {
    List<List<String>> hashData =
        asList(
            asList("contractAddress", "0xb794f5ea0ba39494ce839613fffba74279579268", "address"),
            asList("tokenBuy", "0x0000000000000000000000000000000000000000", "address"),
            asList("amountBuy", "1000000000000000000", "uint256"),
            asList("tokenSell", "0x6810e776880c02933d47db1b9fc05908e5386b96", "address"),
            asList("amountSell", "500000000000000000", "uint256"),
            asList("expires", "100000", "uint256"),
            asList("nonce", "1", "uint256"),
            asList("address", "0x2c7536e3605d9c16a7a3d7b1898e529396a65c23", "address"));

    SignatureData sig = IdexSignature.generateSignature(TEST_PRIVATE_KEY, hashData);

    assertNotNull(sig);
    assertEquals(1, sig.getV().length);
    assertEquals(32, sig.getR().length);
    assertEquals(32, sig.getS().length);
    // Ethereum v is always 27 or 28
    int v = sig.getV()[0] & 0xff;
    assertTrue("v must be 27 or 28, got " + v, v == 27 || v == 28);
  }

  @Test
  void generateSignature_signatureIsVerifiable() {
    // Verify that the signature can be used to recover the correct public key,
    // confirming the v, r, s values are internally consistent.
    List<List<String>> hashData =
        asList(
            asList("contractAddress", "0xb794f5ea0ba39494ce839613fffba74279579268", "address"),
            asList("tokenBuy", "0x0000000000000000000000000000000000000000", "address"),
            asList("amountBuy", "1000000000000000000", "uint256"),
            asList("tokenSell", "0x6810e776880c02933d47db1b9fc05908e5386b96", "address"),
            asList("amountSell", "500000000000000000", "uint256"),
            asList("expires", "100000", "uint256"),
            asList("nonce", "1", "uint256"),
            asList("address", "0x2c7536e3605d9c16a7a3d7b1898e529396a65c23", "address"));

    SignatureData sig = IdexSignature.generateSignature(TEST_PRIVATE_KEY, hashData);

    // Derive the expected public key from the private key
    BigInteger privateKey = new BigInteger(TEST_PRIVATE_KEY.replace("0x", ""), 16);
    ECPoint expectedPublicKey =
        IdexSignature.CURVE.getG().multiply(privateKey).normalize();

    // Reconstruct the message hash that was signed (mirrors generateSignature internals)
    byte[] msgHash = computeMsgHash(hashData);

    // Recovery: recId = v - 27
    int recId = (sig.getV()[0] & 0xff) - 27;
    BigInteger r = new BigInteger(1, sig.getR());
    BigInteger s = new BigInteger(1, sig.getS());
    ECPoint recovered = IdexSignature.recoverPublicKey(recId, r, s, msgHash);

    assertNotNull(recovered);
    assertEquals(expectedPublicKey, recovered);
  }

  /** Replicates the message-hash computation in IdexSignature.generateSignature. */
  private byte[] computeMsgHash(List<List<String>> data) {
    java.io.ByteArrayOutputStream sigArr = new java.io.ByteArrayOutputStream();
    for (List<String> d : data) {
      String data1 = d.get(1);
      byte[] segment;
      byte[] r;
      String last =
          new java.util.LinkedList<>(asList(data1.toLowerCase().split("0x"))).getLast();
      switch (d.get(2)) {
        case "address":
          segment = new byte[20];
          r = new BigInteger(last, 16).toByteArray();
          break;
        case "uint256":
          segment = new byte[32];
          r = new BigInteger(last, 10).toByteArray();
          break;
        default:
          continue;
      }
      int segLen = segment.length;
      int rlen = Math.min(Math.max(segLen, r.length), r.length);
      int oversize = r.length - segLen;
      System.arraycopy(
          r,
          oversize > 0 ? oversize : 0,
          segment,
          oversize > 0 ? 0 : (segLen - rlen),
          Math.min(segLen, rlen));
      try {
        sigArr.write(segment);
      } catch (java.io.IOException e) {
        throw new RuntimeException(e);
      }
    }
    byte[] rawhash = IdexSignature.keccak256(sigArr.toByteArray());
    byte[] saltBytes = "\u0019Ethereum Signed Message:\n32".getBytes();
    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
    try {
      baos.write(saltBytes);
      baos.write(rawhash);
    } catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }
    return IdexSignature.keccak256(baos.toByteArray());
  }
}

package org.knowm.xchange.idex;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.util.Arrays.asList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.KeccakDigest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECPoint;

public class IdexSignature {

  private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
  static final ECDomainParameters CURVE =
      new ECDomainParameters(
          CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH());

  /** Simple container for Ethereum signature components v, r, s. */
  public static class SignatureData {
    private final byte[] v;
    private final byte[] r;
    private final byte[] s;

    public SignatureData(byte v, byte[] r, byte[] s) {
      this.v = new byte[] {v};
      this.r = r;
      this.s = s;
    }

    public byte[] getV() {
      return v;
    }

    public byte[] getR() {
      return r;
    }

    public byte[] getS() {
      return s;
    }
  }

  /** Compute Keccak-256 hash of the input bytes. */
  static byte[] keccak256(byte[] input) {
    KeccakDigest digest = new KeccakDigest(256);
    digest.update(input, 0, input.length);
    byte[] result = new byte[32];
    digest.doFinal(result, 0);
    return result;
  }

  /** Generate v, r, s values from payload */
  static SignatureData generateSignature(String apiSecret, List<List<String>> data) {
    byte[] rawhash;
    byte[] saltBytes;
    String[] last = new String[1];

    try (ByteArrayOutputStream sig_arr = new ByteArrayOutputStream()) {
      for (List<String> d : data) {
        String data1 = d.get(1);
        /* remove 0x prefix and convert to bytes */
        byte[] segment = new byte[0];
        byte[] r = new byte[0];
        last[0] = new LinkedList<>(asList(data1.toLowerCase().split("0x"))).getLast();
        switch (d.get(2)) {
          case "address":
            {
              segment = new byte[20];
              r = new BigInteger(last[0], 16).toByteArray();
              break;
            }
          case "uint256":
            {
              segment = new byte[32];
              r = new BigInteger(last[0], 10).toByteArray();
              break;
            }
        }
        int segLen = segment.length;
        int rlen = min(max(segLen, r.length), r.length);
        int oversize = r.length - segLen;
        System.arraycopy(
            r,
            oversize > 0 ? oversize : 0,
            segment,
            oversize > 0 ? 0 : (segLen - rlen),
            min(segLen, rlen));
        sig_arr.write(segment);
      }
      rawhash = keccak256(sig_arr.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Salt the hashed packed string (Ethereum personal sign prefix)
    saltBytes = "\u0019Ethereum Signed Message:\n32".getBytes();
    byte[] salted;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      baos.write(saltBytes);
      baos.write(rawhash);
      salted = baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // Hash the salted message
    byte[] msgHash = keccak256(salted);

    // Derive the public key point from the private key
    last[0] = new LinkedList<>(asList(apiSecret.split("0x"))).getLast();
    BigInteger privateKeyInt = new BigInteger(last[0], 16);
    ECPoint publicKeyPoint = CURVE.getG().multiply(privateKeyInt).normalize();

    // Sign with ECDSA using RFC 6979 deterministic k (HMacDSAKCalculator with SHA-256)
    ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
    signer.init(true, new ECPrivateKeyParameters(privateKeyInt, CURVE));
    BigInteger[] rs = signer.generateSignature(msgHash);
    BigInteger r = rs[0];
    BigInteger s = rs[1];

    // Enforce low-s canonical form
    BigInteger halfOrder = CURVE.getN().shiftRight(1);
    if (s.compareTo(halfOrder) > 0) {
      s = CURVE.getN().subtract(s);
    }

    // Calculate Ethereum recovery bit: v = 27 + recId
    byte recId = -1;
    for (byte i = 0; i < 4; i++) {
      ECPoint recoveredKey = recoverPublicKey(i, r, s, msgHash);
      if (recoveredKey != null && recoveredKey.equals(publicKeyPoint)) {
        recId = i;
        break;
      }
    }
    if (recId == -1) {
      throw new RuntimeException("Could not find valid recovery id for signature");
    }

    byte v = (byte) (recId + 27);
    return new SignatureData(v, toBytes32(r), toBytes32(s));
  }

  /**
   * Attempt to recover the public key from a signature given a recovery id.
   *
   * @param recId recovery id (0–3)
   * @param r signature r component
   * @param s signature s component
   * @param msgHash the 32-byte message hash
   * @return the recovered EC point, or null if recovery fails
   */
  static ECPoint recoverPublicKey(int recId, BigInteger r, BigInteger s, byte[] msgHash) {
    BigInteger n = CURVE.getN();
    BigInteger x = r.add(BigInteger.valueOf(recId / 2).multiply(n));
    BigInteger prime = CURVE.getCurve().getField().getCharacteristic();
    if (x.compareTo(prime) >= 0) {
      return null;
    }

    // Reconstruct point R with the correct y-parity
    byte[] xBytes = toBytes32(x);
    byte[] compressedPoint = new byte[33];
    compressedPoint[0] = (byte) (0x02 + (recId & 1));
    System.arraycopy(xBytes, 0, compressedPoint, 1, 32);
    ECPoint R;
    try {
      R = CURVE.getCurve().decodePoint(compressedPoint).normalize();
    } catch (Exception e) {
      return null;
    }
    if (!R.isValid()) {
      return null;
    }

    // Q = r^-1 * (s * R - e * G)
    BigInteger e = new BigInteger(1, msgHash);
    BigInteger rInv = r.modInverse(n);
    BigInteger u1 = rInv.multiply(n.subtract(e.mod(n))).mod(n); // -e * r^-1 mod n
    BigInteger u2 = rInv.multiply(s).mod(n); // s * r^-1 mod n
    return ECAlgorithms.sumOfTwoMultiplies(CURVE.getG(), u1, R, u2).normalize();
  }

  /** Convert a BigInteger to a 32-byte big-endian array. */
  static byte[] toBytes32(BigInteger value) {
    byte[] bytes = value.toByteArray();
    if (bytes.length == 32) {
      return bytes;
    }
    byte[] result = new byte[32];
    if (bytes.length > 32) {
      // strip leading zero byte from two's complement representation
      System.arraycopy(bytes, bytes.length - 32, result, 0, 32);
    } else {
      System.arraycopy(bytes, 0, result, 32 - bytes.length, bytes.length);
    }
    return result;
  }
}

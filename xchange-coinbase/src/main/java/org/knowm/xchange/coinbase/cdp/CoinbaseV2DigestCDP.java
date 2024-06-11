package org.knowm.xchange.coinbase.cdp;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import java.io.StringReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CoinbaseV2DigestCDP extends BaseParamsDigest {

  private final String cdpPrivateKey;
  private final String cdpName;

  private CoinbaseV2DigestCDP(String cdpPrivateKey, String cdpName) {
    super("nothing","HmacSHA256");
    this.cdpPrivateKey = cdpPrivateKey;
    this.cdpName = cdpName;
  }

  public static CoinbaseV2DigestCDP createInstance(String cdpPrivateKey, String cdpName) {
    return (cdpPrivateKey == null || cdpName == null) ? null : new CoinbaseV2DigestCDP(cdpPrivateKey, cdpName);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String path = restInvocation.getInvocationUrl().replaceFirst("https://", "").replaceAll("\\?" + restInvocation.getQueryString(), "");
    String requestMethod = restInvocation.getHttpMethod();
    try {
      return generateJwt(path, cdpPrivateKey, cdpName, requestMethod);
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate JWT", e);
    }
  }

  public String generateJwt(String urlString, String cdpPrivateKey, String cdpName, String httpMethod) throws Exception {
    // Register BouncyCastle as a security provider
    Security.addProvider(new BouncyCastleProvider());

    // Load environment variables
    String privateKeyPEM = cdpPrivateKey.replace("\\n", "\n");
    String name = cdpName;

    // create header object
    Map<String, Object> header = new HashMap<>();
    header.put("alg", "ES256");
    header.put("typ", "JWT");
    header.put("kid", name);
    header.put("nonce", String.valueOf(Instant.now().getEpochSecond()));

    // create uri string for current request
    String requestMethod = httpMethod;
    String url = urlString;
    String uri = requestMethod + " " + url;

    // create data object
    Map<String, Object> data = new HashMap<>();
    data.put("iss", "cdp");
    data.put("nbf", Instant.now().getEpochSecond());
    data.put("exp", Instant.now().getEpochSecond() + 120);
    data.put("sub", name);
    data.put("uri", uri);

    // Load private key
    PEMParser pemParser = new PEMParser(new StringReader(privateKeyPEM));
    JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
    Object object = pemParser.readObject();
    PrivateKey privateKey;

    if (object instanceof PrivateKey) {
      privateKey = (PrivateKey) object;
    } else if (object instanceof org.bouncycastle.openssl.PEMKeyPair) {
      privateKey = converter.getPrivateKey(((org.bouncycastle.openssl.PEMKeyPair) object).getPrivateKeyInfo());
    } else {
      throw new Exception("Unexpected private key format");
    }
    pemParser.close();

    // Convert to ECPrivateKey
    KeyFactory keyFactory = KeyFactory.getInstance("EC");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
    ECPrivateKey ecPrivateKey = (ECPrivateKey) keyFactory.generatePrivate(keySpec);

    // create JWT
    JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
    for (Map.Entry<String, Object> entry : data.entrySet()) {
      claimsSetBuilder.claim(entry.getKey(), entry.getValue());
    }
    JWTClaimsSet claimsSet = claimsSetBuilder.build();

    JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.ES256).customParams(header).build();
    SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);

    JWSSigner signer = new ECDSASigner(ecPrivateKey);
    signedJWT.sign(signer);

    String sJWT = signedJWT.serialize();
    return "Bearer " + sJWT;
  }
}

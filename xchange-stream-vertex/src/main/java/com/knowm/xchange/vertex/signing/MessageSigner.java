package com.knowm.xchange.vertex.signing;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.FixedStructuredDataEncoder;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

public class MessageSigner {
  private static final Logger log = LoggerFactory.getLogger(MessageSigner.class);
  private final ECKeyPair keyPair;
  private final ObjectMapper mapper;

  public MessageSigner(String privateKey) {

    // load a key pair from a private key
    keyPair = Credentials.create(privateKey).getEcKeyPair();

    mapper = StreamingObjectMapperHelper.getObjectMapper();
  }


  public SignatureAndDigest signMessage(EIP712Schema schema) {

    try {

      String jsonSchema = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);

      log.trace("Signing message: {}", jsonSchema);

      FixedStructuredDataEncoder encoder = new FixedStructuredDataEncoder(jsonSchema);
      // TODO submit bugs to web3J
//            StructuredDataEncoder encoder = new StructuredDataEncoder(jsonSchema);

      byte[] bytes = encoder.hashStructuredData();

      String digest = Numeric.toHexString(bytes);
      log.trace("digest: {}", digest);

      // Sign the hashed message
      Sign.SignatureData signatureData = Sign.signMessage(bytes, keyPair, false);

      // join the r, s and v fields into one byte array and encode to hex
      byte[] signature = new byte[65];
      System.arraycopy(signatureData.getR(), 0, signature, 0, 32);
      System.arraycopy(signatureData.getS(), 0, signature, 32, 32);
      signature[64] = signatureData.getV()[0];

      String sigString = Numeric.toHexString(signature);
      return new SignatureAndDigest(sigString, digest);

    } catch (Throwable e) {
      throw new RuntimeException(e);
    }

  }
}

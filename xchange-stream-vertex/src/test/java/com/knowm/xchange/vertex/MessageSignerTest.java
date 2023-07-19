package com.knowm.xchange.vertex;

import com.knowm.xchange.vertex.dto.VertexModelUtils;
import com.knowm.xchange.vertex.signing.MessageSigner;
import com.knowm.xchange.vertex.signing.schemas.CancelOrdersSchema;
import com.knowm.xchange.vertex.signing.schemas.PlaceOrderSchema;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

public class MessageSignerTest {

  Logger log = LoggerFactory.getLogger(MessageSignerTest.class);

  @Test
  public void testSignOrder() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

    ECKeyPair ecKeyPair = Keys.createEcKeyPair();

    MessageSigner messageSigner = new MessageSigner(ecKeyPair.getPrivateKey().toString(16));


    PlaceOrderSchema orderSchema = PlaceOrderSchema.build(421613, "0xf03f457a30e598d5020164a339727ef40f2b8fbc", 1L, VertexModelUtils.buildSender("0x3cd04f7Dbef1DE0C27100536CE12819Ee9dCFAC3", ""), BigInteger.ZERO,
        BigInteger.valueOf(10000000000L), BigInteger.valueOf(10000000000L));
    String signatureData = messageSigner.signMessage(orderSchema).getSignature();

    log.info("signatureData: {}", signatureData);


  }


  @Test
  public void examplePlaceOrder() {

    ECKeyPair ecKeyPair = Credentials.create("09093d55d404c51871cc12a73fc482a245bb066d101d1ac840d73ee534cee4b9").getEcKeyPair();

    MessageSigner messageSigner = new MessageSigner(ecKeyPair.getPrivateKey().toString(16));

    BigInteger zero = BigInteger.valueOf(4611687701117784255L);

    String sender = Numeric.toHexString(Numeric.hexStringToByteArray("0x841fe4876763357975d60da128d8a54bb045d76a64656661756c740000000000"));
    PlaceOrderSchema orderSchema = PlaceOrderSchema.build(421613, "0xf03f457a30e598d5020164a339727ef40f2b8fbc", 1764428860167815857L,
        sender, zero, BigInteger.valueOf(-10000000000000000L), new BigInteger("28898000000000000000000"));
    String signatureData = messageSigner.signMessage(orderSchema).getSignature();

    assertEquals("0x4ed2c9e3e8d5dd331d980d0cb7effc8f007b5cc81159c3c0c5cdffb2249de1710e6f7d398fd57b5cab32146b88c8bae1ae74ca5f23dd066779d35166aafa4fb21b", signatureData);

  }

  @Test
  public void exampleCancelOrder() {
    ECKeyPair ecKeyPair = Credentials.create("09093d55d404c51871cc12a73fc482a245bb066d101d1ac840d73ee534cee4b9").getEcKeyPair();

    MessageSigner messageSigner = new MessageSigner(ecKeyPair.getPrivateKey().toString(16));

    String sender = Numeric.toHexString(Numeric.hexStringToByteArray("0x841fe4876763357975d60da128d8a54bb045d76a64656661756c740000000000"));
    CancelOrdersSchema orderSchema = CancelOrdersSchema.build(421613,
        "0xbf16e41fb4ac9922545bfc1500f67064dc2dcc3b", 1L, sender, new long[]{4}, new String[]{"0x51ba8762bc5f77957a4e896dba34e17b553b872c618ffb83dba54878796f2821"});
    String signatureData = messageSigner.signMessage(orderSchema).getSignature();

    assertEquals("0x940651c03ee3201de3b3f46d772f31d0ac276eff1b2c8b7a6b0c159e9005c2fb6b78b6722692c05445aa76d231331f3f8f2f654b278a3af9794979121324f5741b", signatureData);

  }
}

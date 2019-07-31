package org.knowm.xchange.lgo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class LgoSignatureServiceLocalRsaTest {

  private final String apiKey = "b34edb11-eb03-4da5-af84-496e42d8a92c";
  private LgoSignatureServiceLocalRsa signatureService;

  @Before
  public void setUp() throws Exception {
    InputStream pKey = LgoSignatureServiceLocalRsa.class.getResourceAsStream("/private_key.pem");
    String pKeyAsString = IOUtils.toString(pKey, "utf8");
    signatureService = new LgoSignatureServiceLocalRsa(apiKey, pKeyAsString);
  }

  @Test
  public void digestHeader() {
    String digest = signatureService.digestHeader("wss://echo.test", "3");

    assertThat(digest).startsWith(String.format("LGO %s:", apiKey));
    assertThat(digest)
        .endsWith(
            "49fc528952ac62c7fd18524df714c7929900e8da66532cd99f4e7bda74eb22913120f85902afe7d48b1a50639f8a0748b9ffb2c1736189c5a01d9de4c79131dd3911feeb4a5c91290d754bb78700a9b12377059611b21b8853b5ae684d065d1e024a8d0d928bf6e44312620ca586173d838523b4cfd829d3b212a9013064424d832bca63303841def4a922ea777d52e3e1e0ad871278c44a60dfc827dacb25355028a6ef92c449b0ab779ae481c6f7247c0d07707989ac922d7151736c38ab4fe728e36a148851da5b9226ce9ed03cd7ffe66292c79109817a66fff03e1cb71c0f5ab88cb631d0cb776d96727e7d7938cd0c1f4095fc8949960da212e319ec1e");
  }
}

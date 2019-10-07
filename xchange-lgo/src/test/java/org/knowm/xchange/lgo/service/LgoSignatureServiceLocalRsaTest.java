package org.knowm.xchange.lgo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class LgoSignatureServiceLocalRsaTest {

  private final String apiKey = "b34edb11-eb03-4da5-af84-496e42d8a92c";
  private LgoSignatureServiceLocalRsa signatureService;

  @Before
  public void setUp() throws Exception {
    InputStream pKey = LgoSignatureServiceLocalRsa.class.getResourceAsStream("/private_key.pem");
    String pKeyAsString = IOUtils.toString(pKey, StandardCharsets.UTF_8);
    signatureService = new LgoSignatureServiceLocalRsa(apiKey, pKeyAsString);
  }

  @Test
  public void digestHeader() {
    String digest = signatureService.digestSignedUrlHeader("wss://echo.test", "3");

    assertThat(digest)
        .isEqualTo(
            String.format(
                "LGO %s:49fc528952ac62c7fd18524df714c7929900e8da66532cd99f4e7bda74eb22913120f85902afe7d48b1a50639f8a0748b9ffb2c1736189c5a01d9de4c79131dd3911feeb4a5c91290d754bb78700a9b12377059611b21b8853b5ae684d065d1e024a8d0d928bf6e44312620ca586173d838523b4cfd829d3b212a9013064424d832bca63303841def4a922ea777d52e3e1e0ad871278c44a60dfc827dacb25355028a6ef92c449b0ab779ae481c6f7247c0d07707989ac922d7151736c38ab4fe728e36a148851da5b9226ce9ed03cd7ffe66292c79109817a66fff03e1cb71c0f5ab88cb631d0cb776d96727e7d7938cd0c1f4095fc8949960da212e319ec1e",
                apiKey));
  }

  @Test
  public void digestHeaderWithBody() {
    String timestamp = "123456789";
    String urlToSign = "http://localhost:8083/v1/live/orders";
    String payload =
        "{\"type\":\"L\",\"side\":\"S\",\"product_id\":\"BTC-USD\",\"quantity\":\"0.5\",\"price\":\"1000\",\"reference\":2468}";

    String digest = signatureService.digestSignedUrlAndBodyHeader(urlToSign, timestamp, payload);

    assertThat(digest)
        .isEqualTo(
            String.format(
                "LGO %s:58590721eccabb3cfd0bf68658bd69cd3bece50332dfbd5e55ccb695fdc5a8cf9f71257c910a1d1c425c0e3e5b591d3cc480ec29032a642512d71592f236b72ccbca1ba53695e3d28002cdb67b22875de9a4ca57b431b25719ad51fedbc961f00b979077126e76a67f5bf8baaef2109133c06aa358d3e0df21ef818db3d9a0d0770f695be4c9f5123815e184534cef8606e64e4117b81bc9dda83c4793bc4d823f66d1d2f152548e0595f031d0a5caa49407a2fdf31f6f8710133abd14cabd5ad9af7893d8360a65438946bedc377c694fd84c260d30ff6916f61a0c0492966c1cf662c3a8b523111e8ecc36b3acf65241d2e6d71ce2085d0e29a8d88655a03e",
                apiKey));
  }
}

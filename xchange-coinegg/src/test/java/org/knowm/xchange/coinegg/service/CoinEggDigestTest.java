package org.knowm.xchange.coinegg.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import jakarta.ws.rs.FormParam;
import org.junit.Test;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.RestMethodMetadata;

public class CoinEggDigestTest {

  // These Are Expired Keys
  private final String privateKey = "d}rVH-IMPIu-c3RQ4-[9fn.-R^jDN-sM$Ix-SUk$y";
  private final String publicKey = "p4pay-2min8-v41si-hah14-g5iqg-metp3-4d7tp";

  // The Signature That Is Expected To Be Created From Test Data
  private final String expectedSignature =
      "a08e3586b43abc89d0b27b127c95add1613831f9f4da8b68aa1e60e69334e0da";

  @Test
  public void paramDigestTest() throws Exception {
    RestInvocation invocation = testRestInvocation();
    CoinEggDigest digest = CoinEggDigest.createInstance(privateKey);

    assertThat(digest.digestParams(invocation)).isEqualTo(expectedSignature);
  }

  protected RestInvocation testRestInvocation() {
    return RestInvocation.create(
        null,
        new RestMethodMetadata(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new HashMap<>(),
            new Annotation[][] {
              {formParam("key")},
              {formParam("type")},
              {formParam("nonce")},
              {formParam("since")},
              {formParam("coin")},
              {formParam("signature")},
            }),
        new Object[] {publicKey, "all", "1515959060", "0", "eth", ""},
        null);
  }

  private FormParam formParam(String value) {
    return new FormParam() {
      @Override
      public String value() {
        return value;
      }

      @Override
      public Class<? extends Annotation> annotationType() {
        return FormParam.class;
      }
    };
  }
}

package org.knowm.xchange.fcoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.ws.rs.HeaderParam;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.knowm.xchange.fcoin.dto.trade.FCoinOrder;
import org.knowm.xchange.fcoin.dto.trade.FCoinSide;
import org.knowm.xchange.fcoin.dto.trade.FCoinType;
import org.knowm.xchange.fcoin.service.FCoinDigest;
import si.mazi.rescu.HttpMethod;
import si.mazi.rescu.RequestWriterResolver;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.RestMethodMetadata;
import si.mazi.rescu.serialization.jackson.JacksonRequestWriter;

public class ApiSignatureTest {

  @Test
  public void testSignature() {
    FCoinDigest digest = FCoinDigest.createInstance("3600d0a74aa3410fb3b1996cca2419c8");
    FCoinOrder order =
        new FCoinOrder(
            "btcusdt",
            FCoinSide.buy,
            FCoinType.limit,
            new BigDecimal("100.0"),
            new BigDecimal("100.0"));
    RequestWriterResolver writerResolver = new RequestWriterResolver();
    writerResolver.addWriter("application/json", new JacksonRequestWriter(new ObjectMapper()));

    HeaderParam headerParamAnn =
        new HeaderParam() {
          @Override
          public Class<? extends Annotation> annotationType() {
            return HeaderParam.class;
          }

          @Override
          public String value() {
            return "FC-ACCESS-TIMESTAMP";
          }
        };
    String signature =
        digest.digestParams(
            RestInvocation.create(
                writerResolver,
                new RestMethodMetadata(
                    null,
                    HttpMethod.POST,
                    "https://api.fcoin.com/",
                    "v2",
                    "orders",
                    null,
                    "application/json",
                    "application/json",
                    null,
                    new HashMap<>(),
                    new Annotation[][] {{headerParamAnn}, {}}),
                new Object[] {"1523069544359", order},
                null));
    Assertions.assertThat(signature).isEqualTo("DeP6oftldIrys06uq3B7Lkh3a0U=");
  }
}

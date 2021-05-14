package org.knowm.xchange.fcoin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.HeaderParam;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.knowm.xchange.fcoin.dto.trade.FCoinOrder;
import org.knowm.xchange.fcoin.dto.trade.FCoinSide;
import org.knowm.xchange.fcoin.dto.trade.FCoinType;
import org.knowm.xchange.fcoin.service.FCoinDigest;
import si.mazi.rescu.*;

public class ApiSignatureTest {

  @Test
  public void testSignature() {
    FCoinDigest digest = FCoinDigest.createInstance("3600d0a74aa3410fb3b1996cca2419c8");
    Map<Class<? extends Annotation>, Params> paramsMap = new HashMap<>();
    paramsMap.put(HeaderParam.class, Params.of("FC-ACCESS-TIMESTAMP", "1523069544359"));
    FCoinOrder order =
        new FCoinOrder(
            "btcusdt",
            FCoinSide.buy,
            FCoinType.limit,
            new BigDecimal("100.0"),
            new BigDecimal("100.0"));
    RequestWriterResolver writerResolver = new RequestWriterResolver();
    writerResolver.addWriter(
        "application/json",
        new RequestWriter() {
          @Override
          public String writeBody(RestInvocation restInvocation) {
            try {
              return (new ObjectMapper()).writeValueAsString(order);
            } catch (JsonProcessingException e) {
              e.printStackTrace();
            }
            return "";
          }
        });
    String signature =
        digest.digestParams(
            new RestInvocation(
                paramsMap,
                Collections.emptyList(),
                new RestMethodMetadata(
                    null,
                    HttpMethod.POST,
                    null,
                    null,
                    null,
                    null,
                    "application/json",
                    "application/json",
                    null,
                    null,
                    null),
                "api.fcoin.com/v2/orders",
                "https://api.fcoin.com/v2/orders",
                "",
                "/v2/orders",
                writerResolver));
    Assertions.assertThat(signature).isEqualTo("DeP6oftldIrys06uq3B7Lkh3a0U=");
  }
}

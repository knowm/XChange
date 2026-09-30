package org.knowm.xchange.poloniex.dto.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.poloniex.PoloniexAuthenticated;
import org.knowm.xchange.poloniex.dto.PoloniexException;
import si.mazi.rescu.InvocationResult;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestMethodMetadata;
import si.mazi.rescu.SynchronizedValueFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonResponseReader;

class PoloniexBalanceTest {

  @Test
  void balanceRejectTest() throws Exception {
    InvocationResult invocationResult =
        new InvocationResult("{\"error\":\"Invalid API key\\/secret pair.\"}", 200);
    Method apiMethod =
        PoloniexAuthenticated.class.getDeclaredMethod(
            "returnCompleteBalances",
            String.class,
            ParamsDigest.class,
            SynchronizedValueFactory.class,
            String.class);
    RestMethodMetadata balances = RestMethodMetadata.create(apiMethod, "", "");
    assertThatExceptionOfType(PoloniexException.class)
        .isThrownBy(
            () -> {
              try {
                new JacksonResponseReader(
                        new DefaultJacksonObjectMapperFactory().createObjectMapper(), false)
                    .read(invocationResult, balances);
              } catch (PoloniexException e) {
                assertThat(e.getMessage().startsWith("Invalid API key/secret pair.")).isTrue();
                throw e;
              }
            });
  }
}

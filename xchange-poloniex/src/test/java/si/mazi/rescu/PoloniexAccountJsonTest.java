package si.mazi.rescu;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.poloniex.PoloniexAuthenticated;
import org.knowm.xchange.poloniex.PoloniexException;

import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonResponseReader;

public class PoloniexAccountJsonTest {

  @Test
  public void testBalancesError() throws Exception {

    InvocationResult invocationResult = new InvocationResult("{\"error\":\"Invalid API key\\/secret pair.\"}", 200);

    Method apiMethod = PoloniexAuthenticated.class.getDeclaredMethod("returnCompleteBalances", String.class, ParamsDigest.class,
        SynchronizedValueFactory.class, String.class);
    RestMethodMetadata balances = RestMethodMetadata.create(apiMethod, "", "");

    try {
      new JacksonResponseReader(new DefaultJacksonObjectMapperFactory().createObjectMapper(), false).read(invocationResult, balances);
      Assert.assertTrue("Should have failed.", false);
    } catch (PoloniexException e) {
      Assert.assertTrue(e.getMessage().contains("Invalid API key/secret pair."));
    }
  }

}

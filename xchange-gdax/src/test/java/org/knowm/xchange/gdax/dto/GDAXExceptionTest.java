package org.knowm.xchange.gdax.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class GDAXExceptionTest {
  @Test
  public void unmarshalTest() throws IOException {
    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/gdax/dto/trade/example-order-entry-reject.json");
    GDAXException exception = mapper.readValue(is, GDAXException.class);
    assertThat(exception.getMessage()).startsWith("Insufficient funds");
  }
}

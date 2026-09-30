package org.knowm.xchange.bitfinex.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

@Data
public final class Config {

  private ObjectMapper objectMapper;
  private SynchronizedValueFactory<Long> nonceFactory;

  private static Config instance = new Config();

  private Config() {
    nonceFactory = new CurrentTimeIncrementalNonceFactory(TimeUnit.MILLISECONDS);
  }

  public static Config getInstance() {
    return instance;
  }

  /** Falls back to a default Bitfinex mapper if no exchange has been created yet. */
  public ObjectMapper getObjectMapper() {
    if (objectMapper == null) {
      objectMapper = new BitfinexJacksonObjectMapperFactory().createObjectMapper();
    }
    return objectMapper;
  }
}
